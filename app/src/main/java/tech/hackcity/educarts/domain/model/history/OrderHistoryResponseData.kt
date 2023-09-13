package tech.hackcity.educarts.domain.model.history

import java.io.Serializable

data class OrderHistoryResponseData(
    val amount: String,
    val date: String,
    val id: Int,
    val order_id: String,
    val order_type: String,
    val status: String,
    val user: String
):Serializable