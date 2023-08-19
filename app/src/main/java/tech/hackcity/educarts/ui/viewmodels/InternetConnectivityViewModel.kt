package tech.hackcity.educarts.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import tech.hackcity.educarts.uitls.InternetConnectivityUtil

/**
 *Created by Victor Loveday on 8/19/23
 */


class InternetConnectivityViewModel(application: Application) : AndroidViewModel(application) {

    private val _isInternetConnected = MutableLiveData<Boolean>()
    val isInternetConnected: LiveData<Boolean>
        get() = _isInternetConnected

    fun checkInternetConnectivity() {
        val context = getApplication<Application>().applicationContext
        val isConnected = InternetConnectivityUtil.isInternetConnected(context)
        _isInternetConnected.value = isConnected
    }
}
