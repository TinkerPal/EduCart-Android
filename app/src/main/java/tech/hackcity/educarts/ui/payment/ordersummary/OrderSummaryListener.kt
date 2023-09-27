package tech.hackcity.educarts.ui.payment.ordersummary

import tech.hackcity.educarts.domain.model.auth.LoginResponse
import tech.hackcity.educarts.domain.model.auth.RegisterUserResponse
import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.domain.model.history.OrderDetailsResponse
import tech.hackcity.educarts.domain.model.payment.OrderSummaryResponse

/**
 *Created by Victor Loveday on 5/29/23
 */
interface OrderSummaryListener {
    fun onRequestStarted()
    fun onRequestFailed(errorMessage: String)
    fun onRequestSuccessful(response: OrderSummaryResponse)
}