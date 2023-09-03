package tech.hackcity.educarts.ui.payment.sevisfee.sevispayment

import android.content.Intent
import android.os.Bundle
import android.view.View
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
import tech.hackcity.educarts.databinding.FragmentSevisFeeStep3Binding
import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep3Response
import tech.hackcity.educarts.ui.payment.OrderSummaryActivity
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.disablePrimaryButtonState
import tech.hackcity.educarts.uitls.enablePrimaryButtonState
import tech.hackcity.educarts.uitls.hideButtonLoadingState
import tech.hackcity.educarts.uitls.showButtonLoadingState
import tech.hackcity.educarts.uitls.toast

/**
 *Created by Victor Loveday on 8/3/23
 */
class SEVISFeeStep3Fragment : Fragment(R.layout.fragment_sevis_fee_step_3), SEIVSFeeStep3Listener {

    private lateinit var binding: FragmentSevisFeeStep3Binding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var viewModel: SEVISFeeViewModel
    private val args: SEVISFeeStep3FragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSevisFeeStep3Binding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val api = RetrofitInstance(requireContext())
        val sharePreferencesManager = SharePreferencesManager(requireContext())
        val repository = SEVISFeeRepository(api, sharePreferencesManager)
        val factory = SEVISFeeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[SEVISFeeViewModel::class.java]
        viewModel.listener3 = this


        binding.textGuide1.text = resources.getString(
            R.string.please_fill_the_following_form_as_it_is_in_your,
            args.formType
        )

        binding.nextBtn.setOnClickListener {
            viewModel.streetAddress1 = binding.streetAddress1ET.text.toString().trim()
            viewModel.streetAddress2 = binding.streetAddress2ET.text.toString().trim()
            viewModel.country = "Nigeria"
            viewModel.state = "Kano"
            viewModel.city = "Kano"

            Coroutines.onMainWithScope(viewModel.viewModelScope) {
                viewModel.submitSevisFeeStep3(requireContext())
            }
        }
    }

    override fun onRequestStarted() {
        sharedViewModel.updateLoadingScreen(true)
        showButtonLoadingState(binding.nextBtn, binding.progressBar, "")
        disablePrimaryButtonState(binding.nextBtn)
    }

    override fun onRequestFailed(message: String) {
        context?.toast("$message")
        hideButtonLoadingState(
            binding.nextBtn,
            binding.progressBar,
            resources.getString(R.string.next)
        )
        enablePrimaryButtonState(binding.nextBtn)
        sharedViewModel.updateLoadingScreen(false)
    }

    override fun onRequestSuccessful(response: SEVISFeeStep3Response) {
        hideButtonLoadingState(
            binding.nextBtn,
            binding.progressBar,
            resources.getString(R.string.next)
        )
        enablePrimaryButtonState(binding.nextBtn)
        sharedViewModel.updateLoadingScreen(false)

        val intent = Intent(requireContext(), OrderSummaryActivity::class.java)
        intent.putExtra("service", resources.getString(R.string.sevis_fee))
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.updateHorizontalStepViewPosition(3)
    }


}