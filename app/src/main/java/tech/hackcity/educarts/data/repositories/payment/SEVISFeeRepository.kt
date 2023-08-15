package tech.hackcity.educarts.data.repositories.payment

import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.network.SafeApiRequest
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep1Response
import tech.hackcity.educarts.uitls.createPartFromString
import tech.hackcity.educarts.uitls.prepareFilePart

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
        formFileUri: Uri,
        passportFileUri: Uri,
        internationalPassportFileUri: Uri

    ): SEVISFeeStep1Response {
        val userRequestBody = createPartFromString(user)
        val sevisIdRequestBody = createPartFromString(sevis_id)
        val lastNameRequestBody = createPartFromString(last_name)
        val givenNameRequestBody = createPartFromString(given_name)
        val dateOfBirthRequestBody = createPartFromString(date_of_birth)

        val formFile = prepareFilePart("form", formFileUri)
        val passportFile = prepareFilePart("passport", passportFileUri)
        val internationalPassportFile = prepareFilePart("international_passport", internationalPassportFileUri)


        return apiRequest {
            api.sevisFeeAPI.sevisFeeStep1(
                userRequestBody,
                sevisIdRequestBody,
                lastNameRequestBody,
                givenNameRequestBody,
                dateOfBirthRequestBody,
                formFile,
                passportFile,
                internationalPassportFile
            )
        }
    }

    fun fetchUserId(): String? {
        return sharedPreferenceManager.fetchUserId()
    }
}