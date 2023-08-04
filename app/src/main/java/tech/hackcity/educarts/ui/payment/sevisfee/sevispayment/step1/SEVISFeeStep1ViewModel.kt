package tech.hackcity.educarts.ui.payment.sevisfee.sevispayment.step1

import android.content.Context
import androidx.lifecycle.ViewModel
import okhttp3.MultipartBody
import retrofit2.HttpException
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.ErrorCodes.EMPTY_FORM_FIELD
import tech.hackcity.educarts.data.network.ErrorCodes.HTTP_EXCEPTION
import tech.hackcity.educarts.data.network.ErrorCodes.IO_EXCEPTION
import tech.hackcity.educarts.data.network.ErrorCodes.NO_INTERNET_CONNECTION
import tech.hackcity.educarts.data.network.ErrorCodes.STO_EXCEPTION
import tech.hackcity.educarts.data.repositories.payment.SEVISFeeRepository
import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.ui.payment.sevisfee.SEIVSFeeStep1Listener
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.NoInternetException
import java.net.SocketTimeoutException

/**
 *Created by Victor Loveday on 8/3/23
 */
class SEVISFeeStep1ViewModel(
    private val repository: SEVISFeeRepository
) : ViewModel() {

    var listener: SEIVSFeeStep1Listener? = null

    var user: String? = null
    var sevis_id: String? = null
    var last_name: String? = null
    var given_name: String? = null
    var date_of_birth: String? = null
    var form: MultipartBody.Part? = null
    var passport: MultipartBody.Part? = null
    var international_passport: MultipartBody.Part? = null

    fun submitStep1(context: Context) {

        if (
            user.isNullOrEmpty() || sevis_id.isNullOrEmpty()
            || last_name.isNullOrEmpty() || given_name.isNullOrEmpty()
            || date_of_birth.isNullOrEmpty() || form == null
            || passport == null || international_passport == null
        ){
            listener?.onRequestFailed(listOf(ErrorMessage(EMPTY_FORM_FIELD, context.resources.getString(R.string.field_can_not_be_empty))))

            return
        }

        Coroutines.main {
            val response = try {
                repository.sevisFeeStep1(
                    user!!,
                    sevis_id!!,
                    last_name!!,
                    given_name!!,
                    date_of_birth!!,
                    form!!,
                    passport!!,
                    international_passport!!
                )

            } catch (e: java.io.IOException) {
                listener?.onRequestFailed(listOf(ErrorMessage(IO_EXCEPTION, e.message!!)))
                return@main

            } catch (e: NoInternetException) {
                listener?.onRequestFailed(listOf(ErrorMessage(NO_INTERNET_CONNECTION, e.message!!)))
                return@main

            } catch (e: HttpException) {
                listener?.onRequestFailed(listOf(ErrorMessage(HTTP_EXCEPTION, e.message!!)))
                return@main

            } catch (e: SocketTimeoutException) {
                listener?.onRequestFailed(listOf(ErrorMessage(STO_EXCEPTION, e.message!!)))
                return@main
            }

            if (!response.error) {
                listener?.onRequestSuccessful(response)

            } else {
                listener?.onRequestFailed(response.errorMessage)
            }
        }

    }

}