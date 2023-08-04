package tech.hackcity.educarts.data.repositories.payment

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.network.SafeApiRequest
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep1Response

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
        international_passport: MultipartBody.Part

    ): SEVISFeeStep1Response {
        val userIDRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), user)
        val sevisIDRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), sevis_id)
        val lastNameRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), last_name)
        val givenNameRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), given_name)
        val dobRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), date_of_birth)

        return apiRequest {
            api.sevisFeeAPI.sevisFeeStep1(
                userIDRequestBody,
                sevisIDRequestBody,
                lastNameRequestBody,
                givenNameRequestBody,
                dobRequestBody,
                form,
                passport,
                international_passport
            )
        }
    }

    fun fetchUserId(): String? {
        return sharedPreferenceManager.fetchUserId()
    }
}