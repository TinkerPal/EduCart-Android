package tech.hackcity.educarts.domain.model.settings

data class ProfileResponse(
    val `data`: ProfileResponseData,
    val error: Boolean,
    val errorMessage: ProfileErrorMessage,
    val message: String?
)