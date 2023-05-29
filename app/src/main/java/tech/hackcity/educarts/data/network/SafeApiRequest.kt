package tech.hackcity.educarts.data.network

import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import tech.hackcity.educarts.uitls.IOException

/**
 *Created by Victor Loveday on 4/28/23
 */

abstract class SafeApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
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

            throw IOException(message.toString())
        }
    }
}