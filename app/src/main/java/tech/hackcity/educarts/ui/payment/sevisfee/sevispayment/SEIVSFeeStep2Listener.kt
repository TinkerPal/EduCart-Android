package tech.hackcity.educarts.ui.payment.sevisfee.sevispayment

import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISCategoryResponse
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep2Response
import tech.hackcity.educarts.domain.model.support.MultipleChoiceResponse

/**
 *Created by Victor Loveday on 8/3/23
 */
interface SEIVSFeeStep2Listener {
    fun onRequestStarted()
    fun onFetchSevisCategoryStarted()
    fun onRequestFailed(message: String)
    fun onFetchSevisCategorySuccessful(response: MultipleChoiceResponse)
    fun onRequestSuccessful(response: SEVISFeeStep2Response)
}