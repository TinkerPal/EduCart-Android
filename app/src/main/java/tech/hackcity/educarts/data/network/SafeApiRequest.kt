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
            } catch (e: IOException) {
                throw ApiException("IOException: ${e.message}")
            } catch (e: NoInternetException) {
                throw ApiException("IOException: ${e.message}")
            } catch (e: HttpException) {
                throw ApiException("HTTP Exception: ${e.message}")
            } catch (e: SocketTimeoutException) {
                throw ApiException("Socket Timeout Exception: ${e.message}")
            }

            throw ApiException(errorMessage.toString(), dataMessage.toString())
        }
    }
}



//abstract class SafeApiRequest {
//    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
//        try {
//            val response = call.invoke()
//
//            if (response.isSuccessful && response.body() != null) {
//                return response.body()!!
//            } else {
//                val message = StringBuilder()
//                val error = response.errorBody()?.string()
//                error?.let {
//                    try {
//                        message.append(JSONObject(it).getString("errorMessage"))
//                    } catch (e: JSONException) {
//                        message.append(e.message)
//                    }
//                    message.append("\n")
//                }
//
//                message.append("Error code: ${response.code()}")
//
//                throw ApiException(message.toString())
//            }
//        }
//        catch (e: IOException) {
//            throw ApiException("IOException: ${e.message}")
//        } catch (e: NoInternetException) {
//            throw ApiException("No Internet Connection: ${e.message}")
//        } catch (e: HttpException) {
//            throw ApiException("HTTP Exception: ${e.message}")
//        } catch (e: SocketTimeoutException) {
//            throw ApiException("Socket Timeout Exception: ${e.message}")
//        }
//    }
//}


//abstract class SafeApiRequest {
//    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): Pair<String?, T?> {
//        try {
//            val response = call.invoke()
//
//            if (response.isSuccessful && response.body() != null) {
//                val responseData = response.body()!!
//                val errorMessages = response.errorBody()?.toString()
//                return Pair(errorMessages, responseData)
//            } else {
//                val message1 = StringBuilder()
//                val errorMessages = response.errorBody()?.string()
//                errorMessages?.let {
//                    try {
//                        message1.append(JSONObject(it).getString("errorMessage"))
//                    } catch (e: JSONException) {
//                        message1.append(e.message)
//                    }
//                    message1.append("\n")
//                }
//
//                val message2 = StringBuilder()
//                val responseData = response.errorBody()?.string()
//                responseData?.let {
//                    try {
//                        message2.append(JSONObject(it).getString("data"))
//                    }catch (e: JSONException) {
//                        message2.append(e.message)
//                    }
//                    message2.append("\n")
//                }
//
//                message1.append("Error code: ${response.code()}")
//                throw ApiException(message1.toString(), message2.toString())
//            }
//        }
//        catch (e: IOException) {
//            throw ApiException("IOException: ${e.message}", null)
//        } catch (e: NoInternetException) {
//            throw ApiException("No Internet Connection: ${e.message}", null)
//        }
////        catch (e: HttpException) {
////            throw ApiException("HTTP Exception: ${e.message}", null)
////        }
//        catch (e: SocketTimeoutException) {
//            throw ApiException("Socket Timeout Exception: ${e.message}", null)
//        }
//    }
//}


//abstract class SafeApiRequest {
//    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): Pair<String?, T?> {
//        try {
//            val response = call.invoke()
//            val data = response.body()
//            val error = response.errorBody()?.string()
//
//            if (response.isSuccessful) {
//                return Pair(null, data)
//            } else {
//                val errorMessage: String? = try {
//                    error?.let{JSONObject(it).getString("errorMessage")}
//                } catch (e: JSONException) {
//                    e.message
//                }
//
//                val responseData: T? = try {
//                    error?.let{JSONObject(it).getString("data")}
//                } catch (e: JSONException) {
//                    null
//                }
//
//                return Pair(errorMessage, responseData)
//            }
//        } catch (e: IOException) {
//            throw ApiException("IOException: ${e.message}", null)
//        } catch (e: NoInternetException) {
//            throw ApiException("No Internet Connection: ${e.message}", null)
//        } catch (e: HttpException) {
//            val errorResponse = e.response()?.errorBody()?.string()
//            val errorMessage: String? = try {
//                errorResponse?.let {
//                    JSONObject(it).getString("errorMessage")} ?: "Unknown error"
//            } catch (jsonException: JSONException) {
//                jsonException.message
//            }
//
//            val responseData: T? = try {
//                errorResponse?.let {JSONObject(it).getJSONObject("data").toString()}
//            } catch (jsonException: JSONException) {
//                null
//            }
//
//            return Pair(errorMessage ?: "HTTP Exception: ${e.message}", responseData)
//        } catch (e: SocketTimeoutException) {
//            throw ApiException("Socket Timeout Exception: ${e.message}", null)
//        }
//    }
//}
