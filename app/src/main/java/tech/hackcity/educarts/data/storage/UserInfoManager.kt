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
        val USER_INSTITUTION_OF_STUDY_KEY = preferencesKey<String>("USER_INSTITUTION_OF_STUDY")
        val USER_COUNTRY_OF_BIRTH_KEY = preferencesKey<String>("USER_COUNTRY_OF_BIRTH")
        val USER_STATE_KEY = preferencesKey<String>("USER_STATE")
        val USER_CITY_KEY = preferencesKey<String>("USER_CITY")
        val USER_ADDRESS_BOOK_KEY = preferencesKey<String>("USER_ADDRESS_BOOK")
        val IS_USER_PHONE_VERIFIED_KEY = preferencesKey<Boolean>("IS_USER_PHONE_VERIFIED")
        val USER_COUNTRY_CODE_KEY = preferencesKey<Int>("USER_COUNTRY_CODE")
        val USER_COUNTRY_OF_RESIDENCE_KEY = preferencesKey<String>("USER_COUNTRY_OF_RESIDENCE")
        val IS_USER_BALANCE_VISIBLE_KEY = preferencesKey<Boolean>("IS_USER_BALANCE_VISIBLE")
        val USER_TOTAL_ASSET_KEY = preferencesKey<String>("USER_TOTAL_ASSET")
        val USER_PROFILE_IMAGE_KEY = preferencesKey<String>("USER_PROFILE_IMAGE")
        val IS_TRANSACTION_PIN_CREATED_KEY = preferencesKey<Boolean>("IS_TRANSACTION_PIN_CREATED")
        val IS_TOKEN_EXPIRE_KEY = preferencesKey<Boolean>("IS_TOKEN_EXPIRE")
        val IS_PROFILE_COMPLETED_KEY = preferencesKey<Boolean>("IS_PROFILE_COMPLETED")
        val USER_PROFILE_PHOTO_KEY = preferencesKey<String>("USER_PROFILE_PHOTO")
        val IS_USER_RESTRICTED_KEY = preferencesKey<Boolean>("IS_USER_RESTRICTED")

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
            it[USER_PROFILE_PHOTO_KEY] = user.profilePhoto.toString()
            it[USER_FIRST_NAME_KEY] = user.firstName
            it[USER_LAST_NAME_KEY] = user.lastName
            it[USER_COUNTRY_CODE_KEY] = user.countryCode
            it[USER_PHONE_KEY] = user.phoneNumber
            it[USER_COUNTRY_OF_RESIDENCE_KEY] = user.countryOfResidence
            it[USER_EMAIL_KEY] = user.email
            it[IS_PROFILE_COMPLETED_KEY] = user.isProfileCompleted
            it[IS_USER_RESTRICTED_KEY] = user.is_restricted
            it[USER_INSTITUTION_OF_STUDY_KEY] = user.institutionOfStudy.toString()
            it[USER_COUNTRY_OF_BIRTH_KEY] = user.countryOfBirth.toString()
            it[USER_STATE_KEY] = user.state.toString()
            it[USER_CITY_KEY] = user.city.toString()
            it[USER_ADDRESS_BOOK_KEY] = user.addressBook.toString()
        }
    }

    fun fetchUserInfo(): Flow<User> = dataStore.data
        .map {
            User(
                it[USER_ID_KEY] ?: "",
                it[USER_PROFILE_PHOTO_KEY] ?: "",
                it[USER_FIRST_NAME_KEY] ?: "",
                it[USER_LAST_NAME_KEY] ?: "",
                it[USER_COUNTRY_CODE_KEY] ?: 0,
                it[USER_PHONE_KEY] ?: "",
                it[USER_COUNTRY_OF_RESIDENCE_KEY] ?: "",
                it[USER_EMAIL_KEY] ?: "",
                it[IS_PROFILE_COMPLETED_KEY] ?: false,
                it[IS_USER_RESTRICTED_KEY] ?: false,
                it[USER_INSTITUTION_OF_STUDY_KEY] ?: "",
                it[USER_COUNTRY_OF_BIRTH_KEY] ?: "",
                it[USER_STATE_KEY] ?: "",
                it[USER_CITY_KEY] ?: "",
                it[USER_ADDRESS_BOOK_KEY] ?: ""
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