package tech.hackcity.educarts.ui.auth.register

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.ApiException
import tech.hackcity.educarts.data.repositories.auth.AuthRepository
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.removeSpacesFromString

/**
 *Created by Victor Loveday on 5/24/23
 */
class CreatePersonalAccountViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    var userType: String? = null
    var email: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var countryOfResidence: String? = null
    var countryCode: Int? = null
    var phoneNumber: String? = null
    var password: String? = null

    var createPersonalAccountListener: CreatePersonalAccountListener? = null

    fun registerPersonalAccountUser(context: Context) {
        createPersonalAccountListener?.onRequestStarted()

        if (
            email.isNullOrEmpty() || firstName.isNullOrEmpty()
            || lastName.isNullOrEmpty() || password.isNullOrEmpty()
            || phoneNumber.isNullOrEmpty() || countryOfResidence.isNullOrEmpty()
            || countryCode == null
        ) {

            if (userType.isNullOrEmpty()) {
                createPersonalAccountListener?.onRequestFailed(context.resources.getString(R.string.no_user_type_selected))
                return
            }

            createPersonalAccountListener?.onRequestFailed(context.resources.getString(R.string.field_can_not_be_empty))
            return
        }

        val formattedPhoneNumber = removeSpacesFromString(phoneNumber!!)

        Coroutines.onMainWithScope(viewModelScope) {
            try {
                val response = repository.registerPersonalAccountUser(
                    userType!!,
                    email!!,
                    firstName!!,
                    lastName!!,
                    countryOfResidence!!,
                    countryCode!!,
                    formattedPhoneNumber,
                    password!!
                )

                if (!response.error) {
                    createPersonalAccountListener?.onRequestSuccessful(response)
                    repository.saveUserId(response.data.id)

                } else {

                    val messages = mutableListOf(
                        response.message.email?.get(0) ?: "",
                        response.message.phone_number?.get(0) ?: "",
                        response.message.password?.get(0) ?: "",
                    )

                    for (message in messages) {
                        createPersonalAccountListener?.onRequestFailed(message)
                    }

                }

            } catch (e: ApiException) {
                createPersonalAccountListener?.onRequestFailed(e.message!!)
                return@onMainWithScope
            }
        }
    }
}