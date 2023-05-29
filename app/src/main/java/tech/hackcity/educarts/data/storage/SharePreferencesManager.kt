package tech.hackcity.educarts.data.storage

import android.content.Context
import android.content.SharedPreferences

/**
 *Created by Victor Loveday on 4/27/23
 */
class SharePreferencesManager(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

    companion object {
        const val USER_LOGGED_IN = "user_logged_in"
        const val APP_FIRST_TIME_LAUNCHED_STATUS = "app_first_time_launched_status"
        const val USER_ID = "user_id"
    }

    fun clearSharedPreference() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

    fun saveUserId(id: String) {
        val editor = prefs.edit()
        editor.putString(USER_ID, id)
        editor.apply()
    }

    fun fetchUserId(): String? {
        return prefs.getString(USER_ID, null)
    }

    fun saveLoginStatus(isLoggedIn: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean(USER_LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    fun fetchLoginStatus(): Boolean {
        return prefs.getBoolean(USER_LOGGED_IN, false)
    }

    fun saveAppFirstTimeLaunchStatus(isAppLaunchedFirstTime: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean(APP_FIRST_TIME_LAUNCHED_STATUS, isAppLaunchedFirstTime)
        editor.apply()
    }

    fun fetchAppFirstTimeLaunchStatus(): Boolean{
        return prefs.getBoolean(APP_FIRST_TIME_LAUNCHED_STATUS, false)
    }

}