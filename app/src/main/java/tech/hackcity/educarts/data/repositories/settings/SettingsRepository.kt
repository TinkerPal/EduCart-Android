package tech.hackcity.educarts.data.repositories.settings

import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.network.SafeApiRequest
import tech.hackcity.educarts.data.storage.SessionManager
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.domain.model.settings.ChangePasswordResponse

/**
 *Created by Victor Loveday on 5/30/23
 */
class SettingsRepository(
    private val api: RetrofitInstance,
    private val sessionManager: SessionManager,
    private val sharedPreferenceManager: SharePreferencesManager
) : SafeApiRequest() {

    suspend fun changePassword(oldPassword: String, newPassword: String, confirmPassword: String): ChangePasswordResponse {
        return apiRequest {
            api.authenticationAPI.changePassword(oldPassword, newPassword, confirmPassword)
        }
    }
}