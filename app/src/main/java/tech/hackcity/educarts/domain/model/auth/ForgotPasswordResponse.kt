package tech.hackcity.educarts.domain.model.auth

import tech.hackcity.educarts.domain.model.error.ErrorMessage

data class ForgotPasswordResponse(
    val `data`: ForgotPasswordResponseData,
    val error: Boolean,
    val errorMessage: List<ErrorMessage>,
    val message: String
)