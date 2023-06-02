package tech.hackcity.educarts.ui.auth.forgotPassword

import android.content.Context
import androidx.lifecycle.ViewModel
import retrofit2.HttpException
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.repositories.auth.AuthRepository
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.NoInternetException
import java.io.IOException
import java.net.SocketTimeoutException

/**
 *Created by Victor Loveday on 5/29/23
 */
class ForgotPasswordViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    var email: String? = null

    var forgotPasswordListener: ForgotPasswordListener? = null

    fun onSendOTPButtonClicked(context: Context) {
        forgotPasswordListener?.onRequestStarted()

        if (email.isNullOrEmpty()) {
            forgotPasswordListener?.onRequestFailed(context.resources.getString(R.string.field_can_not_be_empty))
            return
        }

        Coroutines.main {
            val response = try {
                repository.forgotPassword(email!!)

            } catch (e: IOException) {
                forgotPasswordListener?.onRequestFailed(e.message!!)
                return@main

            } catch (e: NoInternetException) {
                forgotPasswordListener?.onRequestFailed(e.message!!)
                return@main

            } catch (e: HttpException) {
                forgotPasswordListener?.onRequestFailed(e.message!!)
                return@main

            } catch (e: SocketTimeoutException) {
                forgotPasswordListener?.onRequestFailed(e.message!!)
                return@main

            }

            if (!response.error) {
                forgotPasswordListener?.onRequestSuccessful(response)
                repository.saveUserId(response.data.id)
            } else {
                forgotPasswordListener?.onRequestFailed(response.message)
            }
        }

    }
}