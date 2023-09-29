package tech.hackcity.educarts.ui.payment.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.hackcity.educarts.data.repositories.intro.OnBoardingRepository
import tech.hackcity.educarts.data.repositories.payment.CheckoutRepository
import tech.hackcity.educarts.ui.intro.OnBoardingViewModel

/**
 *Created by Victor Loveday on 9/28/23
 */
@Suppress("UNCHECKED_CAST")
class CheckoutViewModelFactory(
    private val repository: CheckoutRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CheckoutViewModel(repository) as T
    }
}