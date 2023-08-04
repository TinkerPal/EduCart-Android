package tech.hackcity.educarts.data.repositories.intro

import tech.hackcity.educarts.data.storage.SharePreferencesManager

/**
 *Created by Victor Loveday on 8/3/23
 */
class OnBoardingRepository(private val sharePreferencesManager: SharePreferencesManager) {

    fun saveIsGetStartedPressed(isGetStartedPressed: Boolean) {
        sharePreferencesManager.saveIsGetStartedPressed(isGetStartedPressed)
    }

    fun fetchIsGetStartedPressed(): Boolean{
        return sharePreferencesManager.fetchIsGetStartedPressed()
    }

    fun fetchLoginStatus(): Boolean {
        return sharePreferencesManager.fetchLoginStatus()
    }
}