package tech.hackcity.educarts.domain.model.auth

data class RegenerateOTPResponse(
    val `data`: RegenerateOTPResponseData,
    val error: Boolean,
    val message: String
)