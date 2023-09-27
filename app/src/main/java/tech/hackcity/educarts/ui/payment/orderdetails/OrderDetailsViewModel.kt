package tech.hackcity.educarts.ui.payment.orderdetails

import android.content.Context
import androidx.lifecycle.ViewModel
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.ApiException
import tech.hackcity.educarts.data.repositories.payment.OrderRepository
import tech.hackcity.educarts.ui.payment.ordersummary.OrderSummaryListener
import tech.hackcity.educarts.uitls.Coroutines

/**
 *Created by Victor Loveday on 5/29/23
 */
class OrderDetailsViewModel(
    private val repository: OrderRepository
) : ViewModel() {

    var orderDetailsListener: OrderDetailsListener? = null
    var orderSummaryListener: OrderSummaryListener? = null

    fun trackOrder(context: Context, orderId: String) {
        orderDetailsListener?.onRequestStarted()

        if (orderId.isEmpty()) {
            context.resources.getString(R.string.missing_field)
            return
        }

        Coroutines.onMain {
            try {
                val response = repository.trackOrder(orderId)

                if (!response.error) {
                    orderDetailsListener?.onRequestSuccessful(response)

                }

            } catch (e: ApiException) {
                orderDetailsListener?.onRequestFailed("${e.errorMessage} ${e.errorData}")
            }
        }

    }

    fun fetchOrderSummary(context: Context, orderType: String) {
        orderSummaryListener?.onRequestStarted()

        if (orderType.isEmpty()) {
            context.resources.getString(R.string.missing_field)
            return
        }

        Coroutines.onMain {
            try {
                val response = repository.fetchOrderSummary(orderType)

                if (!response.error) {
                    orderSummaryListener?.onRequestSuccessful(response)
                }

            } catch (e: ApiException) {
                orderSummaryListener?.onRequestFailed("${e.errorMessage} ${e.errorData}")
            }
        }

    }

}