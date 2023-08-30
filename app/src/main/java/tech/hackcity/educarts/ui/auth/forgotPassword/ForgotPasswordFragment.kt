package tech.hackcity.educarts.ui.auth.forgotPassword

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.auth.AuthRepository
import tech.hackcity.educarts.data.storage.SessionManager
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.data.storage.UserInfoManager
import tech.hackcity.educarts.databinding.FragmentForgotPasswordBinding
import tech.hackcity.educarts.domain.model.auth.ForgotPasswordResponse
import tech.hackcity.educarts.uitls.hideButtonLoadingState
import tech.hackcity.educarts.uitls.showButtonLoadingState
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.toast

/**
 *Created by Victor Loveday on 2/20/23
 */
class ForgotPasswordFragment : Fragment(R.layout.fragment_forgot_password), ForgotPasswordListener {

    private lateinit var binding: FragmentForgotPasswordBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var isEmailTextInput = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentForgotPasswordBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val api = RetrofitInstance(requireContext())
        val sessionManager = SessionManager(requireContext())
        val sharePreferencesManager = SharePreferencesManager(requireContext())
        val userInfoManager = UserInfoManager(requireContext())
        val repository = AuthRepository(api, sessionManager, sharePreferencesManager, userInfoManager)
        val factory = ForgotPasswordViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[ForgotPasswordViewModel::class.java]
        viewModel.forgotPasswordListener = this

        binding.sendEmailBtn.setOnClickListener {
            viewModel.email = binding.emailET.text.toString().trim()

            Coroutines.onMainWithScope(viewModel.viewModelScope) {
                viewModel.forgotPassword(requireContext())
            }
        }

        binding.swapTextInputTxt.setOnClickListener {
            swapTextInput()
        }
    }

    private fun swapTextInput() {
        if (isEmailTextInput) {
            isEmailTextInput = false
            binding.emailTextInputLayout.visibility = View.GONE
            binding.phoneTextInputLayout.visibility = View.VISIBLE

        } else {
            isEmailTextInput = true
            binding.emailTextInputLayout.visibility = View.VISIBLE
            binding.phoneTextInputLayout.visibility = View.GONE
        }
    }

    override fun onRequestStarted() {
        showButtonLoadingState(binding.sendEmailBtn, binding.progressBar, "")
    }

    override fun onRequestFailed(message: String) {
        context?.toast(message)
        hideButtonLoadingState(
            binding.sendEmailBtn,
            binding.progressBar,
            resources.getString(R.string.send_otp)
        )
    }

    override fun onRequestSuccessful(response: ForgotPasswordResponse) {
        val email = response.data.email
       try {
           val action =
               ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToForgotPasswordInstructionsFragment(
                   email
               )
           findNavController().navigate(action)
       }catch (e: IllegalArgumentException) {
           e.printStackTrace()
       }
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.setToolBarColor(ContextCompat.getColor(requireContext(), R.color.background_001))
        sharedViewModel.updateHorizontalStepViewPosition(1)
        sharedViewModel.updateHorizontalStepViewVisibility(true)
    }
}