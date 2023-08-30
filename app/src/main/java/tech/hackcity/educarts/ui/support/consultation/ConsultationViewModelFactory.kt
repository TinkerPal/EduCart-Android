package tech.hackcity.educarts.ui.support.consultation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.hackcity.educarts.data.repositories.support.SupportRepository

/**
 *Created by Victor Loveday on 8/4/23
 */
@Suppress("UNCHECKED_CAST")
class ConsultationViewModelFactory(
    private val repository: SupportRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ConsultationViewModel(repository) as T
    }
}