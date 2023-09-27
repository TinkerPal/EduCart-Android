package tech.hackcity.educarts.ui.auth.getStarted

import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.domain.model.location.RegionResponse

/**
 *Created by Victor Loveday on 9/22/23
 */
interface GetStartedListener {
    fun onFetchRegionsStarted()
    fun onFetchRequestFailed(errorMessage: String)
    fun onFetchRegionSuccessful(response: RegionResponse)
}