package tech.hackcity.educarts.ui.payment.applicationfee

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentApplicationFeePaymentOnlyBinding

/**
 *Created by Victor Loveday on 6/2/23
 */
class ApplicationFeePaymentOnly : Fragment(R.layout.fragment_application_fee_payment_only) {

    private lateinit var binding: FragmentApplicationFeePaymentOnlyBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentApplicationFeePaymentOnlyBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.nextBtn.setOnClickListener {
            val action =
                ApplicationFeePaymentOnlyDirections
                    .actionApplicationFeePaymentOnlyToUniversityPortalLoginDetailsFragment(
                        resources.getString(R.string.application_fee_payment_only),
                        resources.getString(R.string.application_fee)
                    )
            findNavController().navigate(action)
        }
    }
}