package tech.hackcity.educarts.ui.payment.sevisfee.seviscoupon

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentHaveSevisPaymentCouponBinding
import tech.hackcity.educarts.ui.payment.OrderSummaryActivity
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

/**
 *Created by Victor Loveday on 3/18/23
 */
class SEVISCouponFragment : Fragment(R.layout.fragment_have_sevis_payment_coupon) {

    private lateinit var binding: FragmentHaveSevisPaymentCouponBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val args: SEVISCouponFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHaveSevisPaymentCouponBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.updateStepIndicator(arrayOf(1, 1))

        binding.nextBtn.setOnClickListener {
            val action = SEVISCouponFragmentDirections.actionSevisCouponFragmentToSevisFeeStep1Fragment(
                args.formType,
                resources.getString(R.string.i_have_generated_sevis_payment_coupon)
            )
            findNavController().navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()
    }

}