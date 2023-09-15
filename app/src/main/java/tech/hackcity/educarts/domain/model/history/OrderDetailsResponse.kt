package tech.hackcity.educarts.domain.model.history

import tech.hackcity.educarts.domain.model.error.ErrorMessage
import java.io.Serializable

data class OrderDetailsResponse(
    val `data`: List<OrderDetails>,
    val error: Boolean,
    var errorMessage: List<ErrorMessage>,
    val message: String
): Serializable