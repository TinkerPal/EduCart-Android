package tech.hackcity.educarts.domain.model.payment.sevis

import tech.hackcity.educarts.domain.model.error.ErrorMessage

data class SEVISFeeStep2Response(
    val `data`: SEVISFeeStep2ResponseData,
    val error: Boolean,
    val errorMessage: List<ErrorMessage>,
    val message: String
)