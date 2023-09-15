package tech.hackcity.educarts.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.ErrorCodes.USER_NOT_VERIFIED
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.auth.AuthRepository
import tech.hackcity.educarts.data.storage.SessionManager
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.data.storage.UserInfoManager
import tech.hackcity.educarts.databinding.FragmentLoginBinding
import tech.hackcity.educarts.domain.model.auth.LoginResponse
import tech.hackcity.educarts.ui.alerts.ToastType
import tech.hackcity.educarts.ui.main.MainActivity
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.extractErrorMessagesFromErrorBody
import tech.hackcity.educarts.uitls.hideButtonLoadingState
import tech.hackcity.educarts.uitls.showButtonLoadingState
import tech.hackcity.educarts.uitls.toast
import java.lang.IllegalArgumentException

/**
 *Created by Victor Loveday on 2/20/23
 */
class LoginFragment : Fragment(R.layout.fragment_login), LoginListener {

    private lateinit var binding: FragmentLoginBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentLoginBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val api = RetrofitInstance(requireContext())
        val sessionManager = SessionManager(requireContext())
        val sharePreferencesManager = SharePreferencesManager(requireContext())
        val userInfoManager = UserInfoManager(requireContext())
        val repository =
            AuthRepository(api, sessionManager, sharePreferencesManager, userInfoManager)
        val factory = LoginViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
        viewModel.loginListener = this

        binding.signInBtn.setOnClickListener {
            viewModel.email = binding.emailET.text.toString().trim()
            viewModel.password = binding.passwordET.text.toString().trim()

            lifecycleScope.launchWhenCreated {
                viewModel.loginUser(requireContext())
            }

        }

        binding.signupTextView.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_getStartedFragment)
        }

        binding.forgotPasswordTextView.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

    }

    override fun onRequestStarted() {
        showButtonLoadingState(binding.signInBtn, binding.progressBar, "")
    }

    override fun onRequestFailed(errorMessage: String) {
        Log.d("LoginError", errorMessage)
        context?.toast(description = errorMessage, toastType = ToastType.ERROR)

        val errorMessages = extractErrorMessagesFromErrorBody(errorMessage)
        if (errorMessages.isNotEmpty()) {
            for ((code, message) in errorMessages) {
                if (code == USER_NOT_VERIFIED) {

                    try {
                        val action =
                            LoginFragmentDirections.actionLoginFragmentToOTPFragment(
                                "login",
                                resources.getString(R.string.verification),
                                message,
                                3
                            )
                        findNavController().navigate(action)

                    } catch (e: IllegalArgumentException) {
                        e.printStackTrace()
                    }
                }
            }
        }

        hideButtonLoadingState(
            binding.signInBtn,
            binding.progressBar,
            resources.getString(R.string.sign_in)
        )
    }

    override fun onRequestSuccessful(response: LoginResponse) {
        try {
            startActivity(Intent(requireContext(), MainActivity::class.java))
            activity?.finish()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.setToolBarColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.background_001
            )
        )
        sharedViewModel.updateHorizontalStepViewPosition(0)
    }

}