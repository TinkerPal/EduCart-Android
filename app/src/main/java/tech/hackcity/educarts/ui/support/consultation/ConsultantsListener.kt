package tech.hackcity.educarts.ui.support.consultation

import tech.hackcity.educarts.domain.model.support.ConsultantsResponse
import tech.hackcity.educarts.domain.model.support.ConsultationStep2Response

/**
 *Created by Victor Loveday on 8/4/23
 */
interface ConsultantsListener {
    fun onFetchConsultantsRequestStarted()
    fun onRequestFailed(message: String)
    fun onFetchConsultantsRequestSuccessful(response: ConsultantsResponse)
}