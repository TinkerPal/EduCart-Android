package tech.hackcity.educarts.ui.payment.applicationfee

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tech.hackcity.educarts.data.repositories.payment.ApplicationFeeRepository
import tech.hackcity.educarts.domain.model.payment.School
import tech.hackcity.educarts.uitls.Constants

/**
 *Created by Victor Loveday on 6/1/23
 */
class ApplicationFeeViewModel(
    private val repository: ApplicationFeeRepository
) : ViewModel() {

    val schoolList = MutableLiveData<List<School>>()

    var applicationFeeListener: ApplicationFeeListener? = null

}