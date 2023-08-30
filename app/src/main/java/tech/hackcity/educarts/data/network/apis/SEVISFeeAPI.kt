package tech.hackcity.educarts.data.network.apis

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep1Response
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep2Response
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep3Response
import tech.hackcity.educarts.domain.model.settings.ProfileResponse

/**
 *Created by Victor Loveday on 8/3/23
 */
interface SEVISFeeAPI {

    @Multipart
    @POST("sevis/information/1/")
    suspend fun sevisFeeStep1(
        @Part("user") user: String,
        @Part("sevis_id") sevis_id: String,
        @Part("last_name") last_name: String,
        @Part("given_name") given_name: String,
        @Part("date_of_birth") date_of_birth: String,
        @Part form: MultipartBody.Part,
        @Part passport: MultipartBody.Part,
        @Part international_passport: MultipartBody.Part
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
}