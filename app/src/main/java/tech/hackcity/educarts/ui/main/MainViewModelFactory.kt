package tech.hackcity.educarts.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.hackcity.educarts.data.repositories.MainRepository

/**
 *Created by Victor Loveday on 8/29/23
 */
@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val repository: MainRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}