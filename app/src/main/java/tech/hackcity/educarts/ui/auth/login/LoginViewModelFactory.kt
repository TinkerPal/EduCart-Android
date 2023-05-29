package tech.hackcity.educarts.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.hackcity.educarts.data.repositories.auth.AuthRepository

/**
 *Created by Victor Loveday on 5/29/23
 */
@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(
    private val repository: AuthRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }
}