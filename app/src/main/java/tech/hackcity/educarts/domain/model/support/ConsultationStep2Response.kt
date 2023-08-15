package tech.hackcity.educarts.domain.model.support

import tech.hackcity.educarts.domain.model.error.ErrorMessage

data class ConsultationStep2Response(
    val `data`: ConsultationStep2ResponseData,
    val error: Boolean,
    val errorMessage: List<ErrorMessage>,
    val message: String
)