package tech.hackcity.educarts.domain.model.payment

import java.io.Serializable

data class OrderSummary(
    val charges: Int,
    val order_id: String,
    val order_type: String,
    val rate: Int,
    val sevis_fee: String,
    val total_in_dollars: Int,
    val total_in_naira: Int
): Serializable