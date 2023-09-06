package tech.hackcity.educarts.domain.model.auth

data class RegenerateOTPResponseData(
    val country_code: Int,
    val country_of_residence: String,
    val email: String,
    val first_name: String,
    val id: String,
    val last_name: String,
    val phone_number: String
)