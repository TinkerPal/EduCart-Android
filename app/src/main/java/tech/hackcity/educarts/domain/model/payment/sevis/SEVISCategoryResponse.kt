package tech.hackcity.educarts.domain.model.payment.sevis

data class SEVISCategoryResponse(
    val `data`: Data,
    val error: Boolean,
    val message: String
)