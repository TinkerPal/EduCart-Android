package tech.hackcity.educarts.uitls

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

/**
 *Created by Victor Loveday on 4/28/23
 */

class IOException(message: String) : IOException(message)
class HTTPException(response: Response<*>) : HttpException(response)
class SocketTimeOutException(message: String) : SocketTimeoutException(message)
class NoInternetException(message: String) : IOException(message)