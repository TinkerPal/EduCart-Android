package tech.hackcity.educarts.domain.model.payment.sevis

data class SEVISFeeStep1ResponseData(
    val date_of_birth: String,
    val form: String,
    val given_name: String,
    val international_passport: String,
    val last_name: String,
    val passport: String,
    val sevis_id: String,
    val user: String
)