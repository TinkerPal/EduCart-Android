package tech.hackcity.educarts.uitls

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import tech.hackcity.educarts.domain.model.error.ErrorMessage
import java.io.IOException
import java.net.SocketTimeoutException

/**
 *Created by Victor Loveday on 4/28/23
 */

class IOException(message: String) : IOException(message)
class HTTPException(response: Response<*>) : HttpException(response)
class SocketTimeOutException(message: String) : SocketTimeoutException(message)
class NoInternetException(message: String) : IOException(message)

fun extractErrorMessagesFromErrorBody(errorJson: String): List<Pair<String, String>> {
    val errorList = mutableListOf<Pair<String, String>>()

    try {
        val jsonArray = JSONArray(errorJson)
        for (i in 0 until jsonArray.length()) {
            val errorObject = jsonArray.getJSONObject(i)
            val errorCode = errorObject.getString("code")
            val errorMessage = errorObject.getString("message")
            errorList.add(Pair(errorCode, errorMessage))
        }
    } catch (e: JSONException) {
        e.printStackTrace()
    }

    return errorList
}

fun extractDataFields(dataJson: String): Pair<String?, String?> {
    var id: String? = null
    var email: String? = null

    try {
        val jsonObject = JSONObject(dataJson)

        id = jsonObject.getString("id")
        email = jsonObject.getString("email")

    }catch (e: JSONException) {
        e.printStackTrace()
    }

    return Pair(id, email)
}

fun extractDataFieldsFromErrorBody(dataJson: String): Pair<String?, String?> {
    var id: String? = null
    var email: String? = null

    try {
        val jsonArray = JSONArray(dataJson)
        val jsonObject = jsonArray.getJSONObject(jsonArray.length() - 1)

        id = jsonObject.optString("id")
        email = jsonObject.optString("email")
    } catch (e: JSONException) {
        e.printStackTrace()
    }

    return Pair(id, email)
}


