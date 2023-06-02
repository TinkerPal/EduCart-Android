package tech.hackcity.educarts.ui.auth.verifyOTP

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.hackcity.educarts.data.repositories.auth.AuthRepository

/**
 *Created by Victor Loveday on 5/29/23
 */
@Suppress("UNCHECKED_CAST")
class VerifyOTPViewModelFactory(
    private val repository: AuthRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return VerifyOTPViewModel(repository) as T
    }
}