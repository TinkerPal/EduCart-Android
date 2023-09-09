package tech.hackcity.educarts.ui.payment.sevisfee.sevispayment

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import okhttp3.MultipartBody
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.ApiException
import tech.hackcity.educarts.data.network.ErrorCodes.EMPTY_FORM_FIELD
import tech.hackcity.educarts.data.network.ErrorCodes.IO_EXCEPTION
import tech.hackcity.educarts.data.repositories.payment.SEVISFeeRepository
import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.NoInternetException
import tech.hackcity.educarts.uitls.SocketTimeOutException
import tech.hackcity.educarts.uitls.clearExtraCharacters

/**
 *Created by Victor Loveday on 8/3/23
 */

class SEVISFeeViewModel(private val repository: SEVISFeeRepository) : ViewModel() {

    var listener1: SEIVSFeeStep1Listener? = null
    var listener2: SEIVSFeeStep2Listener? = null
    var listener3: SEIVSFeeStep3Listener? = null

    var sevisId: String? = null
    var lastName: String? = null
    var givenName: String? = null
    var dateOfBirth: String? = null
    var form: MultipartBody.Part? = null
    var passport: MultipartBody.Part? = null
    var internationalPassport: MultipartBody.Part? = null

    var formType: String? = null
    var category: String? = null
    var email: String? = null
    var phoneNumber: String? = null
    var countryOfCitizenship: String? = null
    var countryOfBirth: String? = null

    var streetAddress1: String? = null
    var streetAddress2: String? = null
    var country: String? = null
    var state: String? = null
    var city: String? = null


    fun submitSevisFeeStep1(context: Context) {
        listener1?.onRequestStarted()

        if (
            sevisId.isNullOrEmpty() || lastName.isNullOrEmpty()
            || givenName.isNullOrEmpty() || dateOfBirth.isNullOrEmpty()
            || form == null || passport == null
            || internationalPassport == null
        ) {
            listener1?.onRequestFailed(context.resources.getString(R.string.field_can_not_be_empty))
            return
        }

        Coroutines.onMainWithScope(viewModelScope) {
            try {
                val response = repository.sevisFeeStep1(
                    repository.fetchUserId()!!,
                    sevisId!!,
                    lastName!!,
                    givenName!!,
                    dateOfBirth!!,
                    form!!,
                    passport!!,
                    internationalPassport!!
                )

                if (!response.error) {
                    listener1?.onRequestSuccessful(response)
                }

            } catch (e: ApiException) {
                listener1?.onRequestFailed(e.errorMessage)
            }catch (e: NoInternetException) {
                listener1?.onRequestFailed("${e.message}")
            }catch (e: SocketTimeOutException) {
                listener1?.onRequestFailed("${e.message}")
            }
        }
    }

    fun submitSevisFeeStep2(context: Context) {
        listener2?.onRequestStarted()

        if (
            formType.isNullOrEmpty() || category.isNullOrEmpty()
            || email.isNullOrEmpty() || phoneNumber.isNullOrEmpty()
            || countryOfCitizenship == null || countryOfBirth == null
        ) {
            listener2?.onRequestFailed(context.resources.getString(R.string.field_can_not_be_empty))
            return
        }

        Coroutines.onMainWithScope(viewModelScope) {
            try {
                val response = repository.sevisFeeStep2(
                    formType!!,
                    category!!,
                    email!!,
                    phoneNumber!!,
                    countryOfCitizenship!!,
                    countryOfBirth!!
                )

                if (!response.error) {
                    listener2?.onRequestSuccessful(response)

                } else {
                    listener2?.onRequestFailed(response.errorMessage.toString())
                }

            } catch (e: ApiException) {
                listener2?.onRequestFailed(e.errorMessage)
            }catch (e: NoInternetException) {
                listener2?.onRequestFailed("${e.message}")
            }catch (e: SocketTimeOutException) {
                listener2?.onRequestFailed("${e.message}")
            }
        }
    }

    fun submitSevisFeeStep3(context: Context) {
        listener3?.onRequestStarted()

        if (
            streetAddress1.isNullOrEmpty() || streetAddress2.isNullOrEmpty()
            || country.isNullOrEmpty() || state.isNullOrEmpty()
            || city == null
        ) {
            listener3?.onRequestFailed(context.resources.getString(R.string.field_can_not_be_empty))
            return
        }

        Coroutines.onMainWithScope(viewModelScope) {
            try {
                val response = repository.sevisFeeStep3(
                    streetAddress1!!,
                    streetAddress2!!,
                    country!!, state!!,
                    city!!,
                )

                if (!response.error) {
                    listener3?.onRequestSuccessful(response)

                } else {
                    listener3?.onRequestFailed(response.errorMessage.toString())
                }

            } catch (e: ApiException) {
                listener3?.onRequestFailed(e.errorMessage)
            }catch (e: NoInternetException) {
                listener3?.onRequestFailed("${e.message}")
            }catch (e: SocketTimeOutException) {
                listener3?.onRequestFailed("${e.message}")
            }
        }
    }

    fun fetchSevisCategory() {
        listener2?.onFetchSevisCategoryStarted()

        Coroutines.onMainWithScope(viewModelScope) {
            try {
                val response = repository.fetchSevisCategory()

                if (!response.error) {
                    listener2?.onFetchSevisCategorySuccessful(response)
                }

            } catch (e: ApiException) {
                listener2?.onRequestFailed(e.errorMessage)
            }catch (e: NoInternetException) {
                listener2?.onRequestFailed("${e.message}")
            }catch (e: SocketTimeOutException) {
                listener2?.onRequestFailed("${e.message}")
            }
        }

    }
}
