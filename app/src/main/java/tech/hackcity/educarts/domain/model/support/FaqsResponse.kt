package tech.hackcity.educarts.domain.model.support

import tech.hackcity.educarts.domain.model.error.ErrorMessage

data class FaqsResponse(
    val `data`: List<FaqCategory>,
    val error: Boolean,
    val errorMessage: List<ErrorMessage>,
    val message: String
)