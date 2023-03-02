package tech.hackcity.educarts.uitls

import androidx.biometric.BiometricPrompt

/**
 *Created by Victor Loveday on 3/2/23
 */

interface BiometricAuthListener {

    fun onBiometricAuthenticateError(error: Int,errMsg: String)
    fun onBiometricAuthenticateSuccess(result: BiometricPrompt.AuthenticationResult)

}
