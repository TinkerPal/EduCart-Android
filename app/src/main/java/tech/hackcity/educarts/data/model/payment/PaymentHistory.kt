package tech.hackcity.educarts.data.model.payment

/**
 *Created by Victor Loveday on 5/11/23
 */
data class PaymentHistory(
    val transactionId: String,
    val service: String,
    val status: String,
    val date: String
)
