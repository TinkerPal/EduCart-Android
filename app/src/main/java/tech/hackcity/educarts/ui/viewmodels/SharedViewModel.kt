package tech.hackcity.educarts.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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
}