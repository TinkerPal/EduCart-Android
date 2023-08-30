package tech.hackcity.educarts.ui.auth.forgotPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.hackcity.educarts.data.repositories.auth.AuthRepository

/**
 *Created by Victor Loveday on 5/30/23
 */
@Suppress("UNCHECKED_CAST")
class ResetPasswordViewModelFactory(
    private val repository: AuthRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ResetPasswordViewModel(repository) as T
    }
}