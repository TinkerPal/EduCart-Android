package tech.hackcity.educarts.domain.model.settings

import tech.hackcity.educarts.domain.model.error.ErrorMessage

data class ProfileResponse(
    val `data`: ProfileResponseData,
    val error: Boolean,
    val errorMessage: ProfileErrorMessage,
    val message: String?
)