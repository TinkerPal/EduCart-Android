package tech.hackcity.educarts.ui.auth.verifyOTP

import tech.hackcity.educarts.domain.model.auth.RegenerateOTPResponse
import tech.hackcity.educarts.domain.model.auth.VerifyOTPResponse

/**
 *Created by Victor Loveday on 5/29/23
 */
interface VerifyOTPListener {
    fun onVerifyOTPRequestStarted()
    fun onRegenerateOTPRequestStarted()
    fun onVerifyRequestFailed(message: String)
    fun onRegenerateOTPRequestFailed(message: String)
    fun onVerifyOTPRequestSuccessful(response: VerifyOTPResponse)
    fun onRegenerateOTPRequestSuccessful(response: RegenerateOTPResponse)
}