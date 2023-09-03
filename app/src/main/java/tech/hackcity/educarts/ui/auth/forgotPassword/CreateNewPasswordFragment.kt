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
import tech.hackcity.educarts.databinding.FragmentCreateNewPasswordBinding
import tech.hackcity.educarts.domain.model.auth.CreateNewPasswordResponse
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.compareTwoPasswordFields
import tech.hackcity.educarts.uitls.hideButtonLoadingState
import tech.hackcity.educarts.uitls.showButtonLoadingState
import tech.hackcity.educarts.uitls.toast

/**
 *Created by Victor Loveday on 2/20/23
 */
class CreateNewPasswordFragment : Fragment(R.layout.fragment_create_new_password),
    ResetPasswordListener {

    private lateinit var binding: FragmentCreateNewPasswordBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCreateNewPasswordBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val api = RetrofitInstance(requireContext())
        val sessionManager = SessionManager(requireContext())
        val sharePreferencesManager = SharePreferencesManager(requireContext())
        val userInfoManager = UserInfoManager(requireContext())
        val repository =
            AuthRepository(api, sessionManager, sharePreferencesManager, userInfoManager)
        val factory = ResetPasswordViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[ResetPasswordViewModel::class.java]
        viewModel.resetPasswordListener = this


        compareTwoPasswordFields(
            requireContext(), binding.passwordET, binding.confirmPasswordET,
            binding.passwordLayout, binding.confirmPasswordLayout, binding.changePasswordBtn
        )

        binding.changePasswordBtn.setOnClickListener {
            viewModel.id = sharePreferencesManager.fetchUserId().toString()
            viewModel.password = binding.confirmPasswordET.text.toString().trim()

            Coroutines.onMainWithScope(viewModel.viewModelScope) {
                viewModel.resetPassword(requireContext())
            }
        }
    }

    override fun onRequestStarted() {
        showButtonLoadingState(binding.changePasswordBtn, binding.progressBar, "")
    }

    override fun onRequestFailed(message: String) {
        context?.toast(message)
        hideButtonLoadingState(
            binding.changePasswordBtn,
            binding.progressBar,
            resources.getString(R.string.reset_password)
        )
    }

    override fun onRequestSuccessful(response: CreateNewPasswordResponse) {
        hideButtonLoadingState(
            binding.changePasswordBtn,
            binding.progressBar,
            resources.getString(R.string.reset_password)
        )

        findNavController().navigate(R.id.action_createNewPasswordFragment_to_loginFragment)
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.setToolBarColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.background_001
            )
        )
        sharedViewModel.updateHorizontalStepViewPosition(3)
    }
}