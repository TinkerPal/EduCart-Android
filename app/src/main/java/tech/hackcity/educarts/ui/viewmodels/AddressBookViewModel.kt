package tech.hackcity.educarts.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.hackcity.educarts.data.UserDatabase
import tech.hackcity.educarts.data.model.AddressBook
import tech.hackcity.educarts.data.repositories.AddressBookRepository

/**
 *Created by Victor Loveday on 2/23/23
 */
class AddressBookViewModel(application: Application) : AndroidViewModel(application) {
    
    val readAllAddresses: LiveData<List<AddressBook>>
    private val addressBookRepository: AddressBookRepository
    
    init {
        val addressBookDao = UserDatabase.getDatabase(application).addressBookDao()
        addressBookRepository = AddressBookRepository(addressBookDao)
        readAllAddresses = addressBookRepository.readAllAddresses
        
    }
    
    fun saveAddress(address: AddressBook) {
        viewModelScope.launch(Dispatchers.IO) {  
            addressBookRepository.saveAddress(address)
        }
    }
    
    fun deleteAddress(address: AddressBook) {
        viewModelScope.launch(Dispatchers.IO) {  
            addressBookRepository.deleteAddress(address)
        }
    }
}