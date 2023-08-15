package tech.hackcity.educarts.ui.support

import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.domain.model.support.ConsultationStep2Response

/**
 *Created by Victor Loveday on 8/4/23
 */
interface ConsultationStep2Listener {
    fun onSubmitConsultationStep2RequestStarted()
    fun onRequestFailed(message: List<ErrorMessage>)
    fun onSubmitConsultationStep2RequestSuccessful(response: ConsultationStep2Response)
}