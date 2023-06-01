package tech.hackcity.educarts.ui.auth.verifyOTP

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.auth.AuthRepository
import tech.hackcity.educarts.data.storage.SessionManager
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.databinding.FragmentOtpBinding
import tech.hackcity.educarts.domain.model.auth.VerifyOTPResponse
import tech.hackcity.educarts.uitls.hideButtonLoadingState
import tech.hackcity.educarts.uitls.showButtonLoadingState
import tech.hackcity.educarts.uitls.toast

/**
 *Created by Victor Loveday on 2/20/23
 */
class OTPFragment : Fragment(R.layout.fragment_otp), VerifyOTPListener {

    private lateinit var binding: FragmentOtpBinding

    private val args: OTPFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentOtpBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

//        binding.message.text = args.message

        val api = RetrofitInstance(requireContext())
        val sessionManager = SessionManager(requireContext())
        val sharePreferencesManager = SharePreferencesManager(requireContext())
        val repository = AuthRepository(api, sessionManager, sharePreferencesManager)
        val factory = VerifyOTPViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[VerifyOTPViewModel::class.java]
        viewModel.verifyOTPListener = this

        binding.verifyBtn.setOnClickListener {
            viewModel.id = sharePreferencesManager.fetchUserId().toString()
            viewModel.otp = binding.pinView.value

            viewModel.onVerifyButtonClickedListener(requireContext())
        }
    }

    private fun navigateToDestination() {
        when (args.destination) {
            "login" -> {
                findNavController().navigate(R.id.action_OTPFragment_to_loginFragment)
            }
            "reset password" -> {
                findNavController().navigate(R.id.action_OTPFragment_to_createNewPasswordFragment)
            }
            "create pin" -> {
                findNavController().navigate(R.id.action_OTPFragment_to_createNewPinFragment)
            }
        }
    }

    override fun onRequestStarted() {
        showButtonLoadingState(binding.verifyBtn, binding.progressBar, "")
    }

    override fun onRequestFailed(message: String) {
        context?.toast(message)
        hideButtonLoadingState(
            binding.verifyBtn,
            binding.progressBar,
            resources.getString(R.string.verify)
        )
    }

    override fun onRequestSuccessful(response: VerifyOTPResponse) {
        context?.toast(response.message)
        hideButtonLoadingState(
            binding.verifyBtn,
            binding.progressBar,
            resources.getString(R.string.verify)
        )
        navigateToDestination()
    }
}