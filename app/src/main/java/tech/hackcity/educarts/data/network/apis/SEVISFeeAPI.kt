package tech.hackcity.educarts.data.network.apis

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep1Response

/**
 *Created by Victor Loveday on 8/3/23
 */
interface SEVISFeeAPI {

    @Multipart
    @POST("api/v1/sevis/information/1/")
    fun sevisFeeStep1(
        @Part("user") user: RequestBody,
        @Part("sevis_id") sevis_id: RequestBody,
        @Part("last_name") last_name: RequestBody,
        @Part("given_name") given_name: RequestBody,
        @Part("date_of_birth") date_of_birth: RequestBody,
        @Part form: MultipartBody.Part,
        @Part passport: MultipartBody.Part,
        @Part international_passport: MultipartBody.Part,
    ): Response<SEVISFeeStep1Response>
}