package tech.hackcity.educarts.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentPaymentBinding
import tech.hackcity.educarts.ui.payment.PaymentActivity

/**
 *Created by Victor Loveday on 2/22/23
 */
class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private lateinit var binding: FragmentPaymentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentPaymentBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setupNavigation()
    }

    private fun setupNavigation() {

        binding.sevisFeeTV.setOnClickListener {
            val intent = Intent(requireContext(), PaymentActivity::class.java)
            intent.putExtra("destination", "SEVIS fee")
            startActivity(intent)
        }
    }
}