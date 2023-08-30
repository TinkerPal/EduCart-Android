package tech.hackcity.educarts.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import tech.hackcity.educarts.data.network.ApiException
import tech.hackcity.educarts.data.repositories.MainRepository
import tech.hackcity.educarts.domain.model.auth.User
import tech.hackcity.educarts.domain.model.settings.ProfileResponseData
import tech.hackcity.educarts.uitls.Coroutines

/**
 *Created by Victor Loveday on 8/29/23
 */
class MainViewModel(private val repository: MainRepository) : ViewModel() {

    var listener: MainListener? = null

    fun fetchProfile() {
        listener?.onFetchProfileRequestStarted()

        Coroutines.onMainWithScope(viewModelScope) {
            try {
                val response = repository.fetchProfile()

                if (!response.error) {
                    listener?.onFetchProfileRequestSuccessful(response)
                    saveUser(response.data)

                } else {
                    listener?.onRequestFailed(response.errorMessage.toString())
                }

            } catch (e: ApiException) {
                listener?.onRequestFailed(e.message!!)
                return@onMainWithScope
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
            data.institution_of_study,
        )

        Coroutines.onMain {
            repository.saveUser(user)
        }
    }

}