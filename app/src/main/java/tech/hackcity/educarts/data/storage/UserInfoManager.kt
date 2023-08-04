package tech.hackcity.educarts.data.storage

import android.content.Context
import androidx.datastore.preferences.clear
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tech.hackcity.educarts.domain.model.auth.User

class UserInfoManager(context: Context) {
    private val dataStore = context.createDataStore(name = "user_prefs")

    companion object {
        val USER_FIRST_NAME_KEY = preferencesKey<String>("USER_FIRST_NAME")
        val USER_LAST_NAME_KEY = preferencesKey<String>("USER_LAST_NAME")
        val USER_ID_KEY = preferencesKey<String>("USER_ID")
        val USER_CREATED_AT_KEY = preferencesKey<String>("USER_CREATED_AT")
        val USER_UPDATED_AT_KEY = preferencesKey<String>("USER_UPDATED_AT")
        val USER_STATUS_KEY = preferencesKey<Int>("USER_STATUS")
        val USER_EMAIL_KEY = preferencesKey<String>("USER_EMAIL")
        val USER_PHONE_KEY = preferencesKey<String>("USER_PHONE")
        val IS_USER_PHONE_VERIFIED_KEY = preferencesKey<Boolean>("IS_USER_PHONE_VERIFIED")
        val USER_COUNTRY_CODE_KEY = preferencesKey<String>("USER_DIAL_CODE")
        val USER_COUNTRY_OF_RESIDENCE_KEY = preferencesKey<String>("USER_COUNTRY_OF_RESIDENCE")
        val IS_USER_BALANCE_VISIBLE_KEY = preferencesKey<Boolean>("IS_USER_BALANCE_VISIBLE")
        val USER_TOTAL_ASSET_KEY = preferencesKey<String>("USER_TOTAL_ASSET")
        val USER_PROFILE_IMAGE_KEY = preferencesKey<String>("USER_PROFILE_IMAGE")
        val IS_TRANSACTION_PIN_CREATED_KEY = preferencesKey<Boolean>("IS_TRANSACTION_PIN_CREATED")
        val IS_TOKEN_EXPIRE_KEY = preferencesKey<Boolean>("IS_TOKEN_EXPIRE")

    }

    suspend fun clearUser() {
        dataStore.edit {
            it.clear()
        }
    }

    //Personal Information
    suspend fun saveUser(user: User) {
        dataStore.edit {
            it[USER_ID_KEY] = user.id
            it[USER_FIRST_NAME_KEY] = user.firstName
            it[USER_LAST_NAME_KEY] = user.lastName
            it[USER_PHONE_KEY] = user.phoneNumber
            it[USER_COUNTRY_OF_RESIDENCE_KEY] = user.countryOfResidence
            it[USER_EMAIL_KEY] = user.email
        }
    }

    fun fetchUserInfo(): Flow<User> = dataStore.data
        .map {
            User(
                it[USER_ID_KEY] ?: "",
                it[USER_FIRST_NAME_KEY] ?: "",
                it[USER_LAST_NAME_KEY] ?: "",
                it[USER_PHONE_KEY] ?: "",
                it[USER_COUNTRY_OF_RESIDENCE_KEY] ?: "",
                it[USER_EMAIL_KEY] ?: "",
            )
        }

    suspend fun saveUserProfilePhoto(profile: String) {
        dataStore.edit {
            it[USER_PROFILE_IMAGE_KEY] = profile
        }
    }

    fun fetchUserProfilePhoto(): Flow<String> = dataStore.data.map {
        it[USER_PROFILE_IMAGE_KEY] ?: ""
    }

    suspend fun saveBalanceVisibilityStatus(isBalanceVisible: Boolean) {
        dataStore.edit {
            it[IS_USER_BALANCE_VISIBLE_KEY] = isBalanceVisible
        }
    }

    val fetchBalanceVisibilityStatus: Flow<Boolean> = dataStore.data.map {
        it[IS_USER_BALANCE_VISIBLE_KEY] ?: true
    }

    suspend fun saveTotalAssets(value: String) {
        dataStore.edit {
            it[USER_TOTAL_ASSET_KEY] = value
        }
    }

    val fetchTotalAssets: Flow<String> = dataStore.data.map {
        it[USER_TOTAL_ASSET_KEY] ?: "0.00"
    }

    suspend fun saveTokenExpirationStatus(isExpired: Boolean) {
        dataStore.edit {
            it[IS_TOKEN_EXPIRE_KEY] = isExpired
        }
    }

    val fetchTokenExpirationStatus: Flow<Boolean> = dataStore.data.map {
        it[IS_TOKEN_EXPIRE_KEY] ?: false
    }

}