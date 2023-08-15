package tech.hackcity.educarts.data.network

import retrofit2.Response
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import org.json.JSONException
import org.json.JSONObject
import tech.hackcity.educarts.uitls.NoInternetException

/**
 *Created by Victor Loveday on 4/28/23
 */

//abstract class SafeApiRequest {
//    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
//        val response = call.invoke()
//
//        if (response.isSuccessful && response.body() != null) {
//            return response.body()!!
//
//        } else {
//            val message = StringBuilder()
//            val error = response.errorBody()?.string()
//            error?.let {
//                try {
//                    message.append(JSONObject(it).getString("message"))
//
//                } catch (e: JSONException) {
//                    message.append(e.message)
//                }
//                message.append("\n")
//            }
//
//            message.append("Error code: ${response.code()}")
//
//            throw IOException(message.toString())
//        }
//    }
//}

abstract class SafeApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        try {
            val response = call.invoke()

            if (response.isSuccessful && response.body() != null) {
                return response.body()!!
            } else {
                val message = StringBuilder()
                val error = response.errorBody()?.string()
                error?.let {
                    try {
                        message.append(JSONObject(it).getString("message"))
                    } catch (e: JSONException) {
                        message.append(e.message)
                    }
                    message.append("\n")
                }

                message.append("Error code: ${response.code()}")

                throw ApiException(message.toString(), response.code().toString())
            }
        } catch (e: IOException) {
            throw ApiException("IOException: ${e.message}", ErrorCodes.IO_EXCEPTION)
        } catch (e: NoInternetException) {
            throw ApiException("No Internet Connection: ${e.message}", ErrorCodes.NO_INTERNET_CONNECTION)
        } catch (e: HttpException) {
            throw ApiException("HTTP Exception: ${e.message}", ErrorCodes.HTTP_EXCEPTION)
        } catch (e: SocketTimeoutException) {
            throw ApiException("Socket Timeout Exception: ${e.message}", ErrorCodes.STO_EXCEPTION)
        }
    }
}
