package tech.hackcity.educarts.ui.auth.forgotPassword

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.auth.AuthRepository
import tech.hackcity.educarts.data.storage.SessionManager
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.databinding.FragmentCreateNewPasswordBinding
import tech.hackcity.educarts.domain.model.auth.CreateNewPasswordResponse
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.compareTwoPasswordFields
import tech.hackcity.educarts.uitls.hideButtonLoadingState
import tech.hackcity.educarts.uitls.showButtonLoadingState
import tech.hackcity.educarts.uitls.toast

/**
 *Created by Victor Loveday on 2/20/23
 */
class CreateNewPasswordFragment : Fragment(R.layout.fragment_create_new_password),
    CreateNewPasswordListener {

    private lateinit var binding: FragmentCreateNewPasswordBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCreateNewPasswordBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val api = RetrofitInstance(requireContext())
        val sessionManager = SessionManager(requireContext())
        val sharePreferencesManager = SharePreferencesManager(requireContext())
        val repository = AuthRepository(api, sessionManager, sharePreferencesManager)
        val factory = CreateNewPasswordViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[CreateNewPasswordViewModel::class.java]
        viewModel.createNewPasswordListener = this


        compareTwoPasswordFields(
            requireContext(), binding.passwordET, binding.confirmPasswordET,
            binding.passwordLayout, binding.confirmPasswordLayout, binding.resetPasswordBtn
        )

        binding.resetPasswordBtn.setOnClickListener {
            viewModel.id = sharePreferencesManager.fetchUserId().toString()
            viewModel.password = binding.confirmPasswordET.text.toString().trim()

            viewModel.onResetPasswordButtonClicked(requireContext())
        }
    }

    override fun onRequestStarted() {
        showButtonLoadingState(binding.resetPasswordBtn, binding.progressBar, "")
    }

    override fun onRequestFailed(message: String) {
        context?.toast(message)
        hideButtonLoadingState(
            binding.resetPasswordBtn,
            binding.progressBar,
            resources.getString(R.string.reset_password)
        )
    }

    override fun onRequestSuccessful(response: CreateNewPasswordResponse) {
        hideButtonLoadingState(
            binding.resetPasswordBtn,
            binding.progressBar,
            resources.getString(R.string.reset_password)
        )

        findNavController().navigate(R.id.action_createNewPasswordFragment_to_loginFragment)
    }


}