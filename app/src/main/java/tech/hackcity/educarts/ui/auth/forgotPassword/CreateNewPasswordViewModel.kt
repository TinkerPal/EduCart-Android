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
 *Created by Victor Loveday on 5/30/23
 */
class CreateNewPasswordViewModel(
    private val repository: AuthRepository
): ViewModel() {

    var id: String? = null
    var password: String? = null

    var createNewPasswordListener: CreateNewPasswordListener? = null

    fun onResetPasswordButtonClicked(context: Context) {
        createNewPasswordListener?.onRequestStarted()

        if (id.isNullOrEmpty() || password.isNullOrEmpty()) {
            createNewPasswordListener?.onRequestFailed(context.resources.getString(R.string.field_can_not_be_empty))
            return
        }

        Coroutines.main {
            val response = try {
                repository.createNewPassword(id!!, password!!)

            } catch (e: IOException) {
                createNewPasswordListener?.onRequestFailed(e.message!!)
                return@main

            } catch (e: NoInternetException) {
                createNewPasswordListener?.onRequestFailed(e.message!!)
                return@main

            } catch (e: HttpException) {
                createNewPasswordListener?.onRequestFailed(e.message!!)
                return@main

            } catch (e: SocketTimeoutException) {
                createNewPasswordListener?.onRequestFailed(e.message!!)
                return@main

            }

            if (!response.error) {
                createNewPasswordListener?.onRequestSuccessful(response)
                repository.saveUserId(response.data.id)
            } else {
                createNewPasswordListener?.onRequestFailed(response.message)
            }
        }
    }
}