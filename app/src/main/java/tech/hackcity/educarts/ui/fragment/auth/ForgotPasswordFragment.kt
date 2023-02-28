package tech.hackcity.educarts.ui.fragment.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentForgotPasswordBinding
import tech.hackcity.educarts.databinding.FragmentGetStartedBinding
import tech.hackcity.educarts.databinding.FragmentLoginBinding
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

/**
 *Created by Victor Loveday on 2/20/23
 */
class ForgotPasswordFragment: Fragment(R.layout.fragment_forgot_password) {

    private lateinit var binding: FragmentForgotPasswordBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var isEmailTextInput = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentForgotPasswordBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()

        //navigate to otp fragment
        binding.sendEmailBtn.setOnClickListener {
            val action = ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToOTPFragment("Login")
            findNavController().navigate(action)
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
        }else {
            isEmailTextInput = true
            binding.emailTextInputLayout.visibility = View.VISIBLE
            binding.phoneTextInputLayout.visibility = View.GONE
        }
    }

    private fun setupToolbar() {
        sharedViewModel.setToolbarVisibility(true)
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }
}