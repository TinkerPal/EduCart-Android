package tech.hackcity.educarts.ui.auth.register

import tech.hackcity.educarts.domain.model.auth.RegisterUserResponse

/**
 *Created by Victor Loveday on 5/24/23
 */
interface CreatePersonalAccountListener {
    fun onRequestStarted()
    fun onRequestFailed(message: String)
    fun onRequestSuccessful(response: RegisterUserResponse)

}