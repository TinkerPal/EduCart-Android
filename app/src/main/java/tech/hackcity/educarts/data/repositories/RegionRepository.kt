package tech.hackcity.educarts.data.repositories

import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.network.SafeApiRequest
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.domain.model.location.RegionResponse

/**
 *Created by Victor Loveday on 9/21/23
 */
class RegionRepository(
    private val api: RetrofitInstance,
    private val sharePreferencesManager: SharePreferencesManager
) : SafeApiRequest() {

    suspend fun fetchRegions(): RegionResponse {
        return apiRequest { api.regionsAPI.fetchRegions() }
    }

    fun saveAppSetupStatus(isAppSetup: Boolean) {
        sharePreferencesManager.saveAppSetupStatus(isAppSetup)
    }

    fun fetchIsGetStartedPressed(): Boolean{
        return sharePreferencesManager.fetchIsGetStartedPressed()
    }

    fun fetchLoginStatus(): Boolean {
        return sharePreferencesManager.fetchLoginStatus()
    }
}