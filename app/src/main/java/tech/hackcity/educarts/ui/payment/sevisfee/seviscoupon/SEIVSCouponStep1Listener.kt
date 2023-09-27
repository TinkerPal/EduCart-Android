package tech.hackcity.educarts.ui.payment.sevisfee.seviscoupon

import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep1Response
import tech.hackcity.educarts.domain.model.payment.sevis.SevisCouponStep1Response

/**
 *Created by Victor Loveday on 8/3/23
 */
interface SEIVSCouponStep1Listener {
    fun onRequestStarted()
    fun onRequestFailed(message: String)
    fun onRequestSuccessful(response: SevisCouponStep1Response)
}