package tech.hackcity.educarts.ui.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import tech.hackcity.educarts.data.network.ApiException
import tech.hackcity.educarts.data.repositories.DashboardRepository
import tech.hackcity.educarts.domain.model.auth.User
import tech.hackcity.educarts.domain.model.settings.ProfileResponseData
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.NoInternetException
import tech.hackcity.educarts.uitls.SocketTimeOutException

/**
 *Created by Victor Loveday on 8/19/23
 */
class HomeViewModel(
    private val repository: DashboardRepository
): ViewModel() {

    var listener: DashboardListener? = null
    val userInfo: Flow<User> = repository.fetchUserInfo()

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
            }catch (e: NoInternetException) {
                listener?.onFetchProfileFailed("${e.message}")
            }catch (e: SocketTimeOutException) {
                listener?.onFetchProfileFailed("${e.message}")
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
            }catch (e: NoInternetException) {
                listener?.onFetchOrderHistoryFailed("${e.message}")
            }catch (e: SocketTimeOutException) {
                listener?.onFetchOrderHistoryFailed("${e.message}")
            }
        }
    }

    private fun saveUser(data: ProfileResponseData) {
        val user = User(
            data.id,
            data.profile_picture,
            data.first_name,
            data.last_name,
            data.country_code,
            data.phone_number,
            data.country_of_residence,
            data.email,
            data.profile_completed,
            data.is_restricted,
            data.free_consultation,
            data.institution_of_study,
            data.country_of_birth,
            data.state,
            data.city,
        )

        Log.d("UserInfo", "saved data : $user")

        Coroutines.onMain {
            repository.saveUser(user)
        }
    }

}