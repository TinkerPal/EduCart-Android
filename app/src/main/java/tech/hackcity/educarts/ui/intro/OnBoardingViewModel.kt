package tech.hackcity.educarts.ui.intro

import androidx.lifecycle.ViewModel
import tech.hackcity.educarts.data.repositories.intro.OnBoardingRepository
import tech.hackcity.educarts.uitls.Coroutines

/**
 *Created by Victor Loveday on 8/3/23
 */
class OnBoardingViewModel(
    val repository: OnBoardingRepository
): ViewModel() {

    fun fetchIsGetStartedPressed(): Boolean {
        return repository.fetchIsGetStartedPressed()
    }

    fun fetchLoginStatus(): Boolean {
        return repository.fetchLoginStatus()
    }
}