package tech.hackcity.educarts.ui.auth

import androidx.lifecycle.ViewModel
import tech.hackcity.educarts.data.repositories.auth.AuthRepository

/**
 *Created by Victor Loveday on 8/3/23
 */
class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    fun fetchLoginStatus(): Boolean {
        return repository.fetchLoginStatus()
    }
}