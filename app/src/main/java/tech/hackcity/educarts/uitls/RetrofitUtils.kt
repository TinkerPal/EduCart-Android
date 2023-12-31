package tech.hackcity.educarts.uitls

import android.content.Context
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import java.net.CookieManager
import java.net.CookiePolicy

/**
 *Created by Victor Loveday on 4/27/23
 */
object RetrofitUtils {

    val nullOnEmptyConverterFactory = object : Converter.Factory() {
        fun converterFactory() = this
        override fun responseBodyConverter(
            type: Type,
            annotations: Array<out Annotation>,
            retrofit: Retrofit
        ) = object :
            Converter<ResponseBody, Any?> {
            val nextResponseBodyConverter =
                retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)

            override fun convert(value: ResponseBody) =
                if (value.contentLength() != 0L) nextResponseBodyConverter.convert(value) else null
        }
    }


//    fun okhttpClient(context: Context): OkHttpClient {
//        val authorizationNotRequiredEndpoints = listOf(
//            Regex("${Constants.EDU_CARTS_BASE_URL}auth/register/")
//        )
//
//        return OkHttpClient.Builder()
//            .addInterceptor(APIInterceptor(context, authorizationNotRequiredEndpoints))
//            .build()
//    }

}