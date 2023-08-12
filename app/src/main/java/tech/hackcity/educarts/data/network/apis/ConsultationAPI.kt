package tech.hackcity.educarts.data.network.apis

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import tech.hackcity.educarts.domain.model.support.ConsultationResponse

/**
 *Created by Victor Loveday on 8/4/23
 */
interface ConsultationAPI {
    @GET("support/consultation/1/")
    suspend fun fetchConsultationTopics(): Response<ConsultationResponse>

    @FormUrlEncoded
    @POST("support/consultation/1/")
    suspend fun consultationStep1(
        @Field("user") user: String,
        @Field("consultation") consultation: String,
        @Field("details") details: String,
    )

    @FormUrlEncoded
    @POST("support/consultation/2/")
    suspend fun consultationStep2(
        @Field("consultation_way") consultation_way: String,
        @Field("phone_number") phone_number: String,
        @Field("time_of_consultation") time_of_consultation: String,
        @Field("date") date: String,
        @Field("time") time: String
    )
}