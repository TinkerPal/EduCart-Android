package tech.hackcity.educarts.ui.payment.checkout

import tech.hackcity.educarts.domain.model.auth.LoginResponse
import tech.hackcity.educarts.domain.model.auth.RegisterUserResponse
import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.domain.model.history.OrderDetailsResponse
import tech.hackcity.educarts.domain.model.payment.OrderSummaryResponse
import tech.hackcity.educarts.domain.model.payment.paymentreceipt.TransferReceiptResponse

/**
 *Created by Victor Loveday on 5/29/23
 */
interface CheckoutListener {
    fun onUploadReceiptStarted()
    fun onUploadReceiptFailed(errorMessage: String)
    fun onUploadReceiptSuccessful(response: TransferReceiptResponse)
}