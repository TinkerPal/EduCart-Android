package tech.hackcity.educarts.ui.support.consultation

import android.content.Context
import androidx.lifecycle.ViewModel
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.ApiException
import tech.hackcity.educarts.data.network.ErrorCodes
import tech.hackcity.educarts.data.repositories.support.SupportRepository
import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.NoInternetException
import tech.hackcity.educarts.uitls.SocketTimeOutException

/**
 *Created by Victor Loveday on 8/4/23
 */
class ConsultationViewModel(
    private val repository: SupportRepository
) : ViewModel() {

    var step1listener: ConsultationStep1Listener? = null
    var step2listener: ConsultationStep2Listener? = null

    var consultation: String? = null
    var detail: String? = null
    var consultationWay: String? = null
    var phoneNumber: String? = null
    var timeOfConsultation: String? = null
    var date: String? = null
    var time: String? = null

    fun fetchConsultationTopics() {
        step1listener?.onFetchConsultationTopicsRequestStarted()

        Coroutines.onMain {
            try {
                val response = repository.fetchConsultationTopics()

                if (!response.error) {
                    step1listener?.onFetchConsultationTopicsRequestSuccessful(response)
                } else {
                    step1listener?.onRequestFailed(response.errorMessage.toString())
                }

            } catch (e: ApiException) {
                step1listener?.onRequestFailed(e.errorMessage)
            }catch (e: NoInternetException) {
                step1listener?.onRequestFailed("${e.message}")
            }catch (e: SocketTimeOutException) {
                step1listener?.onRequestFailed("${e.message}")
            }
        }
    }

    fun submitConsultationStep1(context: Context) {
        step1listener?.onSubmitConsultationStep1RequestStarted()

        if (consultation.isNullOrEmpty() || detail.isNullOrEmpty()) {
            step1listener?.onRequestFailed(context.resources.getString(R.string.field_can_not_be_empty))
            return
        }

        Coroutines.onMain {
            try {
                val response = repository.submitConsultationStep1(
                    repository.fetchUserId()!!,
                    consultation!!,
                    detail!!
                )

                if (!response.error) {
                    step1listener?.onSubmitConsultationStep1RequestSuccessful(response)
                } else {
                    step1listener?.onRequestFailed(response.errorMessage.toString())
                }

            } catch (e: ApiException) {
                step1listener?.onRequestFailed(e.errorMessage)
            }catch (e: NoInternetException) {
                step1listener?.onRequestFailed("${e.message}")
            }catch (e: SocketTimeOutException) {
                step1listener?.onRequestFailed("${e.message}")
            }
        }

    }

    fun submitConsultationStep2(context: Context) {
        step2listener?.onSubmitConsultationStep2RequestStarted()

        if (
            consultationWay.isNullOrEmpty() ||
            phoneNumber.isNullOrEmpty() ||
            timeOfConsultation.isNullOrEmpty() ||
            date.isNullOrEmpty() ||
            time.isNullOrEmpty()
        ) {
            step2listener?.onRequestFailed(context.resources.getString(R.string.field_can_not_be_empty))
            return
        }

        Coroutines.onMain {
            try {
                val response = repository.submitConsultationStep2(
                    consultationWay!!,
                    phoneNumber!!,
                    timeOfConsultation!!,
                    date!!,
                    time!!
                )

                if (!response.error) {
                    step2listener?.onSubmitConsultationStep2RequestSuccessful(response)
                } else {
                    step2listener?.onRequestFailed(response.errorMessage.toString())
                }

            } catch (e: ApiException) {
                step2listener?.onRequestFailed(e.errorMessage)
            }catch (e: NoInternetException) {
                step2listener?.onRequestFailed("${e.message}")
            }catch (e: SocketTimeOutException) {
                step2listener?.onRequestFailed("${e.message}")
            }
        }

    }
}