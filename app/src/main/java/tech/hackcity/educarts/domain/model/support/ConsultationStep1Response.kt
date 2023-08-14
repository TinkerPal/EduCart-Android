package tech.hackcity.educarts.domain.model.support

import tech.hackcity.educarts.domain.model.error.ErrorMessage

data class ConsultationStep1Response(
    val `data`: ConsultationStep1ResponseData,
    val error: Boolean,
    val errorMessage: List<ErrorMessage>,
    val message: String
)