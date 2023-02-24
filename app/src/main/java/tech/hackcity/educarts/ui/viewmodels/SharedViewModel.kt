package tech.hackcity.educarts.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tech.hackcity.educarts.data.model.AddressBook

/**
 *Created by Victor Loveday on 2/20/23
 */
class SharedViewModel : ViewModel() {

    private var toolBarVisibility: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    fun isToolbarVisible(): MutableLiveData<Boolean> {
        return toolBarVisibility
    }

    fun setToolbarVisibility(tv: Boolean){
        toolBarVisibility.postValue(tv)
    }

    private var destination: MutableLiveData<String> = MutableLiveData<String>()

    fun getDestination(): MutableLiveData<String> {
        return destination
    }

    fun updateDestination(d: String) {
        destination.postValue(d)
    }

    private var address: MutableLiveData<AddressBook> = MutableLiveData<AddressBook>()

    fun getAddress(): MutableLiveData<AddressBook> {
        return address
    }

    fun updateAddress(ad: AddressBook) {
        address.postValue(ad)
    }
}