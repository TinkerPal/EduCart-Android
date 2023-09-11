package tech.hackcity.educarts.domain.model.support

import tech.hackcity.educarts.domain.model.error.ErrorMessage

data class ConsultantProfileResponse(
    val `data`: Consultant,
    val error: Boolean,
    val errorMessage: List<ErrorMessage>,
    val message: String
)