package tech.hackcity.educarts.data.network

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.hackcity.educarts.uitls.Constants.EDU_CARTS_BASE_URL
import tech.hackcity.educarts.uitls.RetrofitUtils

/**
 *Created by Victor Loveday on 5/29/23
 */
class RetrofitInstance(context: Context) {

    //PiRemit APIs
    private val eduCartsAPI by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl(EDU_CARTS_BASE_URL)
            .addConverterFactory(RetrofitUtils.nullOnEmptyConverterFactory)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .client(RetrofitUtils.okhttpClient(context))
            .build()
    }

    val authenticationAPI: AuthAPI by lazy {
        eduCartsAPI.create(AuthAPI::class.java)
    }


}