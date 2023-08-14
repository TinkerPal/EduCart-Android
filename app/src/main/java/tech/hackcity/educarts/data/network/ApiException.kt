package tech.hackcity.educarts.data.network

/**
 *Created by Victor Loveday on 8/12/23
 */
class ApiException(message: String, val errorCode: String) : Exception(message)
