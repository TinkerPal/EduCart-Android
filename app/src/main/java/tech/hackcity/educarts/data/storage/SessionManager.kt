package tech.hackcity.educarts.data.storage

import android.content.Context
import android.content.SharedPreferences

/**
 *Created by Victor Loveday on 3/24/23
 */

/**
 * Session manager to save and fetch data from SharedPreferences
 */
class SessionManager (context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences("userTokenPref", Context.MODE_PRIVATE)

    companion object {
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
    }

    /**
     *clear tokens
     */
    fun clearAllTokens() {
        val editor = prefs.edit()
        editor.apply()
        editor.clear()
    }

    /**
     *save tokens
     */
    fun saveTokens(accessToken: String, refreshToken: String) {
        val editor = prefs.edit()
        editor.putString(ACCESS_TOKEN, accessToken)
        editor.putString(REFRESH_TOKEN, refreshToken)
        editor.apply()
    }

    /**
     *fetch access token
     */
    fun fetchAccessToken(): String? {
        return prefs.getString(ACCESS_TOKEN, null)
    }

    /**
     *fetch refresh token
     */
    fun fetchRefreshToken(): String? {
        return prefs.getString(REFRESH_TOKEN, null)
    }

}
