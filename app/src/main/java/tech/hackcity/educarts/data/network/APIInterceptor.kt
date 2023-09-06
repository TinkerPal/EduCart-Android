package tech.hackcity.educarts.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import tech.hackcity.educarts.data.storage.SessionManager
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.HttpException
import tech.hackcity.educarts.R
import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.uitls.NoInternetException
import java.io.IOException
import java.net.SocketTimeoutException

/**
 *Created by Victor Loveday on 3/24/23
 */

class APIInterceptor(
    private val context: Context,
    private val authorizationNotRequiredEndpoints: List<Regex>,
    private val retrofitInstance: RetrofitInstance
) : Interceptor {

    private val applicationContext = context.applicationContext
    private val sessionManager = SessionManager(applicationContext)

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable()) {
            throw NoInternetException(context.resources.getString(R.string.no_internet_connection))
//            throw ApiException(context.resources.getString(R.string.no_internet_connection))
        }

        val requestBuilder = chain.request().newBuilder()
        val requestUrl = chain.request().url.toString()
        val accessToken = sessionManager.fetchAccessToken()

        // Check if the current request URL matches any pattern that doesn't require authorization
        if (authorizationNotRequiredEndpoints.none { it.matches(requestUrl) }) {
            accessToken?.let {
                requestBuilder.addHeader("Authorization", "Bearer $it")
            }
        }

        val response = chain.proceed(requestBuilder.build())

        if (response.code == 401) {
            Log.e("TokenRefresh", "Expired")
            val refreshToken = sessionManager.fetchRefreshToken()
            val newAccessToken = runBlocking {
                refreshAccessToken(refreshToken!!)
            }

            if (newAccessToken != null) {
                val newRequest = chain.request().newBuilder()
                    .header("Authorization", "Bearer $newAccessToken")
                    .build()

                response.close()

                return chain.proceed(newRequest)
            }
        }

        return response
    }

    private suspend fun refreshAccessToken(refreshToken: String): String? {
        var retryCount = 0
        val maxRetries = 3

        while (retryCount < maxRetries) {
            try {
                val response = retrofitInstance.authenticationAPI.refreshAccessToken(refreshToken)

                return if (response.isSuccessful) {
                    val accessToken = response.body()?.access
                    val newRefreshToken = response.body()?.refresh

                    sessionManager.saveTokens(accessToken ?: "", newRefreshToken ?: "")

                    accessToken

                } else {
                    val errorMessage = response.body()?.errorMessage.toString()
                    Log.e("TokenRefresh", "Refresh token request failed: $errorMessage")
                    errorMessage
                }

            } catch (e: IOException) {
                Log.e("TokenRefresh", "IOException: ${e.message}")
                retryCount++
                delay(1000)

            } catch (e: NoInternetException) {
                Log.e("TokenRefresh", "No Internet Connection: ${e.message}")
                retryCount++
                delay(1000)

            } catch (e: HttpException) {
                Log.e("TokenRefresh", "HTTP Exception: ${e.message}")
                retryCount++
                delay(1000)

            } catch (e: SocketTimeoutException) {
                Log.e("TokenRefresh", "Socket Timeout Exception: ${e.message}")
                retryCount++
                delay(1000)
            }
        }

        throw ApiException(
            context.resources.getString(R.string.failed_to_refresh_token_after, "$$maxRetries"), ""
        )
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }
    }
}


