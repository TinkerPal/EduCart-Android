package tech.hackcity.educarts.ui.auth.verifyOTP

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
class VerifyOTPViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    var id: String? = null
    var otp: String? = null

    var verifyOTPListener: VerifyOTPListener? = null

    fun onVerifyButtonClickedListener(context: Context) {
        verifyOTPListener?.onRequestStarted()
        if (id.isNullOrEmpty() || otp.isNullOrEmpty()) {
            verifyOTPListener?.onRequestFailed(context.resources.getString(R.string.missing_field))
            return
        }

        Coroutines.main {
            val response = try {
                repository.verifyOTPForNewAccount(id!!, otp!!)

            } catch (e: IOException) {
                verifyOTPListener?.onRequestFailed(e.message!!)
                return@main

            } catch (e: NoInternetException) {
                verifyOTPListener?.onRequestFailed(e.message!!)
                return@main

            } catch (e: HttpException) {
                verifyOTPListener?.onRequestFailed(e.message!!)
                return@main

            } catch (e: SocketTimeoutException) {
                verifyOTPListener?.onRequestFailed(e.message!!)
                return@main

            }

            if (!response.error) {
                verifyOTPListener?.onRequestSuccessful(response)

            } else {
                val errorMessage = errorMessageFetcher(response.errorMessage.toMutableList())
                verifyOTPListener?.onRequestFailed(errorMessage)
            }
        }
    }
}