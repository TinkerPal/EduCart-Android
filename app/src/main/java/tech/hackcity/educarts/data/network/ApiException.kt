package tech.hackcity.educarts.data.network

import java.io.IOException
import java.lang.Exception

/**
 *Created by Victor Loveday on 8/12/23
 */
class ApiException(val errorMessage: String, val  errorData: String? = null) : Exception(errorMessage)
