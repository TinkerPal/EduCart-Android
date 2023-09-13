package tech.hackcity.educarts.ui.auth.forgotPassword

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.ApiException
import tech.hackcity.educarts.data.repositories.auth.AuthRepository
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.NoInternetException
import tech.hackcity.educarts.uitls.SocketTimeOutException

/**
 *Created by Victor Loveday on 5/30/23
 */
class ResetPasswordViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    var id: String? = null
    var password: String? = null

    var resetPasswordListener: ResetPasswordListener? = null

    fun resetPassword(context: Context) {
        resetPasswordListener?.onRequestStarted()

        if (id.isNullOrEmpty() || password.isNullOrEmpty()) {
            resetPasswordListener?.onRequestFailed(context.resources.getString(R.string.field_can_not_be_empty))
            return
        }

        Coroutines.onMainWithScope(viewModelScope) {
            try {
                val response = repository.resetPassword(id!!, password!!)

                if (!response.error) {
                    resetPasswordListener?.onRequestSuccessful(response)
                    repository.saveUserId(response.data.id)
                } else {
                    resetPasswordListener?.onRequestFailed(response.errorMessage.toString())
                }

            } catch (e: ApiException) {
                resetPasswordListener?.onRequestFailed(e.errorMessage)
            }catch (e: NoInternetException) {
                resetPasswordListener?.onRequestFailed("${e.message}")
            }catch (e: SocketTimeOutException) {
                resetPasswordListener?.onRequestFailed("${e.message}")
            }

        }
    }
}