package tech.hackcity.educarts.domain.model.auth

data class LoginResponse(
    val `data`: LoginResponseData,
    val error: Boolean,
    val message: String
)