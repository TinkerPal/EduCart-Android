package tech.hackcity.educarts.ui.settings.profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import okhttp3.MultipartBody
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.ApiException
import tech.hackcity.educarts.data.repositories.settings.SettingsRepository
import tech.hackcity.educarts.domain.model.auth.User
import tech.hackcity.educarts.domain.model.settings.ProfileResponseData
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.NoInternetException
import tech.hackcity.educarts.uitls.SocketTimeOutException
import tech.hackcity.educarts.uitls.clearExtraCharacters
import tech.hackcity.educarts.uitls.removeSpacesFromString

/**
 *Created by Victor Loveday on 8/25/23
 */
class ProfileViewModel(
    private val repository: SettingsRepository
) : ViewModel() {

    var editListener: EditProfileListener? = null
    var profileListener: ProfileListener? = null

    var firstName: String? = null
    var lastName: String? = null
    var countryCode: Int? = null
    var phoneNumber: String? = null
    var countryOfResidence: String? = null
    var institutionOfStudy: String? = null
    var profilePicture: MultipartBody.Part? = null

    fun fetchProfile() {
        profileListener?.onFetchProfileRequestStarted()

        Coroutines.onMainWithScope(viewModelScope) {
            try {
                val response = repository.fetchProfile()

                if (!response.error) {
                    profileListener?.onFetchProfileRequestSuccessful(response)
                    saveUser(response.data)

                } else {
                    editListener?.onRequestFailed(response.errorMessage.toString())
                }

            } catch (e: ApiException) {
                editListener?.onRequestFailed(e.message!!)
            }catch (e: NoInternetException) {
                editListener?.onRequestFailed("${e.message}")
            }catch (e: SocketTimeOutException) {
                editListener?.onRequestFailed("${e.message}")
            }
        }

    }

    fun editProfile(context: Context) {
        editListener?.onEditProfileRequestStarted()

        if (
            institutionOfStudy.isNullOrEmpty() ||
            firstName.isNullOrEmpty() ||
            lastName.isNullOrEmpty() ||
            phoneNumber.isNullOrEmpty() ||
            countryOfResidence.isNullOrEmpty() ||
            countryCode == null
        ) {
            editListener?.onRequestFailed(context.resources.getString(R.string.field_can_not_be_empty))
            return
        }

        val formattedPhoneNumber = removeSpacesFromString(phoneNumber!!)

        Coroutines.onMainWithScope(viewModelScope) {
            Log.d("UserInfo", "$firstName $lastName $countryCode $formattedPhoneNumber $countryOfResidence $institutionOfStudy ")

            try {
                val response = repository.editProfile(
                    firstName!!,
                    lastName!!,
                    countryCode!!,
                    formattedPhoneNumber,
                    countryOfResidence!!,
                    institutionOfStudy!!,
                    profilePicture ?: MultipartBody.Part.createFormData("", "")
                )

                if (!response.error) {
                    editListener?.onEditProfileRequestSuccessful(response)
                    saveUser(response.data)
                } else {
                    editListener?.onRequestFailed(response.errorMessage.toString())
                }

            } catch (e: ApiException) {
                editListener?.onRequestFailed(e.message!!)
            }catch (e: NoInternetException) {
                editListener?.onRequestFailed("${e.message}")
            }catch (e: SocketTimeOutException) {
                editListener?.onRequestFailed("${e.message}")
            }
        }
    }

    private fun saveUser(data: ProfileResponseData) {
        val user = User(
            data.id,
            data.profile_picture,
            data.first_name,
            data.last_name,
            data.country_code,
            data.phone_number,
            data.country_of_residence,
            data.email,
            data.profile_completed,
            data.is_restricted,
            data.institution_of_study,
            data.country_of_birth,
            data.state,
            data.city,
        )

        Log.d("UserInfo", "saved data : $user")

        Coroutines.onMain {
            repository.saveUser(user)
        }
    }

}