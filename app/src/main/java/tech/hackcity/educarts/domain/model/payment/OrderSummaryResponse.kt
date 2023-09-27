package tech.hackcity.educarts.domain.model.payment

import tech.hackcity.educarts.domain.model.error.ErrorMessage

data class OrderSummaryResponse(
    val `data`: OrderSummary,
    val error: Boolean,
    val errorMessage: List<ErrorMessage>,
    val message: String
)