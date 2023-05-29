package tech.hackcity.educarts.ui.auth.verifyOTP

import tech.hackcity.educarts.domain.model.auth.RegisterUserResponse
import tech.hackcity.educarts.domain.model.auth.VerifyOTPResponse

/**
 *Created by Victor Loveday on 5/29/23
 */
interface VerifyOTPListener {
    fun onRequestStarted()
    fun onRequestFailed(message: String)
    fun onRequestSuccessful(response: VerifyOTPResponse)
}