package tech.hackcity.educarts.ui.payment.orderdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.hackcity.educarts.data.repositories.MainRepository
import tech.hackcity.educarts.data.repositories.payment.OrderRepository

/**
 *Created by Victor Loveday on 8/29/23
 */
@Suppress("UNCHECKED_CAST")
class OrderDetailsViewModelFactory(private val repository: OrderRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OrderDetailsViewModel(repository) as T
    }
}