package tech.hackcity.educarts.data.network.apis

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.PUT
import tech.hackcity.educarts.domain.model.settings.ChangePasswordResponse

/**
 *Created by Victor Loveday on 8/18/23
 */
interface SettingsAPI {

    @FormUrlEncoded
    @PUT("settings/change-password/")
    suspend fun changePassword(
        @Field("old_password") old_password: String,
        @Field("new_password") new_password: String,
        @Field("confirm_password") confirm_password: String
    ): Response<ChangePasswordResponse> // change password

}