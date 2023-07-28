package tech.hackcity.educarts.ui.settings.changePassword

import android.content.Context
import androidx.lifecycle.ViewModel
import retrofit2.HttpException
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.repositories.settings.SettingsRepository
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.NoInternetException
import java.io.IOException
import java.net.SocketTimeoutException

/**
 *Created by Victor Loveday on 5/30/23
 */
class ChangePasswordViewModel(
    private val repository: SettingsRepository
) : ViewModel() {

    var oldPassword: String? = null
    var newPassword: String? = null

    var changePasswordListener: ChangePasswordListener? = null

    fun onUpdateButtonClicked(context: Context) {
        changePasswordListener?.onRequestStarted()
        if (oldPassword.isNullOrEmpty() || newPassword.isNullOrEmpty()) {
            changePasswordListener?.onRequestFailed(context.resources.getString(R.string.field_can_not_be_empty))
            return
        }

        Coroutines.main {
            val response = try {
                repository.changePassword(oldPassword!!, newPassword!!, newPassword!!)

            } catch (e: IOException) {
                changePasswordListener?.onRequestFailed(e.message!!)
                return@main

            } catch (e: NoInternetException) {
                changePasswordListener?.onRequestFailed(e.message!!)
                return@main

            } catch (e: HttpException) {
                changePasswordListener?.onRequestFailed(e.message!!)
                return@main

            } catch (e: SocketTimeoutException) {
                changePasswordListener?.onRequestFailed(e.message!!)
                return@main

            }

            if (!response.error) {
                changePasswordListener?.onRequestSuccessful(response)

            } else {
                changePasswordListener?.onRequestFailed(response.message)
            }
        }

    }
}