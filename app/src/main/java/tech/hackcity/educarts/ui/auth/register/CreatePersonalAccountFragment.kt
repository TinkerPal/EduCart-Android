package tech.hackcity.educarts.ui.auth.register

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.hbb20.CountryCodePicker
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.auth.AuthRepository
import tech.hackcity.educarts.data.storage.SessionManager
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.data.storage.UserInfoManager
import tech.hackcity.educarts.databinding.FragmentCreatePersonalAccountBinding
import tech.hackcity.educarts.domain.model.auth.RegisterUserResponse
import tech.hackcity.educarts.ui.alerts.ToastType
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.*
import java.lang.IllegalArgumentException

/**
 *Created by Victor Loveday on 2/20/23
 */
class CreatePersonalAccountFragment : Fragment(R.layout.fragment_create_personal_account),
    CreatePersonalAccountListener {

    private lateinit var binding: FragmentCreatePersonalAccountBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var isTermsAndConditionAgreed = false
    private lateinit var ccpGetNumber: CountryCodePicker
    private var dialCode = ""
    private var isPhoneNumberValid = false
    private var isPasswordStrong = false
    private var userType = "user"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCreatePersonalAccountBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val api = RetrofitInstance(requireContext())
        val sessionManager = SessionManager(requireContext())
        val sharePreferencesManager = SharePreferencesManager(requireContext())
        val userInfoManager = UserInfoManager(requireContext())
        val repository =
            AuthRepository(api, sessionManager, sharePreferencesManager, userInfoManager)
        val factory = CreatePersonalAccountViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[CreatePersonalAccountViewModel::class.java]
        viewModel.createPersonalAccountListener = this

        validatePhoneNumber()

        binding.tabLayout.getTabAt(1)?.view?.setBackgroundResource(R.drawable.disabled_bg)
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> userType = "user"
                    1 -> userType = "user"
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> userType = "user"
                    1 -> userType = "user"
                }
            }

        })


        checkPasswordStrength(
            requireContext(),
            binding.passwordET,
            binding.lowercaseTV,
            binding.uppercaseTV,
            binding.numberTV,
            binding.specialCharTV,
            binding.eightCharTV
        )

        signupFormValidation(
            requireContext(),
            binding.emailET,
            binding.emailTextInputLayout,
            binding.firstNameET,
            binding.lastNameET,
            binding.countryOfResidenceET,
            binding.phoneNumberET,
            binding.passwordET,
            binding.checkboxTC,
            binding.signupBtn
        )

        binding.signupBtn.setOnClickListener {
            viewModel.userType = userType
            viewModel.email = binding.emailET.text.toString().trim()
            viewModel.firstName = binding.firstNameET.text.toString().trim()
            viewModel.lastName = binding.lastNameET.text.toString().trim()
            viewModel.countryOfResidence = resources.getString(R.string.nigeria)
            viewModel.countryCode = dialCode.toInt()
            viewModel.phoneNumber = binding.phoneNumberET.text.toString().trim()
            viewModel.isPhoneNumberValid = isPhoneNumberValid
            viewModel.password = binding.passwordET.text.toString().trim()

            Coroutines.onMainWithScope(viewModel.viewModelScope) {
                viewModel.registerPersonalAccountUser(requireContext())
            }
        }

        binding.signInTextView.setOnClickListener {
            findNavController().navigate(R.id.action_createPersonalAccountFragment_to_loginFragment)
        }

    }

    private fun validatePhoneNumber() {
        ccpGetNumber = binding.ccp
        ccpGetNumber.registerCarrierNumberEditText(binding.phoneNumberET)
        ccpGetNumber.setPhoneNumberValidityChangeListener { isValidNumber ->
            if (isValidNumber) {
                binding.verifyIcon.visibility = View.VISIBLE
                binding.verifyIcon.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.success_green
                    )
                )

                isPhoneNumberValid = true

            } else {
                binding.verifyIcon.visibility = View.INVISIBLE
                isPhoneNumberValid = false

                dialCode = ccpGetNumber.selectedCountryCode
            }
        }

    }

    override fun onRequestStarted() {
        showButtonLoadingState(binding.signupBtn, binding.progressBar, "")
    }

    override fun onRequestFailed(message: String) {
        if (isJsonContainImportantFields(message)) {
            val formattedErrorMessage = extractErrorMessagesFromRegisterErrorBody(message)
            context?.toast(description = formattedErrorMessage, toastType = ToastType.ERROR)
        } else {
            context?.toast(description = message, toastType = ToastType.ERROR)
        }

        hideButtonLoadingState(
            binding.signupBtn,
            binding.progressBar,
            resources.getString(R.string.sign_up)
        )
    }

    override fun onRequestSuccessful(response: RegisterUserResponse) {
        val email = response.data.email
        hideButtonLoadingState(
            binding.signupBtn,
            binding.progressBar,
            resources.getString(R.string.sign_up)
        )

        try {
            val action =
                CreatePersonalAccountFragmentDirections.actionCreatePersonalAccountFragmentToOTPFragment(
                    "login",
                    resources.getString(R.string.verification),
                    resources.getString(R.string.to_verify_your_account_we_will_send_an_otp, email),
                    3
                )
            findNavController().navigate(action)

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
        sharedViewModel.updateHorizontalStepViewPosition(2)
    }

}