package tech.hackcity.educarts.domain.model.settings

data class ProfileResponseData(
    val city: String?,
    val country_code: Int,
    val country_of_birth: String?,
    val country_of_residence: String,
    val email: String,
    val first_name: String,
    val id: String,
    val institution_of_study: String?,
    val is_restricted: Boolean,
    val last_name: String,
    val phone_number: String,
    val profile_completed: Boolean,
    val profile_picture: String?,
    val state: String?,
    val free_consultation: Boolean,
)