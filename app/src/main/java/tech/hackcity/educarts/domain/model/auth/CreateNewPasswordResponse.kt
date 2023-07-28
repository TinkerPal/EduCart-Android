package tech.hackcity.educarts.domain.model.auth

import tech.hackcity.educarts.domain.model.error.ErrorMessage

data class CreateNewPasswordResponse(
    val `data`: CreateNewPasswordResponseData,
    val error: Boolean,
    val errorMessage: List<ErrorMessage>,
    val message: String
)