package tech.hackcity.educarts.domain.model.auth

import tech.hackcity.educarts.domain.model.error.ErrorMessage

data class TokenResponse(
    val access: String,
    val refresh: String,
    val errorMessage: List<ErrorMessage>,
    val error: Boolean
)