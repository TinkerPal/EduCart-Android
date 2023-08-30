package tech.hackcity.educarts.ui.settings.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.hackcity.educarts.data.repositories.settings.SettingsRepository

/**
 *Created by Victor Loveday on 8/25/23
 */
@Suppress("UNCHECKED_CAST")
class ProfileViewModelFactory(
    private val repository: SettingsRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(repository) as T
    }
}