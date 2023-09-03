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
        val USER_EMAIL_KEY = preferencesKey<String>("USER_EMAIL")
        val USER_PHONE_KEY = preferencesKey<String>("USER_PHONE")
        val USER_INSTITUTION_OF_STUDY_KEY = preferencesKey<String>("USER_INSTITUTION_OF_STUDY")
        val USER_COUNTRY_OF_BIRTH_KEY = preferencesKey<String>("USER_COUNTRY_OF_BIRTH")
        val USER_STATE_KEY = preferencesKey<String>("USER_STATE")
        val USER_CITY_KEY = preferencesKey<String>("USER_CITY")
        val USER_COUNTRY_CODE_KEY = preferencesKey<Int>("USER_COUNTRY_CODE")
        val USER_COUNTRY_OF_RESIDENCE_KEY = preferencesKey<String>("USER_COUNTRY_OF_RESIDENCE")
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
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = user.id
            user.profilePhoto?.let { preferences[USER_PROFILE_PHOTO_KEY] = it }
            preferences[USER_FIRST_NAME_KEY] = user.firstName
            preferences[USER_LAST_NAME_KEY] = user.lastName
            preferences[USER_COUNTRY_CODE_KEY] = user.countryCode
            preferences[USER_PHONE_KEY] = user.phoneNumber
            preferences[USER_COUNTRY_OF_RESIDENCE_KEY] = user.countryOfResidence
            preferences[USER_EMAIL_KEY] = user.email
            preferences[IS_PROFILE_COMPLETED_KEY] = user.isProfileCompleted
            preferences[IS_USER_RESTRICTED_KEY] = user.is_restricted
            user.institutionOfStudy?.let { preferences[USER_INSTITUTION_OF_STUDY_KEY] = it }
            user.countryOfBirth?.let { preferences[USER_COUNTRY_OF_BIRTH_KEY] = it }
            user.state?.let { preferences[USER_STATE_KEY] = it }
            user.city?.let { preferences[USER_CITY_KEY] = it }
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
            )
        }
}