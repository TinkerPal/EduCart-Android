package tech.hackcity.educarts.data.repositories.payment

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.network.SafeApiRequest
import tech.hackcity.educarts.domain.model.payment.paymentreceipt.TransferReceiptResponse

/**
 *Created by Victor Loveday on 9/27/23
 */
class CheckoutRepository(
    private val api: RetrofitInstance
) : SafeApiRequest() {

    suspend fun uploadPaymentReceipt(
        receipt: MultipartBody.Part,
        orderId: String
    ): TransferReceiptResponse {
        val orderIdRB = RequestBody.create("text/plain".toMediaTypeOrNull(), orderId)
        return apiRequest { api.orderAPI.uploadPaymentReceipt(receipt, orderIdRB) }
    }
}