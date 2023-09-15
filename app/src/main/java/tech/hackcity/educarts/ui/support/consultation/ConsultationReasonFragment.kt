package tech.hackcity.educarts.ui.support.consultation

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.ErrorCodes
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.support.SupportRepository
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.databinding.FragmentConsultationReasonBinding
import tech.hackcity.educarts.domain.model.support.ConsultationStep1Response
import tech.hackcity.educarts.domain.model.support.MultipleChoiceResponse
import tech.hackcity.educarts.domain.model.support.MultipleChoiceResponseData
import tech.hackcity.educarts.ui.alerts.ToastType
import tech.hackcity.educarts.ui.settings.SettingsActivity
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.enablePrimaryButtonState
import tech.hackcity.educarts.uitls.extractErrorMessagesFromErrorBody
import tech.hackcity.educarts.uitls.hideButtonLoadingState
import tech.hackcity.educarts.uitls.showButtonLoadingState
import tech.hackcity.educarts.uitls.showCustomInfoDialog
import tech.hackcity.educarts.uitls.toast

/**
 *Created by Victor Loveday on 5/17/23
 */
class ConsultationReasonFragment : Fragment(R.layout.fragment_consultation_reason),
    ConsultationReasonListener {

    private lateinit var binding: FragmentConsultationReasonBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var viewModel: ConsultationViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentConsultationReasonBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val api = RetrofitInstance(requireContext())
        val sharePreferencesManager = SharePreferencesManager(requireContext())
        val repository = SupportRepository(api, sharePreferencesManager)
        val factory = ConsultationViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[ConsultationViewModel::class.java]
        viewModel.consultationReasonListener = this

        Coroutines.onMainWithScope(viewModel.viewModelScope) {
            viewModel.fetchConsultationTopics()
        }

        binding.nextBtn.setOnClickListener {
            viewModel.consultation = binding.question.text.toString()
            viewModel.detail = binding.detailsET.text.toString().trim()

            Coroutines.onMainWithScope(viewModel.viewModelScope) {
                viewModel.submitConsultationStep1(requireContext())
            }
        }
    }

    private fun setupConsultationCategory(data: List<MultipleChoiceResponseData>) {
        val options = mutableListOf<String>()

        for (i in data) {
            options.add(i.choice)
        }

        val arrayAdapter1 =
            ArrayAdapter(
                requireContext(),
                R.layout.security_questions_item,
                options
            )
        binding.question.setAdapter(arrayAdapter1)
    }

    override fun onFetchConsultationTopicsRequestStarted() {
        sharedViewModel.updateLoadingScreen(true)
    }

    override fun onSubmitConsultationStep1RequestStarted() {
        showButtonLoadingState(binding.nextBtn, binding.progressBar, "")
        sharedViewModel.updateLoadingScreen(true)
    }

    override fun onRequestFailed(errorMessage: String) {
        sharedViewModel.updateLoadingScreen(false)
        hideButtonLoadingState(binding.nextBtn, binding.progressBar, resources.getString(R.string.next))
        context?.toast(description = errorMessage, toastType = ToastType.ERROR)

        val errorMessages = extractErrorMessagesFromErrorBody(errorMessage)
        if (errorMessages.isNotEmpty()) {
            for ((code, message) in errorMessages) {
                if (code == ErrorCodes.PROFILE_NOT_COMPLETED) {
                    showCustomInfoDialog(
                        requireContext(),
                        resources.getString(R.string.incomplete_profile),
                        resources.getString(R.string.complete_profile_to_enjoy_free_consultation),
                        resources.getString(R.string.complete_profile ),
                        activity = SettingsActivity::class.java,
                        destination = "edit_profile",
                        cancelable = true
                    )
                }
            }
        }
    }

    override fun onFetchConsultationTopicsRequestSuccessful(response: MultipleChoiceResponse) {
        setupConsultationCategory(response.data)
        enablePrimaryButtonState(binding.nextBtn)
        sharedViewModel.updateLoadingScreen(false)
    }

    override fun onSubmitConsultationStep1RequestSuccessful(response: ConsultationStep1Response) {
        hideButtonLoadingState(binding.nextBtn, binding.progressBar, resources.getString(R.string.next))
        findNavController().navigate(R.id.action_consultationReasonFragment_to_chooseAConsultantFragment)
        sharedViewModel.updateLoadingScreen(false)
    }

    override fun onResume() {
        super.onResume()
    }

}