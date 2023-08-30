package tech.hackcity.educarts.domain.model.auth

data class LoginResponseData(
    val access: String,
    val country_of_residence: String,
    val email: String,
    val first_name: String,
    val id: String,
    val profile_picture: String?,
    val last_name: String,
    val country_code: Int,
    val phone_number: String,
    val profile_completed: Boolean,
    val refresh: String,
    val is_restricted: Boolean
)