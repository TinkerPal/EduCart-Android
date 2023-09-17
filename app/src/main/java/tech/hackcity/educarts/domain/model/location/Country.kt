package tech.hackcity.educarts.domain.model.location

import java.io.Serializable

data class Country(
    val currency: String,
    val id: Int,
    val name: String,
    val phone_code: String,
    val states: List<State>
):Serializable