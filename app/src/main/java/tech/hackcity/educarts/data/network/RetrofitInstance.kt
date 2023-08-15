package tech.hackcity.educarts.data.network

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.hackcity.educarts.data.network.apis.AuthAPI
import tech.hackcity.educarts.data.network.apis.SupportAPI
import tech.hackcity.educarts.data.network.apis.SEVISFeeAPI
import tech.hackcity.educarts.uitls.Constants.EDU_CARTS_BASE_URL

/**
 *Created by Victor Loveday on 5/29/23
 */
class RetrofitInstance(context: Context) {

//    private val eduCartsAPI by lazy {
//        val logging = HttpLoggingInterceptor()
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
//        val client = OkHttpClient.Builder()
//            .addInterceptor(logging)
//            .build()
//
//        Retrofit.Builder()
//            .baseUrl(EDU_CARTS_BASE_URL)
//            .addConverterFactory(RetrofitUtils.nullOnEmptyConverterFactory)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
//            .client(RetrofitUtils.okhttpClient(context))
//            .build()
//    }

    private val eduCartsAPI by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(APIInterceptor(context))
            .build()

        Retrofit.Builder()
            .baseUrl(EDU_CARTS_BASE_URL)
//            .addConverterFactory(RetrofitUtils.nullOnEmptyConverterFactory)
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

}