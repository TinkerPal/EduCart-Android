package tech.hackcity.educarts.ui.settings

import androidx.lifecycle.ViewModel
import tech.hackcity.educarts.data.repositories.settings.SettingsRepository
import tech.hackcity.educarts.uitls.Coroutines

/**
 *Created by Victor Loveday on 8/4/23
 */
class SettingsViewModel(
    private val repository: SettingsRepository
) : ViewModel() {

    fun clearAllTokens() {
        Coroutines.main {
            repository.clearAllTokens()
        }
    }

    fun clearSharedPreferences() {
        Coroutines.main {
            repository.clearSharedPreferences()
        }
    }

    fun clearUser() {
        Coroutines.main {
            repository.clearUser()
        }
    }
}