package tech.hackcity.educarts.ui.support

import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.domain.model.support.ConsultationResponse

/**
 *Created by Victor Loveday on 8/4/23
 */
interface ConsultationStep1Listener {
    fun onFetchConsultationTopicsRequestStarted()
    fun onSubmitConsultationStep1RequestStarted()
    fun onRequestFailed(message: List<ErrorMessage>)
    fun onFetchConsultationTopicsRequestSuccessful(response: ConsultationResponse)
    fun onSubmitConsultationStep1RequestSuccessful(response: ConsultationResponse)
}