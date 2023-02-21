package tech.hackcity.educarts.ui.fragment.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentCreatePersonalAccountBinding
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

/**
 *Created by Victor Loveday on 2/20/23
 */
class CreatePersonalAccountFragment : Fragment(R.layout.fragment_create_personal_account) {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var isTermsAndConditionAgreed = false

    private lateinit var binding: FragmentCreatePersonalAccountBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCreatePersonalAccountBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()

        binding.checkboxTC.setOnClickListener {
            if (isTermsAndConditionAgreed) {
                isTermsAndConditionAgreed = false
                disableButtons()
            }else {
                isTermsAndConditionAgreed = true
                enableButtons()
            }
        }

        //navigate to OTP
        binding.signupBtn.setOnClickListener {
            findNavController().navigate(R.id.action_createPersonalAccountFragment_to_OTPFragment)
        }

        //navigate to login fragment
        binding.signInText.setOnClickListener {
            findNavController().navigate(R.id.action_createPersonalAccountFragment_to_loginFragment)
        }

    }

    private fun setupToolbar() {
        sharedViewModel.setToolbarVisibility(false)
    }

    private fun enableButtons() {
        binding.signupBtn.isEnabled = true
        binding.signupBtn.setBackgroundResource(R.drawable.primary_button)
    }
    private fun disableButtons() {
        binding.signupBtn.isEnabled = false
        binding.signupBtn.setBackgroundResource(R.drawable.disabled_button)
    }
}