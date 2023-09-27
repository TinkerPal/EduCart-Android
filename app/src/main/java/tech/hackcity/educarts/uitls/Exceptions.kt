package tech.hackcity.educarts.uitls

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import tech.hackcity.educarts.R
import tech.hackcity.educarts.domain.model.error.ErrorMessage
import java.io.IOException
import java.lang.StringBuilder
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

fun extractErrorMessagesFromRegisterErrorBody(jsonString: String): String {
    val textValues = mutableListOf<String>()

    try {
        val jsonObject = JSONObject(jsonString)

        for (key in jsonObject.keys()) {
            if (key in setOf("email", "phone_number", "password")) {
                val jsonArray = jsonObject.optJSONArray(key)
                if (jsonArray != null) {
                    for (i in 0 until jsonArray.length()) {
                        val value = jsonArray.optString(i)
                        if (!value.isNullOrEmpty()) {
                            textValues.add(value)
                        }
                    }
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return textValues.joinToString("\n\n")
}

fun extractDataFields(dataJson: String): Pair<String?, String?> {
    var id: String? = null
    var email: String? = null

    try {
        val jsonObject = JSONObject(dataJson)

        id = jsonObject.getString("id")
        email = jsonObject.getString("email")

    } catch (e: JSONException) {
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

fun extractIdAndEmailFromErrorBody(jsonStr: String): Pair<String?, String?> {
    try {
        val data = JSONObject(jsonStr).optJSONObject("data")
        val userId = data?.optString("id")
        val userEmail = data?.optString("email")
        return Pair(userId, userEmail)
    } catch (e: Exception) {
        e.printStackTrace()
        return Pair(null, null)
    }
}


