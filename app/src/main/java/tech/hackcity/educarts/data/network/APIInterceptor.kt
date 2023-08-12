package tech.hackcity.educarts.data.network

import android.content.Context
import android.net.ConnectivityManager
import tech.hackcity.educarts.data.storage.SessionManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import tech.hackcity.educarts.uitls.NoInternetException

/**
 *Created by Victor Loveday on 3/24/23
 */
class APIInterceptor(
    context: Context
) : Interceptor {

    private val applicationContext = context.applicationContext
    private val sessionManager = SessionManager(applicationContext)

    override fun intercept(chain: Interceptor.Chain): Response {

        if (!isInternetAvailable())
            throw NoInternetException("No internet connection")

        val requestBuilder = chain.request().newBuilder()

        // If token has been saved, add it to the request
        sessionManager.fetchAuthToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }
    }
}