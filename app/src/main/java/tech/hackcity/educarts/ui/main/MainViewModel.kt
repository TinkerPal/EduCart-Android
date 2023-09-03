package tech.hackcity.educarts.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import tech.hackcity.educarts.data.network.ApiException
import tech.hackcity.educarts.data.repositories.MainRepository
import tech.hackcity.educarts.domain.model.auth.User
import tech.hackcity.educarts.domain.model.settings.ProfileResponseData
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.clearExtraCharacters

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