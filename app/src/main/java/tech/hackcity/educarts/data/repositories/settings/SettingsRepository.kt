package tech.hackcity.educarts.data.repositories.settings

import okhttp3.MultipartBody
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.network.SafeApiRequest
import tech.hackcity.educarts.data.storage.SessionManager
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.data.storage.UserInfoManager
import tech.hackcity.educarts.domain.model.auth.User
import tech.hackcity.educarts.domain.model.settings.ChangePasswordResponse
import tech.hackcity.educarts.domain.model.settings.ProfileResponse
import tech.hackcity.educarts.uitls.Coroutines

/**
 *Created by Victor Loveday on 5/30/23
 */
class SettingsRepository(
    private val api: RetrofitInstance,
    private val sessionManager: SessionManager,
    private val sharedPreferenceManager: SharePreferencesManager,
    private val userInfoManager: UserInfoManager
) : SafeApiRequest() {

    suspend fun changePassword(
        oldPassword: String,
        newPassword: String,
        confirmPassword: String
    ): ChangePasswordResponse {
        return apiRequest {
            api.settingsAPI.changePassword(oldPassword, newPassword, confirmPassword)
        }
    }

    suspend fun editProfile(
        first_name: String,
        last_name: String,
        country_code: Int,
        phone_number: String,
        country_of_residence: String,
        institution_of_study: String,
        profilePicture: MultipartBody.Part
    ): ProfileResponse {
        return apiRequest {
            api.settingsAPI.editProfile(
                first_name,
                last_name,
                country_code,
                phone_number,
                country_of_residence,
                institution_of_study,
                profilePicture
            )
        }
    }

    suspend fun fetchProfile(): ProfileResponse {
        return apiRequest { api.settingsAPI.fetchProfile() }
    }

    fun saveUser(user: User) {
        Coroutines.onMain {
            userInfoManager.saveUser(user)
        }
    }

    fun clearAllTokens() {
        sessionManager.clearAllTokens()
    }

    fun clearSharedPreferences() {
        sharedPreferenceManager.clearSharedPreference()
    }

    suspend fun clearUser() {
        userInfoManager.clearUser()
    }
}