package tech.hackcity.educarts.domain.model.history

data class OrderDetails(
    val amount: String,
    val id: Int,
    val order_id: String,
    val order_stage: List<OrderStage>,
    val order_type: String,
    val status: String,
    val user: String
)