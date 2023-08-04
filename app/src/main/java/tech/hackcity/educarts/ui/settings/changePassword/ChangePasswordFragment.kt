package tech.hackcity.educarts.ui.settings.changePassword

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.settings.SettingsRepository
import tech.hackcity.educarts.data.storage.SessionManager
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.data.storage.UserInfoManager
import tech.hackcity.educarts.databinding.FragmentChangePasswordBinding
import tech.hackcity.educarts.domain.model.settings.ChangePasswordResponse
import tech.hackcity.educarts.uitls.hideButtonLoadingState
import tech.hackcity.educarts.uitls.showButtonLoadingState
import tech.hackcity.educarts.uitls.toast

/**
 *Created by Victor Loveday on 2/28/23
 */
class ChangePasswordFragment : Fragment(R.layout.fragment_change_password), ChangePasswordListener {

    private lateinit var binding: FragmentChangePasswordBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentChangePasswordBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val api = RetrofitInstance(requireContext())
        val sessionManager = SessionManager(requireContext())
        val sharePreferencesManager = SharePreferencesManager(requireContext())
        val userInfoManager = UserInfoManager(requireContext())
        val repository = SettingsRepository(api, sessionManager, sharePreferencesManager, userInfoManager)
        val factory = ChangePasswordViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[ChangePasswordViewModel::class.java]
        viewModel.changePasswordListener = this

        binding.updateBtn.setOnClickListener {
            viewModel.oldPassword = binding.oldPasswordET.text.toString().trim()
            viewModel.newPassword = binding.confirmNewPasswordET.text.toString().trim()

            viewModel.onUpdateButtonClicked(requireContext())
        }

    }

    override fun onRequestStarted() {
        showButtonLoadingState(binding.updateBtn, binding.progressBar, "")
    }

    override fun onRequestFailed(message: String) {
        context?.toast(message)
        hideButtonLoadingState(
            binding.updateBtn,
            binding.progressBar,
            resources.getString(R.string.update)
        )
    }

    override fun onRequestSuccessful(response: ChangePasswordResponse) {
        context?.toast(response.message)
        activity?.finish()
    }

}