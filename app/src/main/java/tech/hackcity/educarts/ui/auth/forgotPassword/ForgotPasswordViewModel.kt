package tech.hackcity.educarts.ui.auth.forgotPassword

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.ApiException
import tech.hackcity.educarts.data.repositories.auth.AuthRepository
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.HTTPException
import tech.hackcity.educarts.uitls.NoInternetException
import tech.hackcity.educarts.uitls.SocketTimeOutException
import java.net.UnknownHostException

/**
 *Created by Victor Loveday on 5/29/23
 */
class ForgotPasswordViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    var email: String? = null

    var forgotPasswordListener: ForgotPasswordListener? = null

    fun forgotPassword(context: Context) {
        forgotPasswordListener?.onRequestStarted()

        if (email.isNullOrEmpty()) {
            forgotPasswordListener?.onRequestFailed(context.resources.getString(R.string.field_can_not_be_empty))
            return
        }

        Coroutines.onMainWithScope(viewModelScope) {
            try {
                val response = repository.forgotPassword(email!!)

                if (!response.error) {
                    forgotPasswordListener?.onRequestSuccessful(response)
                    repository.saveUserId(response.data.id)
                } else {
                    forgotPasswordListener?.onRequestFailed(response.errorMessage.toString())
                }

            } catch (e: ApiException) {
                forgotPasswordListener?.onRequestFailed(e.errorMessage)
            }
        }

    }
}