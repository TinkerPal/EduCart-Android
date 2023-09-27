package tech.hackcity.educarts.ui.payment.applicationfee

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentUniversityPortalLoginDetailsBinding
import tech.hackcity.educarts.ui.payment.ordersummary.OrderSummaryActivity
import tech.hackcity.educarts.ui.payment.PaymentActivity

/**
 *Created by Victor Loveday on 6/1/23
 */
class UniversityPortalLoginDetailsFragment :
    Fragment(R.layout.fragment_university_portal_login_details) {

    private lateinit var binding: FragmentUniversityPortalLoginDetailsBinding

    private val args: UniversityPortalLoginDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentUniversityPortalLoginDetailsBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        (activity as PaymentActivity).supportActionBar?.title = args.pageTitle

        binding.service.text = args.service

        binding.proceedBtn.setOnClickListener {
            val intent = Intent(requireContext(), OrderSummaryActivity::class.java)
            intent.putExtra("title", args.pageTitle)
            intent.putExtra("service", args.service)
            startActivity(intent)
        }
    }
}