package tech.hackcity.educarts.ui.main.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import tech.hackcity.educarts.data.network.ApiException
import tech.hackcity.educarts.data.repositories.DashboardRepository
import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.uitls.Coroutines

/**
 *Created by Victor Loveday on 8/19/23
 */
class HomeViewModel(
    private val repository: DashboardRepository
): ViewModel() {

    var listener: DashboardListener? = null

    fun fetchOrderHistory() {
        listener?.onFetchOrderHistoryStarted()

        Coroutines.onMainWithScope(viewModelScope) {
            try {
                val response = repository.fetchOrderHistory()

                if (!response.error) {
                    listener?.onFetchOrderHistorySuccessful(response)

                }else {
                    listener?.onFetchOrderHistoryFailed(response.errorMessage)
                }

            }catch (e: ApiException) {
                listener?.onFetchOrderHistoryFailed(listOf(ErrorMessage(e.errorCode, e.message!!)))
                return@onMainWithScope
            }
        }
    }
}