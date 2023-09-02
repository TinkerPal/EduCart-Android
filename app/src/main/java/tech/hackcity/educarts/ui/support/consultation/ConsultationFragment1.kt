package tech.hackcity.educarts.ui.support.consultation

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.support.SupportRepository
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.databinding.FragmentConsultation1Binding
import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.domain.model.support.ConsultationResponse
import tech.hackcity.educarts.domain.model.support.ConsultationResponseData
import tech.hackcity.educarts.domain.model.support.ConsultationStep1Response
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.enablePrimaryButtonState
import tech.hackcity.educarts.uitls.hideButtonLoadingState
import tech.hackcity.educarts.uitls.showButtonLoadingState

/**
 *Created by Victor Loveday on 5/17/23
 */
class ConsultationFragment1 : Fragment(R.layout.fragment_consultation_1),
    ConsultationStep1Listener {

    private lateinit var binding: FragmentConsultation1Binding

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentConsultation1Binding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val api = RetrofitInstance(requireContext())
        val sharePreferencesManager = SharePreferencesManager(requireContext())
        val repository = SupportRepository(api, sharePreferencesManager)
        val factory = ConsultationViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[ConsultationViewModel::class.java]
        viewModel.step1listener = this

        viewModel.fetchConsultationTopics()

        binding.scheduleMeetingBtn.setOnClickListener {
            viewModel.consultation = binding.question.text.toString()
            viewModel.detail = binding.detailsET.text.toString().trim()
            viewModel.submitConsultationStep1(requireContext())
        }
    }

    private fun setupConsultationFAQs(data: List<ConsultationResponseData>) {
        val options = mutableListOf<String>()

        for (i in data) {
            options.add(i.option)
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
        showButtonLoadingState(binding.scheduleMeetingBtn, binding.progressBar, "")
    }

    override fun onRequestFailed(message: String) {
        sharedViewModel.updateLoadingScreen(false)
        hideButtonLoadingState(binding.scheduleMeetingBtn, binding.progressBar, resources.getString(R.string.schedule_meeting))
        Toast.makeText(requireContext(), "$message", Toast.LENGTH_SHORT).show()
    }

    override fun onFetchConsultationTopicsRequestSuccessful(response: ConsultationResponse) {
        setupConsultationFAQs(response.data)
        enablePrimaryButtonState(binding.scheduleMeetingBtn)
        sharedViewModel.updateLoadingScreen(false)
    }

    override fun onSubmitConsultationStep1RequestSuccessful(response: ConsultationStep1Response) {
        hideButtonLoadingState(binding.scheduleMeetingBtn, binding.progressBar, resources.getString(R.string.schedule_meeting))
        findNavController().navigate(R.id.action_consultationFragment1_to_consultationFragment2)
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.updateStepIndicator(arrayOf(1, 2, 1))
    }

}