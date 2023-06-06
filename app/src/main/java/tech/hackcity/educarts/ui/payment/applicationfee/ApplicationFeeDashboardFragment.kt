package tech.hackcity.educarts.ui.payment.applicationfee

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentApplicationFeeDashboardBinding

/**
 *Created by Victor Loveday on 5/31/23
 */
class ApplicationFeeDashboardFragment : Fragment(R.layout.fragment_application_fee_dashboard) {

    private lateinit var binding: FragmentApplicationFeeDashboardBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentApplicationFeeDashboardBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.applyToSchool.setOnClickListener {
            findNavController().navigate(R.id.action_applicationFeeFragment_to_selectSchoolOrProgramFragment)
        }
        binding.applicationFeePaymentOnly.setOnClickListener {
            findNavController().navigate(R.id.action_applicationFeeFragment_to_applicationFeePaymentOnly)
        }
        binding.applicationReview.setOnClickListener {
            findNavController().navigate(R.id.action_applicationFeeFragment_to_applicationReviewFragment)
        }

    }
}