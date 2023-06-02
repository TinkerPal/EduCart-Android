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
        const val USER_AUTHENTICATED_TOKEN = "user_authenticated_token"
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
     *save auth token
     */
    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_AUTHENTICATED_TOKEN, token)
        editor.apply()
    }

    /**
     *fetch auth token
     */
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_AUTHENTICATED_TOKEN, null)
    }
}
