package tech.hackcity.educarts.domain.model.auth

/**
 *Created by Victor Loveday on 8/3/23
 */
data class User(
    val id: String,
    val profilePhoto: String?,
    val firstName: String,
    val lastName: String,
    val countryCode: Int,
    val phoneNumber: String,
    val countryOfResidence: String,
    val email: String,
    val isProfileCompleted: Boolean,
    val is_restricted: Boolean,
    val institutionOfStudy: String? = null,
    val countryOfBirth: String? = null,
    val state: String? = null,
    val city: String? = null,
)
