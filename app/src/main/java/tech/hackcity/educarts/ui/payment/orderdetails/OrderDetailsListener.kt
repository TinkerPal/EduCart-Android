package tech.hackcity.educarts.ui.payment.orderdetails

import tech.hackcity.educarts.domain.model.auth.LoginResponse
import tech.hackcity.educarts.domain.model.auth.RegisterUserResponse
import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.domain.model.history.OrderDetailsResponse

/**
 *Created by Victor Loveday on 5/29/23
 */
interface OrderDetailsListener {
    fun onRequestStarted()
    fun onRequestFailed(errorMessage: String)
    fun onRequestSuccessful(response: OrderDetailsResponse)
}