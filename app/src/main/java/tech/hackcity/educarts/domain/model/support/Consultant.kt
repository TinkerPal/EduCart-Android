package tech.hackcity.educarts.domain.model.support

import java.io.Serializable

data class Consultant(
    val country: String?,
    val id: Int,
    val name: String?,
    val price_per_hour: Int?,
    val specialization: String?,
    val state: String?,
    val user: String,
    val years_of_experience: Int?,
    val profile_picture: String?,
    val rate: Int?,
    val availability: String?,
    val daily_availability: String?,
    val bio: String?,
    val qualification: String?
):Serializable