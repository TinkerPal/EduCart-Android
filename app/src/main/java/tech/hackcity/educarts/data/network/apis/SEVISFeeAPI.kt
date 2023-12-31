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
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISCategoryResponse
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep1Response
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep2Response
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep3Response
import tech.hackcity.educarts.domain.model.payment.sevis.SevisCouponStep1Response
import tech.hackcity.educarts.domain.model.payment.sevis.SevisCouponStep2Response
import tech.hackcity.educarts.domain.model.support.MultipleChoiceResponse

/**
 *Created by Victor Loveday on 8/3/23
 */
interface SEVISFeeAPI {

    @Multipart
    @POST("sevis/information/1/")
    suspend fun sevisFeeStep1(
        @Part form: MultipartBody.Part,
        @Part passport: MultipartBody.Part,
        @Part international_passport: MultipartBody.Part,
        @Part("user") user: RequestBody,
        @Part("sevis_id") sevis_id: RequestBody,
        @Part("last_name") last_name: RequestBody,
        @Part("given_name") given_name: RequestBody,
        @Part("date_of_birth") date_of_birth: RequestBody
    ): Response<SEVISFeeStep1Response>

    @FormUrlEncoded
    @POST("sevis/information/2/")
    suspend fun sevisFeeStep2(
        @Field("form_type") form_type: String,
        @Field("category") category: String,
        @Field("email") email: String,
        @Field("phone_number") phone_number: String,
        @Field("country_of_citizenship") country_of_citizenship: String,
        @Field("country_of_birth") country_of_birth: String
    ): Response<SEVISFeeStep2Response>

    @FormUrlEncoded
    @POST("sevis/information/3/")
    suspend fun sevisFeeStep3(
        @Field("street_address_1") street_address_1: String,
        @Field("street_address_2") street_address_2: String,
        @Field("country") country: String,
        @Field("state") state: String,
        @Field("city") city: String
    ): Response<SEVISFeeStep3Response>

    @GET("sevis/information/2/")
    suspend fun fetchSevisCategory(): Response<MultipleChoiceResponse>

    @Multipart
    @POST("sevis/coupon/1/")
    suspend fun sevisCouponStep1(
        @Part sevis_coupon: MultipartBody.Part,
        @Part("user") user: RequestBody,
        @Part("form_type") form_type: RequestBody,
    ): Response<SevisCouponStep1Response>

    @Multipart
    @POST("sevis/coupon/2/")
    suspend fun sevisCouponStep2(
        @Part form: MultipartBody.Part,
        @Part passport: MultipartBody.Part,
        @Part international_passport: MultipartBody.Part,
        @Part("sevis_id") sevis_id: RequestBody,
        @Part("last_name") last_name: RequestBody,
        @Part("given_name") given_name: RequestBody,
        @Part("date_of_birth") date_of_birth: RequestBody
    ): Response<SevisCouponStep2Response>
}