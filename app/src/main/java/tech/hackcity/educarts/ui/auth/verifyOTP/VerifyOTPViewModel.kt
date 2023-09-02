package tech.hackcity.educarts.ui.auth.verifyOTP

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import retrofit2.HttpException
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.ApiException
import tech.hackcity.educarts.data.repositories.auth.AuthRepository
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.NoInternetException
import java.io.IOException
import java.net.SocketTimeoutException

/**
 *Created by Victor Loveday on 5/29/23
 */
class VerifyOTPViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    var otp: String? = null

    var verifyOTPListener: VerifyOTPListener? = null

    fun verifyOTPForNewAccount(context: Context) {
        verifyOTPListener?.onRequestStarted()

        if (otp.isNullOrEmpty()) {
            verifyOTPListener?.onRequestFailed(context.resources.getString(R.string.missing_field))
            return
        }

        Log.d("OTPError", "${repository.fetchUserId()} $otp")
        Coroutines.onMainWithScope(viewModelScope) {
            try {
                val response = repository.verifyOTPForNewAccount(repository.fetchUserId()!!, otp!!)

                if (!response.error) {
                    verifyOTPListener?.onRequestSuccessful(response)

                } else {
                    verifyOTPListener?.onRequestFailed(response.errorMessage.toString())
                }

            } catch (e: ApiException) {
                verifyOTPListener?.onRequestFailed(e.errorMessage)
                return@onMainWithScope
            }
        }
    }

    fun verifyOTPForPasswordReset(context: Context) {
        verifyOTPListener?.onRequestStarted()

        if (otp.isNullOrEmpty()) {
            verifyOTPListener?.onRequestFailed(context.resources.getString(R.string.missing_field))
            return
        }

        Coroutines.onMainWithScope(viewModelScope) {
            try {
                val response = repository.verifyOTPForPasswordReset(repository.fetchUserId()!!, otp!!)

                if (!response.error) {
                    verifyOTPListener?.onRequestSuccessful(response)

                } else {
                    verifyOTPListener?.onRequestFailed(response.errorMessage.toString())
                }

            } catch (e: ApiException) {
                verifyOTPListener?.onRequestFailed(e.errorMessage)
                return@onMainWithScope

            }

        }
    }
}