package tech.hackcity.educarts.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentLoginBinding
import tech.hackcity.educarts.ui.main.MainActivity
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

/**
 *Created by Victor Loveday on 2/20/23
 */
class LoginFragment: Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentLoginBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()

        //navigate to create personal account fragment
        binding.signUpText.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_createPersonalAccountFragment)
        }

        //navigate to forgot password fragment
        binding.forgotPasswordTxt.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        //navigate to main app
        binding.signInBtn.setOnClickListener {
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }

    }

    private fun setupToolbar() {
        sharedViewModel.setToolbarVisibility(false)
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

}