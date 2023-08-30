package tech.hackcity.educarts.ui.settings.profile

import tech.hackcity.educarts.domain.model.settings.ProfileResponse

/**
 *Created by Victor Loveday on 8/25/23
 */
interface ProfileListener {
    fun onFetchProfileRequestStarted()
    fun onRequestFailed(message: String)
    fun onFetchProfileRequestSuccessful(response: ProfileResponse)
}