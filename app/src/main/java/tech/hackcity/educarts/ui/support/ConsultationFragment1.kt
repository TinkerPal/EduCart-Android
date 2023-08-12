package tech.hackcity.educarts.ui.support

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
import tech.hackcity.educarts.data.repositories.support.ConsultationRepository
import tech.hackcity.educarts.data.storage.SessionManager
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.databinding.FragmentConsultation1Binding
import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.domain.model.support.ConsultationResponse
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.Constants
import tech.hackcity.educarts.uitls.hideButtonLoadingState
import tech.hackcity.educarts.uitls.showButtonLoadingState

/**
 *Created by Victor Loveday on 5/17/23
 */
class ConsultationFragment1 : Fragment(R.layout.fragment_consultation_1), ConsultationStep1Listener {

    private lateinit var binding: FragmentConsultation1Binding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var question = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentConsultation1Binding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val api = RetrofitInstance(requireContext())
        val sharePreferencesManager = SharePreferencesManager(requireContext())
        val repository = ConsultationRepository(api, sharePreferencesManager)
        val factory = ConsultationViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[ConsultationViewModel::class.java]
        viewModel.listener = this

        viewModel.fetchConsultationTopics()


//        setupConsultationFAQs()

        binding.nextBtn.setOnClickListener {
            findNavController().navigate(R.id.action_consultationFragment1_to_consultationFragment2)
        }
    }

    private fun setupConsultationFAQs() {
        val arrayAdapter1 =
            ArrayAdapter(
                requireContext(),
                R.layout.security_questions_item,
                Constants.dummyConsultationFAQs
            )
        binding.question.setAdapter(arrayAdapter1)
        question = binding.question.text.toString()
    }

    override fun onFetchConsultationTopicsRequestStarted() {
        showButtonLoadingState(binding.nextBtn, binding.progressBar, "")
    }

    override fun onSubmitConsultationStep1RequestStarted() {
    }

    override fun onRequestFailed(message: List<ErrorMessage>) {
        Toast.makeText(requireContext(), "$message", Toast.LENGTH_LONG).show()
        hideButtonLoadingState(binding.nextBtn, binding.progressBar, "Next")
    }

    override fun onFetchConsultationTopicsRequestSuccessful(response: ConsultationResponse) {
        Toast.makeText(requireContext(), "${response.data}", Toast.LENGTH_SHORT).show()
        hideButtonLoadingState(binding.nextBtn, binding.progressBar, "Next")
    }

    override fun onSubmitConsultationStep1RequestSuccessful() {
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.updateStepIndicator(arrayOf(1, 2, 1))
    }

}