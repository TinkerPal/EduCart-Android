package tech.hackcity.educarts.domain.model.history

/**
 *Created by Victor Loveday on 9/13/23
 */
data class OrderDetails(
    val amount: String,
    val date: String,
    val id: Int,
    val order_id: String,
    val order_type: String,
    val status: String,
    val user: String,
    val statusTitle: String,
    val statusDescription: String,
    val step: Int
)
