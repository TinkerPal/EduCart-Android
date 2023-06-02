package tech.hackcity.educarts.ui.settings.pin

import android.content.Context
import androidx.lifecycle.ViewModel
import tech.hackcity.educarts.data.repositories.settings.SettingsRepository

/**
 *Created by Victor Loveday on 5/31/23
 */
class ChangePinViewModel(
    private val repository: SettingsRepository
) : ViewModel() {

    var pin: String? = null
    var securityQuestion1: String? = null
    var securityQuestion2: String? = null
    var securityQuestionAnswer1: String? = null
    var securityQuestionAnswer2: String? = null

    fun onCreatePINButtonClicked(context: Context) {

    }
}