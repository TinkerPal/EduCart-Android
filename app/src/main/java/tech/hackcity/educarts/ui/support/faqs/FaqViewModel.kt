package tech.hackcity.educarts.ui.support.faqs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import tech.hackcity.educarts.data.network.ApiException
import tech.hackcity.educarts.data.repositories.support.SupportRepository
import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.NoInternetException
import tech.hackcity.educarts.uitls.SocketTimeOutException

/**
 *Created by Victor Loveday on 8/14/23
 */
class FaqViewModel(
    private val repository: SupportRepository
) : ViewModel() {

    var listener: FaqListener? = null

    fun fetchFAQs() {
        listener?.onRequestStarted()

        Coroutines.onMainWithScope(viewModelScope) {
            try {
                val response = repository.fetchFAQs()

                if (!response.error) {
                    listener?.onRequestSuccessful(response)
                } else {
                    listener?.onRequestFailed(response.errorMessage.toString())
                }

            } catch (e: ApiException) {
                listener?.onRequestFailed(e.errorMessage)
            }
        }
    }
}