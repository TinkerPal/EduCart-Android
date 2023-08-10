package tech.hackcity.educarts.ui.support

import androidx.lifecycle.ViewModel
import retrofit2.HttpException
import tech.hackcity.educarts.data.network.ErrorCodes
import tech.hackcity.educarts.data.repositories.support.ConsultationRepository
import tech.hackcity.educarts.domain.model.auth.User
import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.NoInternetException
import tech.hackcity.educarts.uitls.errorMessageFetcher
import java.io.IOException
import java.net.SocketTimeoutException

/**
 *Created by Victor Loveday on 8/4/23
 */
class ConsultationViewModel(
    private val repository: ConsultationRepository
): ViewModel() {

    val listener: ConsultationStep1Listener? = null

    fun fetchConsultationTopics() {
        Coroutines.main {
            val response = try {
                repository.fetchConsultationTopics()

            } catch (e: java.io.IOException) {
                listener?.onRequestFailed(listOf(ErrorMessage(ErrorCodes.IO_EXCEPTION, e.message!!)))
                return@main

            } catch (e: NoInternetException) {
                listener?.onRequestFailed(listOf(ErrorMessage(ErrorCodes.NO_INTERNET_CONNECTION, e.message!!)))
                return@main

            } catch (e: HttpException) {
                listener?.onRequestFailed(listOf(ErrorMessage(ErrorCodes.HTTP_EXCEPTION, e.message!!)))
                return@main

            } catch (e: SocketTimeoutException) {
                listener?.onRequestFailed(listOf(ErrorMessage(ErrorCodes.STO_EXCEPTION, e.message!!)))
                return@main
            }

            if (!response.error) {
                listener?.onFetchConsultationTopicsRequestSuccessful(response)

            } else {
                listener?.onRequestFailed(response.errorMessage)
            }
        }

    }
}