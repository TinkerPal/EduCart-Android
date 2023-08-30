package tech.hackcity.educarts.ui.payment.sevisfee.sevispayment

import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep1Response

/**
 *Created by Victor Loveday on 8/3/23
 */
interface SEIVSFeeStep1Listener {
    fun onRequestStarted()
    fun onRequestFailed(message: List<ErrorMessage>)
    fun onRequestSuccessful(response: SEVISFeeStep1Response)
}