package tech.hackcity.educarts.ui.auth.getStarted

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import tech.hackcity.educarts.data.network.ApiException
import tech.hackcity.educarts.data.repositories.RegionRepository
import tech.hackcity.educarts.uitls.Coroutines

/**
 *Created by Victor Loveday on 9/21/23
 */
class RegionsViewModel(
    private val repository: RegionRepository,
) : ViewModel() {

    var listener: GetStartedListener? = null

    fun fetchRegions() {
        listener?.onFetchRegionsStarted()

        Coroutines.onMainWithScope(viewModelScope) {
            try {
                val response = repository.fetchRegions()

                if (!response.error) {
                    listener?.onFetchRegionSuccessful(response)

                } else {
                    listener?.onFetchRequestFailed(response.errorMessage.toString())
                }

            } catch (e: ApiException) {
                listener?.onFetchRequestFailed(e.message!!)
            }
        }

    }

    fun saveAppSetupStatus(isAppSetup: Boolean) {
        Coroutines.onMain {
            repository.saveAppSetupStatus(isAppSetup)
        }
    }

    fun fetchIsGetStartedPressed(): Boolean {
        return repository.fetchIsGetStartedPressed()
    }

    fun fetchLoginStatus(): Boolean {
        return repository.fetchLoginStatus()
    }

}