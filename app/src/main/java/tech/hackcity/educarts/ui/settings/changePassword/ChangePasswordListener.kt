package tech.hackcity.educarts.ui.settings.changePassword

import tech.hackcity.educarts.domain.model.auth.LoginResponse
import tech.hackcity.educarts.domain.model.settings.ChangePasswordResponse

/**
 *Created by Victor Loveday on 5/30/23
 */
interface ChangePasswordListener {
    fun onRequestStarted()
    fun onRequestFailed(message: String)
    fun onRequestSuccessful(response: ChangePasswordResponse)
}