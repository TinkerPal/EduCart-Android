package tech.hackcity.educarts.ui.settings.changePassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.hackcity.educarts.data.repositories.settings.SettingsRepository

/**
 *Created by Victor Loveday on 5/30/23
 */
@Suppress("UNCHECKED_CAST")
class ChangePasswordViewModelFactory(
    private val repository: SettingsRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChangePasswordViewModel(repository) as T
    }
}