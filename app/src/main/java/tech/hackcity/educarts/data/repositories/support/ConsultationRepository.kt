package tech.hackcity.educarts.data.repositories.support

import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.network.SafeApiRequest
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.domain.model.support.ConsultationResponse
import tech.hackcity.educarts.domain.model.support.ConsultationStep1Response
import tech.hackcity.educarts.domain.model.support.ConsultationStep2Response

/**
 *Created by Victor Loveday on 8/4/23
 */
class ConsultationRepository(
    private val api: RetrofitInstance,
    private val sharedPreferenceManager: SharePreferencesManager
) : SafeApiRequest() {

    suspend fun fetchConsultationTopics(): ConsultationResponse {
        return apiRequest { api.consultationAPI.fetchConsultationTopics() }
    }

    suspend fun submitConsultationStep1(
        user: String,
        consultation: String,
        details: String
    ): ConsultationStep1Response {
        return apiRequest {
            api.consultationAPI.submitConsultationStep1(
                user,
                consultation,
                details
            )
        }
    }

    suspend fun submitConsultationStep2(
        consultationWay: String,
        phoneNumber: String,
        timeOfConsultation: String,
        date: String,
        time: String
    ): ConsultationStep2Response {
        return apiRequest {
            api.consultationAPI.submitConsultationStep2(
                consultationWay,
                phoneNumber,
                timeOfConsultation,
                date,
                time
            )
        }
    }

    fun fetchUserId(): String? {
        return sharedPreferenceManager.fetchUserId()
    }

}