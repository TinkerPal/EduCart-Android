package tech.hackcity.educarts.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentPaymentBinding
import tech.hackcity.educarts.ui.browser.BrowserActivity
import tech.hackcity.educarts.ui.payment.PaymentActivity
import tech.hackcity.educarts.ui.payment.TrackOrderActivity
import tech.hackcity.educarts.uitls.shortenString
import tech.hackcity.educarts.uitls.spannableTextWithForegroundColour

/**
 *Created by Victor Loveday on 2/22/23
 */
class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private lateinit var binding: FragmentPaymentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentPaymentBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setupNavigation()

        spannableTextWithForegroundColour(
            resources.getString(R.string.can_t_find_a_payment_you_want_to_make_t_contact_support),
            41,
            56,
            ContextCompat.getColor(requireContext(), R.color.text_heading_light),
            binding.contactSupportTV
        )
    }

    private fun setupNavigation() {
        val intent = Intent(requireContext(), PaymentActivity::class.java)

        binding.sevisFeeCardView.setOnClickListener {
            intent.putExtra("destination", "sevis fee")
            startActivity(intent)
        }

        binding.trackOrderCardView.setOnClickListener {
            startActivity(Intent(requireContext(), TrackOrderActivity::class.java))
        }

        binding.applicationFeeCardView.setOnClickListener {
            intent.putExtra("destination", "application fee")
            startActivity(intent)
        }

        binding.wesEvaluationCardView.setOnClickListener {
            intent.putExtra("destination", "credential evaluation")
            startActivity(intent)
        }

        binding.contactSupportTV.setOnClickListener {
            startActivity(Intent(requireContext(), BrowserActivity::class.java))
        }
    }
}