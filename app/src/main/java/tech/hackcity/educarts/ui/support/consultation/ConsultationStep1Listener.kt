package tech.hackcity.educarts.ui.support.consultation

import tech.hackcity.educarts.domain.model.support.ConsultationStep1Response
import tech.hackcity.educarts.domain.model.support.MultipleChoiceResponse

/**
 *Created by Victor Loveday on 8/4/23
 */
interface ConsultationStep1Listener {
    fun onFetchConsultationTopicsRequestStarted()
    fun onSubmitConsultationStep1RequestStarted()
    fun onRequestFailed(message: String)
    fun onFetchConsultationTopicsRequestSuccessful(response: MultipleChoiceResponse)
    fun onSubmitConsultationStep1RequestSuccessful(response: ConsultationStep1Response)
}