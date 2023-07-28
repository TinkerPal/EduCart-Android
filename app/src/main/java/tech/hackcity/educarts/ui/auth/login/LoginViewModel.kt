package tech.hackcity.educarts.ui.auth.login

import android.content.Context
import androidx.lifecycle.ViewModel
import retrofit2.HttpException
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.repositories.auth.AuthRepository
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.NoInternetException
import tech.hackcity.educarts.uitls.errorMessageFetcher
import java.io.IOException
import java.net.SocketTimeoutException

/**
 *Created by Victor Loveday on 5/29/23
 */
class LoginViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    var email: String? = null
    var password: String? = null

    var loginListener: LoginListener? = null

    fun onLoginButtonClicked(context: Context) {
        loginListener?.onRequestStarted()

        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            loginListener?.onRequestFailed(context.resources.getString(R.string.field_can_not_be_empty))
            return
        }

        Coroutines.main {
            val response = try {
                repository.loginUser(
                    email!!,
                    password!!
                )

            } catch (e: IOException) {
                loginListener?.onRequestFailed(e.message!!)
                return@main

            } catch (e: NoInternetException) {
                loginListener?.onRequestFailed(e.message!!)
                return@main

            } catch (e: HttpException) {
                loginListener?.onRequestFailed(e.message!!)
                return@main

            } catch (e: SocketTimeoutException) {
                loginListener?.onRequestFailed(e.message!!)
                return@main

            }

            if (!response.error) {
                loginListener?.onRequestSuccessful(response)
                repository.saveAuthToken(response.data.access)

            } else {
                val errorMessage = errorMessageFetcher(response.errorMessage.toMutableList())
                loginListener?.onRequestFailed(errorMessage)
            }
        }

    }
}