package tech.hackcity.educarts.ui.auth.forgotPassword

import tech.hackcity.educarts.domain.model.auth.CreateNewPasswordResponse

/**
 *Created by Victor Loveday on 5/30/23
 */
interface CreateNewPasswordListener {
    fun onRequestStarted()
    fun onRequestFailed(message: String)
    fun onRequestSuccessful(response: CreateNewPasswordResponse)
}