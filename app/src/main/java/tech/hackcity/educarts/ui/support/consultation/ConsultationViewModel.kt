package tech.hackcity.educarts.ui.support.consultation

import android.content.Context
import androidx.lifecycle.ViewModel
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.ApiException
import tech.hackcity.educarts.data.repositories.support.SupportRepository
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.NoInternetException
import tech.hackcity.educarts.uitls.SocketTimeOutException

/**
 *Created by Victor Loveday on 8/4/23
 */
class ConsultationViewModel(
    private val repository: SupportRepository
) : ViewModel() {

    var consultationReasonListener: ConsultationReasonListener? = null
    var consultantsListener: ConsultantsListener? = null
    var consultantListener: ConsultantListener? = null

    var consultation: String? = null
    var detail: String? = null
    var consultationWay: String? = null
    var phoneNumber: String? = null
    var timeOfConsultation: String? = null
    var date: String? = null
    var time: String? = null

    fun fetchConsultationTopics() {
        consultationReasonListener?.onFetchConsultationTopicsRequestStarted()

        Coroutines.onMain {
            try {
                val response = repository.fetchConsultationTopics()

                if (!response.error) {
                    consultationReasonListener?.onFetchConsultationTopicsRequestSuccessful(response)
                }

            } catch (e: ApiException) {
                consultationReasonListener?.onRequestFailed(e.errorMessage)
            }
        }
    }

    fun submitConsultationStep1(context: Context) {
        consultationReasonListener?.onSubmitConsultationStep1RequestStarted()

        if (consultation.isNullOrEmpty() || detail.isNullOrEmpty()) {
            consultationReasonListener?.onRequestFailed(context.resources.getString(R.string.field_can_not_be_empty))
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
                    consultationReasonListener?.onSubmitConsultationStep1RequestSuccessful(response)
                }

            } catch (e: ApiException) {
                consultationReasonListener?.onRequestFailed(e.errorMessage)
            }
        }

    }

    fun fetchConsultants() {
        consultantsListener?.onFetchConsultantsRequestStarted()

        Coroutines.onMain {
            try {
                val response = repository.fetchConsultants()

                if (!response.error) {
                    consultantsListener?.onFetchConsultantsRequestSuccessful(response)
                }

            } catch (e: ApiException) {
                consultantsListener?.onRequestFailed(e.errorMessage)
            }
        }
    }

    fun fetchConsultantProfile(id: Int) {
        consultantListener?.onFetchConsultantRequestStarted()

        Coroutines.onMain {
            try {
                val response = repository.fetchConsultantProfile(id)

                if (!response.error) {
                    consultantListener?.onFetchConsultantRequestSuccessful(response)
                }

            } catch (e: ApiException) {
                consultantListener?.onRequestFailed(e.errorMessage)
            }
        }
    }

}