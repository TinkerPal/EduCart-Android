package tech.hackcity.educarts.ui.support.faqs

import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.domain.model.support.FaqsResponse

/**
 *Created by Victor Loveday on 8/14/23
 */
interface FaqListener {
    fun onRequestStarted()
    fun onRequestFailed(message: String)
    fun onRequestSuccessful(response: FaqsResponse)
}