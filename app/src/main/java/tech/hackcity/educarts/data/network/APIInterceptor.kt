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
    private val context: Context
) : Interceptor {

    private val applicationContext = context.applicationContext
    private val sessionManager = SessionManager(applicationContext)

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable())
            throw NoInternetException("No internet connection")

        val request = chain.request()

        sessionManager.fetchAuthToken()?.let { token ->
            if (requiresAuthorization(request)) {
                val authenticatedRequest = request.newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                return chain.proceed(authenticatedRequest)
            }
        }

        return chain.proceed(request)
    }

    private fun requiresAuthorization(request: Request): Boolean {
        val url = request.url.toString()
        return url.contains("secured")
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }
    }
}
