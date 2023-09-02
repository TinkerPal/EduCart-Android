package tech.hackcity.educarts.ui.main.home

import tech.hackcity.educarts.domain.model.history.OrderHistoryResponse
import tech.hackcity.educarts.domain.model.settings.ProfileResponse

/**
 *Created by Victor Loveday on 8/19/23
 */
interface DashboardListener {
    fun onFetchOrderHistoryStarted()
    fun onFetchProfileRequestStarted()
    fun onFetchOrderHistoryFailed(message: String)
    fun onFetchProfileFailed(message: String)
    fun onFetchProfileRequestSuccessful(response: ProfileResponse)
    fun onFetchOrderHistorySuccessful(response: OrderHistoryResponse)
}