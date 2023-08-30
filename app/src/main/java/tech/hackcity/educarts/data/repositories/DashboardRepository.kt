package tech.hackcity.educarts.data.repositories

import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.network.SafeApiRequest
import tech.hackcity.educarts.domain.model.history.OrderHistoryResponse

/**
 *Created by Victor Loveday on 8/19/23
 */
class DashboardRepository(
    private val api: RetrofitInstance
) : SafeApiRequest() {

    suspend fun fetchOrderHistory(): OrderHistoryResponse {
        return apiRequest { api.dashboardAPI.fetchOrderHistory() }
    }
}