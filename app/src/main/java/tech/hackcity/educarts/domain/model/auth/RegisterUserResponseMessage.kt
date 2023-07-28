package tech.hackcity.educarts.domain.model.auth

data class RegisterUserResponseMessage(
    val success: List<String>?,
    val email: List<String>?,
    val password: List<String>?,
    val phone_number: List<String>?
)