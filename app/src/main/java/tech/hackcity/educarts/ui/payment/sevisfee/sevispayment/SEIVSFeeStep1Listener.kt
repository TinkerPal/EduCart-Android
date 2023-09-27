package tech.hackcity.educarts.ui.payment.sevisfee.sevispayment

import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep1Response
import tech.hackcity.educarts.domain.model.payment.sevis.SevisCouponStep2Response

/**
 *Created by Victor Loveday on 8/3/23
 */
interface SEIVSFeeStep1Listener {
    fun onRequestStarted()
    fun onRequestFailed(message: String)
    fun onSevisStep1RequestSuccessful(response: SEVISFeeStep1Response)
    fun onSevisCouponStep21RequestSuccessful(response: SevisCouponStep2Response)
}