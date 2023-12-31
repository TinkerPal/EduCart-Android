package tech.hackcity.educarts.data.network

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.hackcity.educarts.data.network.apis.AuthAPI
import tech.hackcity.educarts.data.network.apis.DashboardAPI
import tech.hackcity.educarts.data.network.apis.OrderAPI
import tech.hackcity.educarts.data.network.apis.FlutterWaveAPI
import tech.hackcity.educarts.data.network.apis.RegionsAPI
import tech.hackcity.educarts.data.network.apis.SupportAPI
import tech.hackcity.educarts.data.network.apis.SEVISFeeAPI
import tech.hackcity.educarts.data.network.apis.SettingsAPI
import tech.hackcity.educarts.uitls.Constants.EDU_CARTS_BASE_URL
import tech.hackcity.educarts.uitls.Constants.FLUTTER_WAVE_BASE_URL
import tech.hackcity.educarts.uitls.RetrofitUtils

/**
 *Created by Victor Loveday on 5/29/23
 */
class RetrofitInstance(context: Context) {

    private val eduCartsAPI by lazy {
        val authorizationNotRequiredEndpoints = listOf(
            Regex("${EDU_CARTS_BASE_URL}auth/register/"),
            Regex("${EDU_CARTS_BASE_URL}auth/login/"),
            Regex("${EDU_CARTS_BASE_URL}auth/forgot-password/"),
            Regex("${EDU_CARTS_BASE_URL}auth/verify-otp/"),
            Regex("${EDU_CARTS_BASE_URL}auth/forgot-password/verify-otp/"),
            Regex("${EDU_CARTS_BASE_URL}auth/reset-password/"),
            Regex("${EDU_CARTS_BASE_URL}token/refresh/")
        )

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val apiInterceptor = APIInterceptor(context, authorizationNotRequiredEndpoints, this)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(apiInterceptor)
            .build()

        Retrofit.Builder()
            .baseUrl(EDU_CARTS_BASE_URL)
            .addConverterFactory(RetrofitUtils.nullOnEmptyConverterFactory)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }


    val authenticationAPI: AuthAPI by lazy {
        eduCartsAPI.create(AuthAPI::class.java)
    }

    val sevisFeeAPI: SEVISFeeAPI by lazy {
        eduCartsAPI.create(SEVISFeeAPI::class.java)
    }

    val supportAPI: SupportAPI by lazy {
        eduCartsAPI.create(SupportAPI::class.java)
    }

    val settingsAPI: SettingsAPI by lazy {
        eduCartsAPI.create(SettingsAPI::class.java)
    }

    val dashboardAPI: DashboardAPI by lazy {
        eduCartsAPI.create(DashboardAPI::class.java)
    }

    val orderAPI: OrderAPI by lazy {
        eduCartsAPI.create(OrderAPI::class.java)
    }

    val regionsAPI: RegionsAPI by lazy {
        eduCartsAPI.create(RegionsAPI::class.java)
    }


    private val flutterWavePaymentAPI by lazy {
        val authorizationNotRequiredEndpoints = listOf(
            Regex("${FLUTTER_WAVE_BASE_URL}charges?type=card")
        )

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val apiInterceptor = APIInterceptor(context, authorizationNotRequiredEndpoints, this)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(apiInterceptor)
            .build()

        Retrofit.Builder()
            .baseUrl(FLUTTER_WAVE_BASE_URL)
            .addConverterFactory(RetrofitUtils.nullOnEmptyConverterFactory)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val flutterWaveAPI: FlutterWaveAPI by lazy {
        flutterWavePaymentAPI.create(FlutterWaveAPI::class.java)
    }


}