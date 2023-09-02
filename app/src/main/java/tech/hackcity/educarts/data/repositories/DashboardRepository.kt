package tech.hackcity.educarts.data.repositories

import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.network.SafeApiRequest
import tech.hackcity.educarts.data.storage.UserInfoManager
import tech.hackcity.educarts.domain.model.auth.User
import tech.hackcity.educarts.domain.model.history.OrderHistoryResponse
import tech.hackcity.educarts.domain.model.settings.ProfileResponse
import tech.hackcity.educarts.uitls.Coroutines

/**
 *Created by Victor Loveday on 8/19/23
 */
class DashboardRepository(
    private val api: RetrofitInstance,
    private val userInfoManager: UserInfoManager
) : SafeApiRequest() {

    suspend fun fetchProfile(): ProfileResponse {
        return apiRequest { api.settingsAPI.fetchProfile() }
    }

    suspend fun fetchOrderHistory(): OrderHistoryResponse {
        return apiRequest { api.dashboardAPI.fetchOrderHistory() }
    }

    fun saveUser(user: User) {
        Coroutines.onMain {
            userInfoManager.saveUser(user)
        }
    }

}