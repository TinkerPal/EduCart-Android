package tech.hackcity.educarts.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentGetStartedBinding
import tech.hackcity.educarts.databinding.FragmentOtpBinding

/**
 *Created by Victor Loveday on 2/20/23
 */
class OTPFragment: Fragment(R.layout.fragment_otp) {

    private lateinit var binding: FragmentOtpBinding

    private val args: OTPFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentOtpBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setupNavigation()
    }

    private fun setupNavigation() {
        binding.verifyOTPBtn.setOnClickListener {
            when(args.destination) {
                "Login" -> {
                    findNavController().navigate(R.id.action_OTPFragment_to_loginFragment)
                }
                "Create Personal Account" -> {
                    findNavController().navigate(R.id.action_OTPFragment_to_createNewPinFragment)
                }
            }
        }
    }
}