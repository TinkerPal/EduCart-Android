package tech.hackcity.educarts.data.repositories.support

import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.network.SafeApiRequest
import tech.hackcity.educarts.data.storage.SessionManager
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.domain.model.support.ConsultationResponse

/**
 *Created by Victor Loveday on 8/4/23
 */
class ConsultationRepository(
    private val api: RetrofitInstance,
    private val sharedPreferenceManager: SharePreferencesManager
): SafeApiRequest() {

    suspend fun fetchConsultationTopics() : ConsultationResponse {
        return apiRequest { api.consultationAPI.fetchConsultationTopics() }
    }

    fun fetchUserId(): String? {
        return sharedPreferenceManager.fetchUserId()
    }

}