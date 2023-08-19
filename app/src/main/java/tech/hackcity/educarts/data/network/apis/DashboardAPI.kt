package tech.hackcity.educarts.data.network.apis

import retrofit2.Response
import retrofit2.http.GET
import tech.hackcity.educarts.domain.model.history.OrderHistoryResponse

/**
 *Created by Victor Loveday on 8/19/23
 */
interface DashboardAPI {
    @GET("dashboard/order-history/")
    suspend fun fetchOrderHistory(): Response<OrderHistoryResponse>
}