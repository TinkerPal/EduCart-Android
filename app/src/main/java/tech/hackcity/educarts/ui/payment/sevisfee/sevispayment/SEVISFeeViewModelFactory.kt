package tech.hackcity.educarts.ui.payment.sevisfee.sevispayment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.hackcity.educarts.data.repositories.payment.SEVISFeeRepository

/**
 *Created by Victor Loveday on 8/3/23
 */
@Suppress("UNCHECKED_CAST")
class SEVISFeeViewModelFactory(
    private val repository: SEVISFeeRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SEVISFeeViewModel(repository) as T
    }
}