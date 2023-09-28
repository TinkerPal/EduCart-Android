package tech.hackcity.educarts.domain.model.payment.paymentreceipt

data class TransferReceiptResponseData(
    val date: String,
    val id: Int,
    val order_id: String,
    val receipt: String
)