package tech.hackcity.educarts.data.network.apis

import retrofit2.http.POST

/**
 *Created by Victor Loveday on 9/27/23
 */
interface FlutterWaveAPI {

    @POST("charges?type=card")
    suspend fun payWithCard()
}