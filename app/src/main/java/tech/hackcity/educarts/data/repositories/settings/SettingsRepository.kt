package tech.hackcity.educarts.data.repositories.settings

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.network.SafeApiRequest
import tech.hackcity.educarts.data.storage.SessionManager
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.data.storage.UserInfoManager
import tech.hackcity.educarts.domain.model.auth.User
import tech.hackcity.educarts.domain.model.location.RegionResponse
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

    suspend fun fetchRegions(): RegionResponse {
        return apiRequest { api.regionsAPI.fetchRegions() }
    }

    suspend fun editProfile(
        first_name: String,
        last_name: String,
        country_code: Int,
        phone_number: String,
        country_of_residence: String,
        institution_of_study: String,
        country_of_birth: String,
        state_of_birth: String,
        city_of_birth: String,
        profilePicture: MultipartBody.Part
    ): ProfileResponse {

        val firstName = RequestBody.create("text/plain".toMediaTypeOrNull(), first_name)
        val lastName = RequestBody.create("text/plain".toMediaTypeOrNull(), last_name)
        val countryCode = RequestBody.create("text/plain".toMediaTypeOrNull(), country_code.toString())
        val phoneNumber = RequestBody.create("text/plain".toMediaTypeOrNull(), phone_number)
        val countOfResidence = RequestBody.create("text/plain".toMediaTypeOrNull(), country_of_residence)
        val institutionOfStudy = RequestBody.create("text/plain".toMediaTypeOrNull(), institution_of_study)
        val countryOfBirth = RequestBody.create("text/plain".toMediaTypeOrNull(), country_of_birth)
        val state = RequestBody.create("text/plain".toMediaTypeOrNull(), state_of_birth)
        val city = RequestBody.create("text/plain".toMediaTypeOrNull(), city_of_birth)

        return apiRequest {
            api.settingsAPI.editProfile(
                firstName,
                lastName,
                countryCode,
                phoneNumber,
                countOfResidence,
                institutionOfStudy,
                countryOfBirth,
                state,
                city,
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