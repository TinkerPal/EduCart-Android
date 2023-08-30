package tech.hackcity.educarts.ui.payment.sevisfee.sevispayment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.payment.SEVISFeeRepository
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.databinding.FragmentSevisFeeStep2Binding
import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep2Response
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.disablePrimaryButtonState
import tech.hackcity.educarts.uitls.enablePrimaryButtonState
import tech.hackcity.educarts.uitls.hideButtonLoadingState
import tech.hackcity.educarts.uitls.showButtonLoadingState

/**
 *Created by Victor Loveday on 8/3/23
 */
class SEVISFeeStep2Fragment : Fragment(R.layout.fragment_sevis_fee_step_2), SEIVSFeeStep2Listener {

    private lateinit var binding: FragmentSevisFeeStep2Binding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var viewModel: SEVISFeeViewModel
    private val args: SEVISFeeStep2FragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSevisFeeStep2Binding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val api = RetrofitInstance(requireContext())
        val sharePreferencesManager = SharePreferencesManager(requireContext())
        val repository = SEVISFeeRepository(api, sharePreferencesManager)
        val factory = SEVISFeeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[SEVISFeeViewModel::class.java]
        viewModel.listener2 = this

        binding.textGuide1.text = resources.getString(
            R.string.please_fill_the_following_form_as_it_is_in_your,
            args.formType
        )

        if (args.formType == resources.getString(R.string.ds_2019)) {
            binding.categoryTextInputLayout.visibility = View.VISIBLE
        }

        binding.nextBtn.setOnClickListener {
            viewModel.formType = args.formType
            viewModel.category = "au pair ($35)"
            viewModel.email = binding.emailET.text.toString().trim()
//            viewModel.phoneNumber = binding.phoneNumberET.toString().trim()
            viewModel.phoneNumber = "09066965746"
            viewModel.countryOfCitizenship = "Nigeria"
            viewModel.countryOfBirth = "Nigeria"

            Coroutines.onMainWithScope(viewModel.viewModelScope) {
                viewModel.submitSevisFeeStep2(requireContext())
            }

        }

    }

    override fun onRequestStarted() {
        sharedViewModel.updateLoadingScreen(true)
        showButtonLoadingState(binding.nextBtn, binding.progressBar, "")
        disablePrimaryButtonState(binding.nextBtn)
    }

    override fun onRequestFailed(message: List<ErrorMessage>) {
        Toast.makeText(requireContext(), "$message", Toast.LENGTH_SHORT).show()
        hideButtonLoadingState(
            binding.nextBtn,
            binding.progressBar,
            resources.getString(R.string.next)
        )
        enablePrimaryButtonState(binding.nextBtn)
        sharedViewModel.updateLoadingScreen(false)
    }

    override fun onRequestSuccessful(response: SEVISFeeStep2Response) {
        hideButtonLoadingState(
            binding.nextBtn,
            binding.progressBar,
            resources.getString(R.string.next)
        )
        enablePrimaryButtonState(binding.nextBtn)
        sharedViewModel.updateLoadingScreen(false)

        val action =
            SEVISFeeStep2FragmentDirections.actionSevisFeeStep2FragmentToSevisFeeStep3Fragment(args.formType)
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.updateHorizontalStepViewPosition(2)
        sharedViewModel.updateHorizontalStepViewVisibility(true)
    }
}