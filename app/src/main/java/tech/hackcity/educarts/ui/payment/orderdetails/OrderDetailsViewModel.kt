package tech.hackcity.educarts.ui.payment.orderdetails

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.ApiException
import tech.hackcity.educarts.data.network.ErrorCodes.USER_NOT_VERIFIED
import tech.hackcity.educarts.data.repositories.auth.AuthRepository
import tech.hackcity.educarts.data.repositories.payment.OrderRepository
import tech.hackcity.educarts.domain.model.auth.LoginResponseData
import tech.hackcity.educarts.domain.model.auth.User
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.NoInternetException
import tech.hackcity.educarts.uitls.extractDataFields
import tech.hackcity.educarts.uitls.extractErrorMessagesFromErrorBody

/**
 *Created by Victor Loveday on 5/29/23
 */
class OrderDetailsViewModel(
    private val repository: OrderRepository
) : ViewModel() {

    var orderId: String? = null

    var listener: OrderDetailsListener? = null

    fun trackOrder(context: Context) {
        listener?.onRequestStarted()

        if (orderId.isNullOrEmpty()) {
            context.resources.getString(R.string.field_can_not_be_empty)
            return
        }

        Coroutines.onMain {
            try {
                val response = repository.trackOrder(orderId!!)

                if (!response.error) {
                    listener?.onRequestSuccessful(response)

                }

            } catch (e: ApiException) {
                listener?.onRequestFailed("${e.errorMessage} ${e.errorData}")
            }
        }

    }

}