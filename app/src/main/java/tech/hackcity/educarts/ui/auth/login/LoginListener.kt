package tech.hackcity.educarts.ui.auth.login

import tech.hackcity.educarts.domain.model.auth.LoginResponse
import tech.hackcity.educarts.domain.model.auth.RegisterUserResponse

/**
 *Created by Victor Loveday on 5/29/23
 */
interface LoginListener {
    fun onRequestStarted()
    fun onRequestFailed(message: String)
    fun onRequestSuccessful(response: LoginResponse)
}