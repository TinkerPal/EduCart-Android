package tech.hackcity.educarts.data.network.apis

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import tech.hackcity.educarts.domain.model.history.OrderDetailsResponse
import tech.hackcity.educarts.domain.model.location.RegionResponse

/**
 *Created by Victor Loveday on 8/4/23
 */
interface RegionsAPI {

    @GET("countries/")
    suspend fun fetchRegions(): Response<RegionResponse>

}