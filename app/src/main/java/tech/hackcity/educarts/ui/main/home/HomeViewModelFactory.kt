package tech.hackcity.educarts.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.hackcity.educarts.data.repositories.DashboardRepository

/**
 *Created by Victor Loveday on 8/19/23
 */
@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(
    private val repository: DashboardRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }
}