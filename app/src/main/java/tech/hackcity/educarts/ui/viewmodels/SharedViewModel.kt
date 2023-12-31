package tech.hackcity.educarts.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 *Created by Victor Loveday on 2/20/23
 */
class SharedViewModel : ViewModel() {

    private var toolBarVisibility: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    fun fetchToolBarVisibility(): MutableLiveData<Boolean> {
        return toolBarVisibility
    }

    fun setToolbarVisibility(tv: Boolean){
        toolBarVisibility.postValue(tv)
    }

    private var toolBarColor: MutableLiveData<Int> = MutableLiveData<Int>()

    fun fetchToolbarColor(): MutableLiveData<Int> {
        return toolBarColor
    }

    fun setToolBarColor(color: Int){
        toolBarColor.postValue(color)
    }

    private var horizontalStepViewPosition: MutableLiveData<Int> = MutableLiveData<Int>()

    fun fetchHorizontalStepViewPosition(): MutableLiveData<Int> {
        return horizontalStepViewPosition
    }

    fun updateHorizontalStepViewPosition(position: Int) {
        horizontalStepViewPosition.postValue(position)
    }

    private var destination: MutableLiveData<String> = MutableLiveData<String>()

    fun getDestination(): MutableLiveData<String> {
        return destination
    }

    fun updateDestination(d: String) {
        destination.postValue(d)
    }

    //This array will only have two element
    // The first index is the current step
    // The second index is the overall step
    private var stepIndicator: MutableLiveData<Array<Int>> = MutableLiveData<Array<Int>>()

    fun getStepIndicator(): MutableLiveData<Array<Int>> {
        return stepIndicator
    }

    fun updateStepIndicator(step: Array<Int>) {
        stepIndicator.postValue(step)
    }

    private var screenLoader: MutableLiveData<Pair<Boolean, String>> = MutableLiveData()

    fun isScreenLoading(): MutableLiveData<Pair<Boolean, String>> {
        return screenLoader
    }

    fun updateScreenLoader(isScreenLoading: Pair<Boolean, String>) {
        screenLoader.postValue(isScreenLoading)
    }
}