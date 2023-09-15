package tech.hackcity.educarts.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import tech.hackcity.educarts.data.network.ApiException
import tech.hackcity.educarts.data.repositories.MainRepository
import tech.hackcity.educarts.domain.model.auth.User
import tech.hackcity.educarts.domain.model.settings.ProfileResponseData
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.NoInternetException
import tech.hackcity.educarts.uitls.clearExtraCharacters

/**
 *Created by Victor Loveday on 8/29/23
 */
class MainViewModel(private val repository: MainRepository) : ViewModel() {

    var listener: MainListener? = null

    fun fetchProfile() {
        listener?.onFetchProfileRequestStarted()

        Coroutines.onMainWithScope(viewModelScope) {
            try {
                val response = repository.fetchProfile()

                if (!response.error) {
                    listener?.onFetchProfileRequestSuccessful(response)

                } else {
                    listener?.onRequestFailed(response.errorMessage.toString())
                }

            } catch (e: ApiException) {
                listener?.onRequestFailed(e.message!!)
            }catch (e: NoInternetException) {
                listener?.onRequestFailed("${e.message}")
            }
        }

    }


}