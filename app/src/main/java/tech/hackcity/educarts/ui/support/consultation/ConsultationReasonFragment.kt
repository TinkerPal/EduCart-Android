package tech.hackcity.educarts.ui.support.consultation

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.support.SupportRepository
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.databinding.FragmentConsultationReasonBinding
import tech.hackcity.educarts.domain.model.support.ConsultationStep1Response
import tech.hackcity.educarts.domain.model.support.MultipleChoiceResponse
import tech.hackcity.educarts.domain.model.support.MultipleChoiceResponseData
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.enablePrimaryButtonState
import tech.hackcity.educarts.uitls.hideButtonLoadingState
import tech.hackcity.educarts.uitls.showButtonLoadingState

/**
 *Created by Victor Loveday on 5/17/23
 */
class ConsultationReasonFragment : Fragment(R.layout.fragment_consultation_reason),
    ConsultationReasonListener {

    private lateinit var binding: FragmentConsultationReasonBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentConsultationReasonBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val api = RetrofitInstance(requireContext())
        val sharePreferencesManager = SharePreferencesManager(requireContext())
        val repository = SupportRepository(api, sharePreferencesManager)
        val factory = ConsultationViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[ConsultationViewModel::class.java]
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

    override fun onRequestFailed(message: String) {
        sharedViewModel.updateLoadingScreen(false)
        hideButtonLoadingState(binding.nextBtn, binding.progressBar, resources.getString(R.string.schedule_meeting))
        Toast.makeText(requireContext(), "$message", Toast.LENGTH_SHORT).show()
        sharedViewModel.updateLoadingScreen(false)
    }

    override fun onFetchConsultationTopicsRequestSuccessful(response: MultipleChoiceResponse) {
        setupConsultationCategory(response.data)
        enablePrimaryButtonState(binding.nextBtn)
        sharedViewModel.updateLoadingScreen(false)
    }

    override fun onSubmitConsultationStep1RequestSuccessful(response: ConsultationStep1Response) {
        hideButtonLoadingState(binding.nextBtn, binding.progressBar, resources.getString(R.string.schedule_meeting))
        findNavController().navigate(R.id.action_consultationReasonFragment_to_chooseAConsultantFragment)
        sharedViewModel.updateLoadingScreen(false)
    }

    override fun onResume() {
        super.onResume()
    }

}