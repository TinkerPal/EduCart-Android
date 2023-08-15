package tech.hackcity.educarts.domain.model.auth

/**
 *Created by Victor Loveday on 8/3/23
 */
data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val countryOfResidence: String,
    val email: String
)
