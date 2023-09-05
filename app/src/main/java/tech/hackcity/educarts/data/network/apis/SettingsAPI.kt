package tech.hackcity.educarts.data.network.apis

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.PUT
import retrofit2.http.Part
import tech.hackcity.educarts.domain.model.settings.ChangePasswordResponse
import tech.hackcity.educarts.domain.model.settings.ProfileResponse

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

    @GET("account-setup/profile/")
    suspend fun fetchProfile():Response<ProfileResponse>

//    @Multipart
//    @PATCH("account-setup/profile/")
//    suspend fun editProfile(
//        @Part("first_name") first_name: String,
//        @Part("last_name") last_name: String,
//        @Part("country_code") country_code: Int,
//        @Part("phone_number") phone_number: String,
//        @Part("country_of_residence") country_of_residence: String,
//        @Part("institution_of_study") institution_of_study: String,
//        @Part profile_picture: MultipartBody.Part?
//    ): Response<ProfileResponse>


    @Multipart
    @PATCH("account-setup/profile/")
    suspend fun editProfile(
        @Part("first_name") first_name: RequestBody,
        @Part("last_name") last_name: RequestBody,
        @Part("country_code") country_code: RequestBody,
        @Part("phone_number") phone_number: RequestBody,
        @Part("country_of_residence") country_of_residence: RequestBody,
        @Part("institution_of_study") institution_of_study: RequestBody,
        @Part profile_picture: MultipartBody.Part?
    ): Response<ProfileResponse>

}