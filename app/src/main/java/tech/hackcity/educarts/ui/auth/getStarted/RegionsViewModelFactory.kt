package tech.hackcity.educarts.ui.auth.getStarted

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.hackcity.educarts.data.repositories.RegionRepository

/**
 *Created by Victor Loveday on 8/3/23
 */
@Suppress("UNCHECKED_CAST")
class RegionsViewModelFactory(
    private val repository: RegionRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegionsViewModel(repository) as T
    }
}