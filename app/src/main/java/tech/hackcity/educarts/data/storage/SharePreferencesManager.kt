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
        const val USER_ID = "user_id"
        const val USER_LOGGED_IN = "user_logged_in"
        const val GET_STARTED = "get_started"
        const val TRANSACTION_PIN_CREATED = "transaction_pin_created"
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
        return prefs.getString(USER_ID, "")
    }

    fun saveLoginStatus(isLoggedIn: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean(USER_LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    fun fetchLoginStatus(): Boolean {
        return prefs.getBoolean(USER_LOGGED_IN, false)
    }

    fun saveIsGetStartedPressed(isGetStartedPressed: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean(GET_STARTED, isGetStartedPressed)
        editor.apply()
    }

    fun fetchIsGetStartedPressed(): Boolean{
        return prefs.getBoolean(GET_STARTED, false)
    }

    fun savePinCreationStatus(isCreated: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean(TRANSACTION_PIN_CREATED, isCreated)
        editor.apply()
    }

    fun isTransactionPinCreated(): Boolean {
        return prefs.getBoolean(TRANSACTION_PIN_CREATED, false)
    }

}