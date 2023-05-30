package tech.hackcity.educarts.ui.auth.forgotPassword

import tech.hackcity.educarts.domain.model.auth.ForgotPasswordResponse

/**
 *Created by Victor Loveday on 5/29/23
 */
interface ForgotPasswordListener {
    fun onRequestStarted()
    fun onRequestFailed(message: String)
    fun onRequestSuccessful(response: ForgotPasswordResponse)
}