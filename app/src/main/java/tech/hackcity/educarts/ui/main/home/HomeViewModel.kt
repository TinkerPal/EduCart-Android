package tech.hackcity.educarts.ui.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import tech.hackcity.educarts.data.network.ApiException
import tech.hackcity.educarts.data.repositories.DashboardRepository
import tech.hackcity.educarts.domain.model.auth.User
import tech.hackcity.educarts.domain.model.settings.ProfileResponseData
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.clearExtraCharacters

/**
 *Created by Victor Loveday on 8/19/23
 */
class HomeViewModel(
    private val repository: DashboardRepository
): ViewModel() {

    var listener: DashboardListener? = null

    fun fetchProfile() {
        listener?.onFetchProfileRequestStarted()

        Coroutines.onMainWithScope(viewModelScope) {
            try {
                val response = repository.fetchProfile()

                if (!response.error) {
                    listener?.onFetchProfileRequestSuccessful(response)
                    saveUser(response.data)

                } else {
                    listener?.onFetchProfileFailed(response.errorMessage.toString())
                }

            } catch (e: ApiException) {
                listener?.onFetchProfileFailed(e.message!!)
                return@onMainWithScope
            }
        }

    }

    fun fetchOrderHistory() {
        listener?.onFetchOrderHistoryStarted()

        Coroutines.onMainWithScope(viewModelScope) {
            try {
                val response = repository.fetchOrderHistory()

                if (!response.error) {
                    listener?.onFetchOrderHistorySuccessful(response)
                    Log.d("OrderHistory", "$response")

                }else {
                    listener?.onFetchOrderHistoryFailed(response.errorMessage.toString())
                }

            }catch (e: ApiException) {
                listener?.onFetchOrderHistoryFailed(e.errorMessage)
                return@onMainWithScope
            }
        }
    }

    private fun saveUser(data: ProfileResponseData) {
        val user = User(
            clearExtraCharacters(data.id),
            data.profile_picture,
            clearExtraCharacters(data.first_name),
            clearExtraCharacters(data.last_name),
            data.country_code,
            clearExtraCharacters(data.phone_number),
            clearExtraCharacters(data.country_of_residence),
            clearExtraCharacters(data.email),
            data.profile_completed,
            data.is_restricted,
            data.institution_of_study?.let { clearExtraCharacters(it) },
            data.country_of_birth?.let { clearExtraCharacters(it) },
            data.state?.let { clearExtraCharacters(it) },
            data.city?.let { clearExtraCharacters(it) },
        )

        Log.d("UserInfo", "saved data : $user")

        Coroutines.onMain {
            repository.saveUser(user)
        }
    }

}