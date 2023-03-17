package tech.hackcity.educarts.ui.fragment.payment.sevisfee

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentOrderSummaryBinding
import tech.hackcity.educarts.ui.CheckoutActivity
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

/**
 *Created by Victor Loveday on 3/15/23
 */
class OrderSummaryFragment: Fragment(R.layout.fragment_order_summary) {

    private lateinit var binding: FragmentOrderSummaryBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentOrderSummaryBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.proceedToPaymentBtn.setOnClickListener {
            val intent = Intent(requireContext(), CheckoutActivity::class.java)
            intent.putExtra("service", "SEVIS Fee")
            intent.putExtra("amount", "269,500")
            intent.putExtra("currency", "NGN")
            startActivity(intent)
        }
    }

}