package tech.hackcity.educarts.data.network.apis

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import tech.hackcity.educarts.domain.model.history.OrderDetailsResponse
import tech.hackcity.educarts.domain.model.payment.OrderSummaryResponse
import tech.hackcity.educarts.domain.model.payment.paymentreceipt.TransferReceiptResponse


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

    @Multipart
    @POST("payment/upload-receipt/")
    suspend fun uploadPaymentReceipt(
        @Part receipt: MultipartBody.Part,
        @Part("order_id") order_id: RequestBody
    ): Response<TransferReceiptResponse>

}