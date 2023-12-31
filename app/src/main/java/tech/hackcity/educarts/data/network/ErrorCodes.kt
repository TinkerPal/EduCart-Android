package tech.hackcity.educarts.data.network

/**
 *Created by Victor Loveday on 8/3/23
 */
object ErrorCodes {
    const val NO_INTERNET_CONNECTION = "no_internet_connection"
    const val HTTP_EXCEPTION = "no_internet_connection"
    const val IO_EXCEPTION = "io_exception"
    const val STO_EXCEPTION = "socket_time_out_exception"
    const val INVALID_CREDENTIALS = "invalid_credentials"
    const val EMPTY_FORM_FIELD = "empty_form_field"
    const val EMPTY_USER_FIELD = "empty_user_field"
    const val EMPTY_PASSPORT_FIELD = "empty_passport_field"
    const val EMPTY_INTERNATIONAL_PASSPORT_FIELD = "empty_international_passport_field"
    const val WEAK_PASSWORD = "weak_password"
    const val MAX_RETRIES_EXCEEDED = "max_retries_exceeded"
    const val USER_NOT_VERIFIED = "user_not_verified"
    const val PROFILE_NOT_COMPLETED = "profile_not_completed"
    const val NON_FIELD_ERRORS = "non_field_errors"
    const val INVALID_PK = "invalid_pk"
}