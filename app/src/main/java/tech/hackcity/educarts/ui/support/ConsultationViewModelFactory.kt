package tech.hackcity.educarts.ui.support

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.hackcity.educarts.data.repositories.support.ConsultationRepository

/**
 *Created by Victor Loveday on 8/4/23
 */
@Suppress("UNCHECKED_CAST")
class ConsultationViewModelFactory(
    private val repository: ConsultationRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ConsultationViewModel(repository) as T
    }
}