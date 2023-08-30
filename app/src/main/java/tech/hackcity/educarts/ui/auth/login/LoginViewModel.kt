package tech.hackcity.educarts.ui.auth.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.ApiException
import tech.hackcity.educarts.data.repositories.auth.AuthRepository
import tech.hackcity.educarts.domain.model.auth.LoginResponseData
import tech.hackcity.educarts.domain.model.auth.User
import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.domain.model.settings.ProfileResponseData
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.clearExtraCharacters
import tech.hackcity.educarts.uitls.errorMessageFetcher

/**
 *Created by Victor Loveday on 5/29/23
 */
class LoginViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    var email: String? = null
    var password: String? = null

    var loginListener: LoginListener? = null

    fun loginUser(context: Context) {
        loginListener?.onRequestStarted()

        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            loginListener?.onRequestFailed(context.resources.getString(R.string.field_can_not_be_empty))
            return
        }

        Coroutines.onMainWithScope(viewModelScope) {
            try {
                val response = repository.loginUser(
                    email!!,
                    password!!
                )

                if (!response.error) {
                    loginListener?.onRequestSuccessful(response)
                    repository.saveTokens(response.data.access, response.data.refresh)
                    repository.saveLoginStatus(true)
                    repository.saveUserId(response.data.id)

                    saveUser(response.data)

                } else {
                    Log.d("LoginError", "${response.errorMessage}")
                    loginListener?.onRequestFailed(response.errorMessage.toString())
                }

            } catch (e: ApiException) {
                loginListener?.onRequestFailed(e.message!!)
                return@onMainWithScope
            }

        }

    }

    private fun saveUser(data: LoginResponseData) {
        val user = User(
            clearExtraCharacters(data.id),
            data.profile_picture,
            clearExtraCharacters(data.first_name),
            clearExtraCharacters(data.last_name),
            data.country_code,
            data.phone_number,
            clearExtraCharacters(data.country_of_residence),
            clearExtraCharacters(data.email),
            data.profile_completed,
            data.is_restricted,
        )

        Log.d("UserInfo", "saved data : $user")

        Coroutines.onMain {
            repository.saveUser(user)
        }
    }

}