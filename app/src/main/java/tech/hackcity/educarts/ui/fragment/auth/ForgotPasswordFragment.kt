package tech.hackcity.educarts.ui.fragment.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentForgotPasswordBinding
import tech.hackcity.educarts.databinding.FragmentGetStartedBinding
import tech.hackcity.educarts.databinding.FragmentLoginBinding

/**
 *Created by Victor Loveday on 2/20/23
 */
class ForgotPasswordFragment: Fragment(R.layout.fragment_forgot_password) {

    private lateinit var binding: FragmentForgotPasswordBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentForgotPasswordBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

    }
}