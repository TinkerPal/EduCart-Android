package tech.hackcity.educarts.uitls

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

fun errorMessageFetcher(errorMessages: MutableList<ErrorMessage>): String {

    val messages = mutableListOf<String>()

    for (i in errorMessages) {
        messages.add(i.message)
    }

    return messages.joinToString(",")
        .split(",")
        .joinToString("\n\n")
}