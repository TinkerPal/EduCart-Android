package tech.hackcity.educarts.domain.model.support

import tech.hackcity.educarts.domain.model.error.ErrorMessage

data class ConsultationResponse(
    val `data`: List<ConsultationResponseData>,
    val error: Boolean,
    val errorMessage: List<ErrorMessage>,
    val message: String
)