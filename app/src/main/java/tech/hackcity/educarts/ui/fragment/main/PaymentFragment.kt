package tech.hackcity.educarts.ui.fragment.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentAccountBinding
import tech.hackcity.educarts.databinding.FragmentPaymentBinding

/**
 *Created by Victor Loveday on 2/22/23
 */
class PaymentFragment: Fragment(R.layout.fragment_payment) {

    private lateinit var binding: FragmentPaymentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentPaymentBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
    }
}