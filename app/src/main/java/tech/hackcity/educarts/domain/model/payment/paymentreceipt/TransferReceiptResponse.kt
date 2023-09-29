package tech.hackcity.educarts.domain.model.payment.paymentreceipt

data class TransferReceiptResponse(
    val `data`: TransferReceiptResponseData,
    val error: Boolean,
    val message: String
)