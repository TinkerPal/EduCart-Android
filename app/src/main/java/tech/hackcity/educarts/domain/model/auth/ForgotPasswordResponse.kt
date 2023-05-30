package tech.hackcity.educarts.domain.model.auth

data class ForgotPasswordResponse(
    val `data`: ForgotPasswordResponseData,
    val error: Boolean,
    val message: String
)