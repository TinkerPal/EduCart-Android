package tech.hackcity.educarts.domain.model.support

import tech.hackcity.educarts.domain.model.error.ErrorMessage

data class ConsultantsResponse(
    val `data`: List<Consultant>,
    val error: Boolean,
    val errorMessage: List<ErrorMessage>,
    val message: String
)