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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentForgotPasswordBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()

        //navigate to otp fragment
        binding.sendEmailBtn.setOnClickListener {
            findNavController().navigate(R.id.action_forgotPasswordFragment_to_OTPFragment)
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