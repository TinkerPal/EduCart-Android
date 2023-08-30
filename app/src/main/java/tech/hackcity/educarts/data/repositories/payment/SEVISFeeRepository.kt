package tech.hackcity.educarts.data.repositories.payment

import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.network.SafeApiRequest
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep1Response
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep2Response

/**
 *Created by Victor Loveday on 8/3/23
 */
class SEVISFeeRepository(
    private val api: RetrofitInstance,
    private val sharedPreferenceManager: SharePreferencesManager
) : SafeApiRequest() {

    suspend fun sevisFeeStep1(
        user: String,
        sevis_id: String,
        last_name: String,
        given_name: String,
        date_of_birth: String,
        form: MultipartBody.Part,
        passport: MultipartBody.Part,
        internationalPassport: MultipartBody.Part

    ): SEVISFeeStep1Response {
        return apiRequest {
            api.sevisFeeAPI.sevisFeeStep1(
                user,
                sevis_id,
                last_name,
                given_name,
                date_of_birth,
                form,
                passport,
                internationalPassport
            )
        }
    }

    suspend fun sevisFeeStep2(
        formType: String,
        category: String,
        email: String,
        phoneNumber: String,
        countryOfCitizenship: String,
        countryOfBirth: String

    ): SEVISFeeStep2Response {
        return apiRequest {
            api.sevisFeeAPI.sevisFeeStep2(
                formType,
                category,
                email,
                phoneNumber,
                countryOfCitizenship,
                countryOfBirth
            )
        }
    }

    fun fetchUserId(): String? {
        return sharedPreferenceManager.fetchUserId()
    }
}