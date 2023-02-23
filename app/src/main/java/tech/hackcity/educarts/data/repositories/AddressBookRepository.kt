package tech.hackcity.educarts.data.repositories

import androidx.lifecycle.LiveData
import tech.hackcity.educarts.data.dao.AddressBookDao
import tech.hackcity.educarts.data.model.AddressBook

/**
 *Created by Victor Loveday on 2/23/23
 */
class AddressBookRepository(private val addressBookDao: AddressBookDao) {

    val readAllAddresses: LiveData<List<AddressBook>> = addressBookDao.readAllAddresses()

    suspend fun saveAddress(address: AddressBook) {
        addressBookDao.saveAddress(address)
    }

    suspend fun deleteAddress(address: AddressBook) {
        addressBookDao.deleteAddress(address)
    }
}