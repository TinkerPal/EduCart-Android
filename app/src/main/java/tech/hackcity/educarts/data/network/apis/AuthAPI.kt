package tech.hackcity.educarts.data.network.apis

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.PUT
import tech.hackcity.educarts.domain.model.auth.*

/**
 *Created by Victor Loveday on 5/29/23
 */
interface AuthAPI {

    @FormUrlEncoded
    @POST("auth/register/")
    suspend fun registerPersonalUserAccount(
        @Field("user_type") user_type: String,
        @Field("email") email: String,
        @Field("first_name") first_name: String,
        @Field("last_name") last_name: String,
        @Field("country_of_residence") country_of_residence: String,
        @Field("country_code") country_code: Int,
        @Field("phone_number") phone_number: String,
        @Field("password") password: String,
    ): Response<RegisterUserResponse> // register user

    @FormUrlEncoded
    @POST("auth/verify-otp/")
    suspend fun verifyOTPForNewAccount(
        @Field("id") id: String,
        @Field("otp") otp: String
    ): Response<VerifyOTPResponse> // verify otp for new account

    @FormUrlEncoded
    @POST("auth/login/")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse> // login user

    @FormUrlEncoded
    @POST("auth/forgot-password/")
    suspend fun forgotPassword(
        @Field("email") email: String
    ): Response<ForgotPasswordResponse> // forgot password

    @FormUrlEncoded
    @POST("auth/forgot-password/verify-otp/")
    suspend fun verifyOTPForPasswordReset(
        @Field("id") id: String,
        @Field("otp") otp: String
    ): Response<VerifyOTPResponse> // verify otp for password reset

    @FormUrlEncoded
    @POST("auth/regenerate-otp/")
    suspend fun regenerateOTP(
        @Field("id") id: String
    ): Response<RegenerateOTPResponse>

    @FormUrlEncoded
    @PUT("auth/reset-password/")
    suspend fun resetPassword(
        @Field("id") id: String,
        @Field("password") password: String,
        @Field("confirm_password") confirm_password: String
    ): Response<CreateNewPasswordResponse> // create new password

    @FormUrlEncoded
    @POST("token/refresh/")
    suspend fun refreshAccessToken(
        @Field("refresh") refresh : String
    ): Response<TokenResponse> // refresh access token
}