package tech.hackcity.educarts.domain.model.auth

data class CreateNewPasswordResponse(
    val `data`: CreateNewPasswordResponseData,
    val error: Boolean,
    val message: String
)