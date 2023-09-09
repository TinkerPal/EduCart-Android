package tech.hackcity.educarts.data.network.apis

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import tech.hackcity.educarts.domain.model.support.ConsultationStep1Response
import tech.hackcity.educarts.domain.model.support.ConsultationStep2Response
import tech.hackcity.educarts.domain.model.support.FaqsResponse
import tech.hackcity.educarts.domain.model.support.MultipleChoiceResponse

/**
 *Created by Victor Loveday on 8/4/23
 */
interface SupportAPI {
    @GET("support/consultation/1/")
    suspend fun fetchConsultationTopics(): Response<MultipleChoiceResponse>

    @FormUrlEncoded
    @POST("support/consultation/1/")
    suspend fun submitConsultationStep1(
        @Field("user") user: String,
        @Field("consultation") consultation: String,
        @Field("details") details: String,
    ): Response<ConsultationStep1Response>

    @FormUrlEncoded
    @POST("support/consultation/2/")
    suspend fun submitConsultationStep2(
        @Field("consultation_way") consultation_way: String,
        @Field("phone_number") phone_number: String,
        @Field("time_of_consultation") time_of_consultation: String,
        @Field("date") date: String,
        @Field("time") time: String
    ): Response<ConsultationStep2Response>

    @GET("support/faqs/")
    suspend fun fetchFAQs(): Response<FaqsResponse>
}