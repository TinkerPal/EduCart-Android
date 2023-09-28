package tech.hackcity.educarts.ui.payment.checkout

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import okhttp3.MultipartBody
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.ApiException
import tech.hackcity.educarts.data.repositories.payment.CheckoutRepository
import tech.hackcity.educarts.uitls.Coroutines

/**
 *Created by Victor Loveday on 9/27/23
 */
class CheckoutViewModel(private val repository: CheckoutRepository): ViewModel() {

    var listener: CheckoutListener? = null


    fun uploadPaymentReceipt(context: Context, receipt: MultipartBody.Part?, orderId: String?) {
        listener?.onUploadReceiptStarted()

        if (receipt == null || orderId.isNullOrEmpty()) {
            listener?.onUploadReceiptFailed(context.resources.getString(R.string.missing_field))
            return
        }

        Coroutines.onMainWithScope(viewModelScope) {
            try {
                val response = repository.uploadPaymentReceipt(receipt, orderId)

                if (!response.error) {
                    listener?.onUploadReceiptSuccessful(response)
                }

            } catch (e: ApiException) {
                listener?.onUploadReceiptFailed(e.errorMessage)
            }
        }
    }

}