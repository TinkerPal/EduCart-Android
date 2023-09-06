package tech.hackcity.educarts.ui.auth.verifyOTP

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.ApiException
import tech.hackcity.educarts.data.repositories.auth.AuthRepository
import tech.hackcity.educarts.uitls.Coroutines

/**
 *Created by Victor Loveday on 5/29/23
 */
class VerifyOTPViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    var otp: String? = null

    var verifyOTPListener: VerifyOTPListener? = null

    fun verifyOTPForNewAccount(context: Context) {
        verifyOTPListener?.onVerifyOTPRequestStarted()

        if (otp.isNullOrEmpty()) {
            verifyOTPListener?.onVerifyRequestFailed(context.resources.getString(R.string.missing_field))
            return
        }

        Coroutines.onMainWithScope(viewModelScope) {
            try {
                val response = repository.verifyOTPForNewAccount(repository.fetchUserId()!!, otp!!)

                if (!response.error) {
                    verifyOTPListener?.onVerifyOTPRequestSuccessful(response)
                }

            } catch (e: ApiException) {
                verifyOTPListener?.onVerifyRequestFailed(e.errorMessage)
                return@onMainWithScope
            }
        }
    }

    fun verifyOTPForPasswordReset(context: Context) {
        verifyOTPListener?.onVerifyOTPRequestStarted()

        if (otp.isNullOrEmpty()) {
            verifyOTPListener?.onVerifyRequestFailed(context.resources.getString(R.string.missing_field))
            return
        }

        Coroutines.onMainWithScope(viewModelScope) {
            try {
                val response = repository.verifyOTPForPasswordReset(repository.fetchUserId()!!, otp!!)

                if (!response.error) {
                    verifyOTPListener?.onVerifyOTPRequestSuccessful(response)
                }

            } catch (e: ApiException) {
                verifyOTPListener?.onVerifyRequestFailed(e.errorMessage)
                return@onMainWithScope

            }

        }
    }

    fun regenerateOTP() {
        verifyOTPListener?.onRegenerateOTPRequestStarted()

        Coroutines.onMainWithScope(viewModelScope) {
            try {
                val response = repository.regenerateOTP(repository.fetchUserId()!!)

                if (!response.error) {
                    verifyOTPListener?.onRegenerateOTPRequestSuccessful(response)
                }

            } catch (e: ApiException) {
                verifyOTPListener?.onRegenerateOTPRequestFailed(e.errorMessage)
                return@onMainWithScope

            }

        }
    }


}