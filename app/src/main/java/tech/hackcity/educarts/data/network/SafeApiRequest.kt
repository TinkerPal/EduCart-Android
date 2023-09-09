package tech.hackcity.educarts.data.network

import retrofit2.Response
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import tech.hackcity.educarts.uitls.NoInternetException
import java.io.IOException
import java.net.SocketTimeoutException

/**
 *Created by Victor Loveday on 4/28/23
 */

abstract class SafeApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()

        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
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
    }
}

