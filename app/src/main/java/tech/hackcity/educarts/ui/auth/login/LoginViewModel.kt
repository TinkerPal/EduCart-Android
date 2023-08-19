package tech.hackcity.educarts.ui.auth.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.ApiException
import tech.hackcity.educarts.data.repositories.auth.AuthRepository
import tech.hackcity.educarts.domain.model.auth.User
import tech.hackcity.educarts.uitls.Coroutines
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
                    val user = User(
                        response.data.id,
                        response.data.first_name,
                        response.data.last_name,
                        response.data.phone_number,
                        response.data.country_of_residence,
                        response.data.email,
                    )
                    repository.saveUser(user)

                } else {
                    val errorMessage = errorMessageFetcher(response.errorMessage.toMutableList())
                    loginListener?.onRequestFailed(errorMessage)
                }

            } catch (e: ApiException) {
                loginListener?.onRequestFailed(e.message!!)
                return@onMainWithScope
            }

        }

    }
}