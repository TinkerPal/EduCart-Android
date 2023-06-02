package tech.hackcity.educarts.ui.payment.sevisfee

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentHaveSevisPaymentCouponBinding
import tech.hackcity.educarts.ui.payment.OrderSummaryActivity
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

/**
 *Created by Victor Loveday on 3/18/23
 */
class HaveSEVISPaymentCouponFragment : Fragment(R.layout.fragment_have_sevis_payment_coupon) {

    private lateinit var binding: FragmentHaveSevisPaymentCouponBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHaveSevisPaymentCouponBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.updateStepIndicator(arrayOf(1, 1))

        binding.nextBtn.setOnClickListener {
            val intent = Intent(requireContext(), OrderSummaryActivity::class.java)
            intent.putExtra("title", resources.getString(R.string.sevis_fee))
            intent.putExtra("service", resources.getString(R.string.sevis_fee))
            startActivity(intent)
            sharedViewModel.updateStepIndicator(arrayOf(0, 3))
        }
    }
}