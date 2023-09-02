package tech.hackcity.educarts.data.network

import tech.hackcity.educarts.domain.model.error.ErrorMessage

/**
 *Created by Victor Loveday on 8/30/23
 */
interface ResponseWithErrors {
    val error: Boolean
    val errorMessage: List<ErrorMessage>
}
