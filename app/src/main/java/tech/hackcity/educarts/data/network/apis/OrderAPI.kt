package tech.hackcity.educarts.data.network.apis

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import tech.hackcity.educarts.domain.model.history.OrderDetailsResponse
import tech.hackcity.educarts.domain.model.payment.OrderSummaryResponse
import tech.hackcity.educarts.domain.model.support.ConsultantProfileResponse
import tech.hackcity.educarts.domain.model.support.ConsultantsResponse
import tech.hackcity.educarts.domain.model.support.ConsultationStep1Response
import tech.hackcity.educarts.domain.model.support.ConsultationStep2Response
import tech.hackcity.educarts.domain.model.support.FaqsResponse
import tech.hackcity.educarts.domain.model.support.MultipleChoiceResponse

/**
 *Created by Victor Loveday on 8/4/23
 */
interface OrderAPI {

    @FormUrlEncoded
    @POST("dashboard/track-order/")
    suspend fun trackOrder(
        @Field("order_id") order_id: String
    ): Response<OrderDetailsResponse>

    @GET("order-summary/{order_type}/")
    suspend fun fetchOrderSummary(
        @Path("order_type") order_type: String
    ): Response<OrderSummaryResponse>

}