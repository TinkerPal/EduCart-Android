package tech.hackcity.educarts.ui.auth.register

import android.content.Context
import androidx.lifecycle.ViewModel
import retrofit2.HttpException
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.repositories.auth.AuthRepository
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.NoInternetException
import tech.hackcity.educarts.uitls.errorMessageFetcher
import tech.hackcity.educarts.uitls.removeSpacesFromString
import java.io.IOException
import java.net.SocketTimeoutException

/**
 *Created by Victor Loveday on 5/24/23
 */
class CreatePersonalAccountViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    var email: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var countryOfResidence: String? = null
    var countryCode: Int? = null
    var phoneNumber: String? = null
    var password: String? = null

    var createPersonalAccountListener: CreatePersonalAccountListener? = null

    fun onSignUpButtonClicked(context: Context) {
        createPersonalAccountListener?.onRequestStarted()

        if (
            email.isNullOrEmpty() || firstName.isNullOrEmpty()
            || lastName.isNullOrEmpty() || password.isNullOrEmpty()
            || phoneNumber.isNullOrEmpty() || countryOfResidence.isNullOrEmpty()
            || countryCode == null
        ) {
            createPersonalAccountListener?.onRequestFailed(context.resources.getString(R.string.field_can_not_be_empty))
            return
        }

        val formattedPhoneNumber = removeSpacesFromString(phoneNumber!!)

        Coroutines.main {
            val response = try {
                repository.registerPersonalAccountUser(
                    email!!,
                    firstName!!,
                    lastName!!,
                    countryOfResidence!!,
                    countryCode!!,
                    formattedPhoneNumber,
                    password!!
                )

            } catch (e: IOException) {
                createPersonalAccountListener?.onRequestFailed(e.message!!)
                return@main

            } catch (e: NoInternetException) {
                createPersonalAccountListener?.onRequestFailed(e.message!!)
                return@main

            } catch (e: HttpException) {
                createPersonalAccountListener?.onRequestFailed(e.message!!)
                return@main

            } catch (e: SocketTimeoutException) {
                createPersonalAccountListener?.onRequestFailed(e.message!!)
                return@main

            }

            if (!response.error) {
                createPersonalAccountListener?.onRequestSuccessful(response)

            } else {

                var errorMessage = ""

                if (response.message.email != null) {
                    errorMessage = response.message.email.toString()
                }else if (response.message.phone_number != null) {
                    errorMessage = response.message.phone_number.toString()
                }else if (response.message.password != null) {
                    errorMessage = response.message.password.toString()
                }

                createPersonalAccountListener?.onRequestFailed(errorMessage)
            }
        }
    }
}