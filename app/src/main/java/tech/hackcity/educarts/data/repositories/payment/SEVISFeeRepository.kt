package tech.hackcity.educarts.data.repositories.payment

import okhttp3.MultipartBody
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.network.SafeApiRequest
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISCategoryResponse
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep1Response
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep2Response
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep3Response

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
                form,
                passport,
                internationalPassport,
                user,
                sevis_id,
                last_name,
                given_name,
                date_of_birth
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

    suspend fun sevisFeeStep3(
        streetAddress1: String,
        streetAddress2: String,
        country: String,
        state: String,
        city: String,

        ): SEVISFeeStep3Response {
        return apiRequest {
            api.sevisFeeAPI.sevisFeeStep3(
                streetAddress1,
                streetAddress2,
                country,
                state,
                city
            )
        }
    }

    suspend fun fetchSevisCategory(): SEVISCategoryResponse {
        return apiRequest {
            api.sevisFeeAPI.fetchSevisCategory()
        }
    }

    fun fetchUserId(): String? {
        return sharedPreferenceManager.fetchUserId()
    }
}