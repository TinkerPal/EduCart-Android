package tech.hackcity.educarts.ui.payment.sevisfee.sevispayment

import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep1Response
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep2Response

/**
 *Created by Victor Loveday on 8/3/23
 */
interface SEIVSFeeStep2Listener {
    fun onRequestStarted()
    fun onRequestFailed(message: List<ErrorMessage>)
    fun onRequestSuccessful(response: SEVISFeeStep2Response)
}