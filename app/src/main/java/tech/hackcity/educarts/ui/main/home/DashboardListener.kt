package tech.hackcity.educarts.ui.main.home

import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.domain.model.history.OrderHistoryResponse

/**
 *Created by Victor Loveday on 8/19/23
 */
interface DashboardListener {
    fun onFetchOrderHistoryStarted()
    fun onFetchOrderHistoryFailed(message: List<ErrorMessage>)
    fun onFetchOrderHistorySuccessful(response: OrderHistoryResponse)
}