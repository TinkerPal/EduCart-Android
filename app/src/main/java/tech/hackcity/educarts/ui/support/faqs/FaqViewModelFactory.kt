package tech.hackcity.educarts.ui.support.faqs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.hackcity.educarts.data.repositories.support.SupportRepository

/**
 *Created by Victor Loveday on 8/14/23
 */
@Suppress("UNCHECKED_CAST")
class FaqViewModelFactory(
    private val repository: SupportRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FaqViewModel(repository) as T
    }
}