package tech.hackcity.educarts.domain.model.auth

data class RegisterUserResponse(
    val data: RegisterUserResponseData,
    val error: Boolean,
    val message: Message
)