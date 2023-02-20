package tech.hackcity.educarts.ui.fragment.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentGetStartedBinding
import tech.hackcity.educarts.databinding.FragmentOtpBinding

/**
 *Created by Victor Loveday on 2/20/23
 */
class OTPFragment: Fragment(R.layout.fragment_otp) {

    private lateinit var binding: FragmentOtpBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentOtpBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

    }
}