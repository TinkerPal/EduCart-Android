package tech.hackcity.educarts.data.network.apis

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import tech.hackcity.educarts.domain.model.support.ConsultantProfileResponse
import tech.hackcity.educarts.domain.model.support.ConsultantsResponse
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

    @GET("support/consultants/")
    suspend fun fetchConsultants(): Response<ConsultantsResponse>

    @GET("support/consultants/{id}/")
    suspend fun fetchConsultantProfile(
//        @Query("id") id: Int
        @Path("id") id: Int
    ): Response<ConsultantProfileResponse>

    @GET("support/faqs/")
    suspend fun fetchFAQs(): Response<FaqsResponse>
}