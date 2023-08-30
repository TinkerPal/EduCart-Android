package tech.hackcity.educarts.domain.model.payment.sevis

data class SEVISFeeStep2ResponseData(
    val category: String,
    val country_of_birth: String,
    val country_of_citizenship: String,
    val email: String,
    val form_type: String,
    val phone_number: String
)