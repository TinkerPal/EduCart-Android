package tech.hackcity.educarts.ui.payment.sevisfee.sevispayment.step1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.hackcity.educarts.data.repositories.payment.SEVISFeeRepository

/**
 *Created by Victor Loveday on 8/3/23
 */
@Suppress("UNCHECKED_CAST")
class SEVISFeeStep1ViewModelFactory(
    private val repository: SEVISFeeRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SEVISFeeStep1ViewModel(repository) as T
    }
}