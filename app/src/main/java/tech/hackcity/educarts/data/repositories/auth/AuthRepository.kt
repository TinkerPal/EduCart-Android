package tech.hackcity.educarts.data.repositories.auth

import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.network.SafeApiRequest
import tech.hackcity.educarts.data.storage.SessionManager
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.data.storage.UserInfoManager
import tech.hackcity.educarts.domain.model.auth.*
import tech.hackcity.educarts.uitls.Coroutines

/**
 *Created by Victor Loveday on 5/24/23
 */
class AuthRepository(
    private val api: RetrofitInstance,
    private val sessionManager: SessionManager,
    private val sharedPreferenceManager: SharePreferencesManager,
    private val userInfoManager: UserInfoManager
) : SafeApiRequest() {

    suspend fun registerPersonalAccountUser(
        userType: String, email: String,
        firstName: String, lastName: String,
        countryOfResidence: String, countryCode: Int,
        phoneNumber: String, password: String
    ): RegisterUserResponse {

        return apiRequest {
            api.authenticationAPI.registerPersonalUserAccount(
                userType,
                email,
                firstName,
                lastName,
                countryOfResidence,
                countryCode,
                phoneNumber,
                password
            )
        }
    }

    suspend fun verifyOTPForNewAccount(id: String, otp: String): VerifyOTPResponse {
        return apiRequest { api.authenticationAPI.verifyOTPForNewAccount(id, otp) }
    }

    suspend fun verifyOTPForPasswordReset(id: String, otp: String): VerifyOTPResponse {
        return apiRequest { api.authenticationAPI.verifyOTPForPasswordReset(id, otp) }
    }

    suspend fun regenerateOTP(id: String): RegenerateOTPResponse {
        return apiRequest { api.authenticationAPI.regenerateOTP(id) }
    }

    suspend fun loginUser(email: String, password: String): LoginResponse {
        return apiRequest { api.authenticationAPI.loginUser(email, password) }
    }

    suspend fun forgotPassword(email: String): ForgotPasswordResponse {
        return apiRequest { api.authenticationAPI.forgotPassword(email) }
    }

    suspend fun resetPassword(id: String, password: String): CreateNewPasswordResponse {
        return apiRequest { api.authenticationAPI.resetPassword(id, password, password) }
    }

    fun saveUser(user: User) {
        Coroutines.onMain {
            userInfoManager.saveUser(user)
        }
    }

    fun saveUserId(id: String) {
        Coroutines.onMain {
            sharedPreferenceManager.saveUserId(id)
        }

    }

    fun fetchUserId(): String? {
        return sharedPreferenceManager.fetchUserId()
    }

    fun saveTokens(access: String, refresh: String) {
        Coroutines.onMain {
            sessionManager.saveTokens(access,refresh)
        }
    }

    fun saveLoginStatus(isLoggedIn: Boolean) {
        sharedPreferenceManager.saveLoginStatus(isLoggedIn)
    }

    fun fetchLoginStatus(): Boolean {
        return sharedPreferenceManager.fetchLoginStatus()
    }
}