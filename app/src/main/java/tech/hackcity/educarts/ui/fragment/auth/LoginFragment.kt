package tech.hackcity.educarts.ui.fragment.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentGetStartedBinding
import tech.hackcity.educarts.databinding.FragmentLoginBinding
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

    }

    private fun setupToolbar() {
        sharedViewModel.setToolbarVisibility(false)
    }

}