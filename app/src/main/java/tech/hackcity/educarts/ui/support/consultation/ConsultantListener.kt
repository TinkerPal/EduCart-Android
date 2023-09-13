package tech.hackcity.educarts.ui.support.consultation

import tech.hackcity.educarts.domain.model.support.ConsultantProfileResponse
import tech.hackcity.educarts.domain.model.support.ConsultantsResponse
import tech.hackcity.educarts.domain.model.support.ConsultationStep2Response

/**
 *Created by Victor Loveday on 8/4/23
 */
interface ConsultantListener {
    fun onFetchConsultantRequestStarted()
    fun onRequestFailed(message: String)
    fun onFetchConsultantRequestSuccessful(response: ConsultantProfileResponse)
}