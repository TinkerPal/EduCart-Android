package tech.hackcity.educarts.ui.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.hackcity.educarts.data.repositories.intro.OnBoardingRepository

/**
 *Created by Victor Loveday on 8/3/23
 */
@Suppress("UNCHECKED_CAST")
class OnBoardingViewModelFactory(
    private val repository: OnBoardingRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OnBoardingViewModel(repository) as T
    }
}