package tech.hackcity.educarts.domain.model.location

import tech.hackcity.educarts.domain.model.error.ErrorMessage

data class RegionResponse(
    val `data`: List<Country>,
    val error: Boolean,
    val errorMessage: List<ErrorMessage>,
    val message: String
)