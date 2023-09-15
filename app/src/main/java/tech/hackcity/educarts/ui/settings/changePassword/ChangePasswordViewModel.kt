package tech.hackcity.educarts.ui.settings.changePassword

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.ApiException
import tech.hackcity.educarts.data.repositories.settings.SettingsRepository
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.NoInternetException
import tech.hackcity.educarts.uitls.SocketTimeOutException

/**
 *Created by Victor Loveday on 5/30/23
 */
class ChangePasswordViewModel(
    private val repository: SettingsRepository
) : ViewModel() {

    var oldPassword: String? = null
    var newPassword: String? = null

    var changePasswordListener: ChangePasswordListener? = null

    fun changePassword(context: Context) {
        changePasswordListener?.onRequestStarted()

        if (oldPassword.isNullOrEmpty() || newPassword.isNullOrEmpty()) {
            changePasswordListener?.onRequestFailed(context.resources.getString(R.string.field_can_not_be_empty))
            return
        }

        Coroutines.onMainWithScope(viewModelScope) {
            try {
                val response = repository.changePassword(
                    oldPassword!!,
                    newPassword!!,
                    newPassword!!
                )

                if (!response.error) {
                    changePasswordListener?.onRequestSuccessful(response)

                } else {
                    changePasswordListener?.onRequestFailed(response.message)
                }

            } catch (e: ApiException) {
                changePasswordListener?.onRequestFailed(e.message!!)
            }
        }

    }
}