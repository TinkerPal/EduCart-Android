package tech.hackcity.educarts.domain.model.history

import tech.hackcity.educarts.domain.model.error.ErrorMessage
import java.io.Serializable

data class OrderHistoryResponse(
    val date: List<OrderHistoryResponseData>,
    val error: Boolean,
    val errorMessage: List<ErrorMessage>,
    val message: String
): Serializable