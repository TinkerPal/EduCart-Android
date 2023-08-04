package tech.hackcity.educarts.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.hackcity.educarts.data.repositories.settings.SettingsRepository

/**
 *Created by Victor Loveday on 8/4/23
 */
@Suppress("UNCHECKED_CAST")
class SettingsViewModelFactory(
    private val repository: SettingsRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsViewModel(repository) as T
    }
}