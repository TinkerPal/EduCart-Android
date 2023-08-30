package tech.hackcity.educarts.domain.model.settings

data class ProfileResponseData(
    val country_of_residence: String,
    val email: String,
    val first_name: String,
    val id: String,
    val institution_of_study: String,
    val last_name: String,
    val country_code: Int,
    val phone_number: String,
    val profile_picture: String,
    val is_restricted: Boolean,
    val profile_completed: Boolean
)