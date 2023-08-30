package tech.hackcity.educarts.ui.main

import tech.hackcity.educarts.domain.model.settings.ProfileResponse

/**
 *Created by Victor Loveday on 8/29/23
 */
interface MainListener {
    fun onFetchProfileRequestStarted()
    fun onRequestFailed(message: String)
    fun onFetchProfileRequestSuccessful(response: ProfileResponse)
}