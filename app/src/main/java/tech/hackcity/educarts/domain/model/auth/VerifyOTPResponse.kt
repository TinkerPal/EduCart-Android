package tech.hackcity.educarts.domain.model.auth

data class VerifyOTPResponse(
    val `data`: VerifyOTPResponseData,
    val error: Boolean,
    val message: String
)