package tech.hackcity.educarts.ui.auth.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.ApiException
import tech.hackcity.educarts.data.network.ErrorCodes.USER_NOT_VERIFIED
import tech.hackcity.educarts.data.repositories.auth.AuthRepository
import tech.hackcity.educarts.domain.model.auth.LoginResponseData
import tech.hackcity.educarts.domain.model.auth.User
import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.HTTPException
import tech.hackcity.educarts.uitls.NoInternetException
import tech.hackcity.educarts.uitls.SocketTimeOutException
import tech.hackcity.educarts.uitls.clearExtraCharacters
import tech.hackcity.educarts.uitls.extractDataFields
import tech.hackcity.educarts.uitls.extractDataFieldsFromErrorBody
import tech.hackcity.educarts.uitls.extractErrorMessagesFromErrorBody
import tech.hackcity.educarts.uitls.extractIdAndEmailFromErrorBody
import java.lang.IllegalArgumentException

/**
 *Created by Victor Loveday on 5/29/23
 */
class LoginViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    var email: String? = null
    var password: String? = null

    var loginListener: LoginListener? = null

    fun loginUser(context: Context) {
        loginListener?.onRequestStarted()

        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            loginListener?.onRequestFailed(context.resources.getString(R.string.field_can_not_be_empty))
            return
        }

        Coroutines.onMain {
            try {
                val response = repository.loginUser(email!!, password!!)

                if (!response.error) {
                    loginListener?.onRequestSuccessful(response)
                    repository.saveTokens(response.data.access, response.data.refresh)
                    repository.saveLoginStatus(true)
                    repository.saveUserId(response.data.id)
                    saveUser(response.data)
                }

            } catch (e: ApiException) {
                loginListener?.onRequestFailed("${e.errorMessage} ${e.errorData}")
                val errorMessages = extractErrorMessagesFromErrorBody(e.errorMessage)
                val (id, email) = extractIdAndEmailFromErrorBody(e.errorData.toString())

                if (errorMessages.isNotEmpty()) {
                    for ((code, message) in errorMessages) {
                        if (code == USER_NOT_VERIFIED) {
                            Log.d("LoginError", "${e.errorMessage} ${e.errorData} ")

//                            val (userId, userEmail) = extractIdAndEmailFromErrorBody(e.errorMessage)

//                            if (userId != null && userEmail != null) {
//                                Log.d("LoginError", "User ID: $userId")
//                            } else {
//                                Log.d("LoginError", "Failed to extract user data from JSON response.")
//                            }

                        }
                    }
                }
            }
        }

    }

    private fun saveUser(data: LoginResponseData) {
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
            data.free_consultation
        )

        Log.d("UserInfo", "saved data : $user")

        Coroutines.onMain {
            repository.saveUser(user)
        }
    }

}