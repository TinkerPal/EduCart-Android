package tech.hackcity.educarts.data.network

import retrofit2.Response
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import tech.hackcity.educarts.uitls.NoInternetException
import java.io.IOException
import java.net.SocketTimeoutException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tech.hackcity.educarts.uitls.HTTPException
import java.net.UnknownHostException

/**
 *Created by Victor Loveday on 4/28/23
 */

abstract class SafeApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        return withContext(Dispatchers.IO) {
            try {
                val response = call.invoke()

                if (response.isSuccessful && response.body() != null) {
                    return@withContext response.body()!!
                } else {
                    handleApiError(response)
                }
            } catch (e: Exception) {
                throw handleNetworkError(e)
            }
        }
    }

    private fun handleApiError(response: Response<*>): Nothing {
        val errorMessage = StringBuilder()
        val dataMessage = StringBuilder()

        try {
            response.errorBody()?.let { errorBody ->
                val errorJson = JSONObject(errorBody.string())
                errorMessage.append(errorJson.optString("errorMessage"))

                // Capture the "data" field if it exists
                if (errorJson.has("data")) {
                    dataMessage.append(errorJson.getString("data"))
                }
            }
        } catch (e: JSONException) {
            errorMessage.append(e.message)
        }

        throw ApiException(errorMessage.toString(), dataMessage.toString())
    }

    private fun handleNetworkError(e: Exception): ApiException {
        return when (e) {
            is SocketTimeoutException -> ApiException("Check your internet connection and try again")
            is UnknownHostException -> ApiException("Something went wrong. Please try again")
            is NoInternetException -> ApiException("No internet connection")
            is IOException -> ApiException("Something went wrong: ${e.message}")
            is HTTPException -> ApiException("${e.message}")
            else -> ApiException("${e.message}")
        }
    }
}


