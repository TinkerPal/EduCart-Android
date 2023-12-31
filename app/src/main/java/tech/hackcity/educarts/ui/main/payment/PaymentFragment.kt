package tech.hackcity.educarts.ui.main.payment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentPaymentBinding
import tech.hackcity.educarts.ui.alerts.ToastType
import tech.hackcity.educarts.ui.payment.PaymentActivity
import tech.hackcity.educarts.ui.payment.orderdetails.TrackOrderActivity
import tech.hackcity.educarts.ui.support.SupportActivity
import tech.hackcity.educarts.uitls.spannableTextWithForegroundColour
import tech.hackcity.educarts.uitls.toast

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
            resources.getString(R.string.can_t_find_a_payment_contact_support),
            21,
            39,
            ContextCompat.getColor(requireContext(), R.color.text_heading_light),
            binding.contactSupportTV
        )
    }

    private fun setupNavigation() {
        val paymentIntent = Intent(requireContext(), PaymentActivity::class.java)
        val supportIntent = Intent(requireContext(), SupportActivity::class.java)

        binding.sevisFeeCardView.setOnClickListener {
            paymentIntent.putExtra("destination", "sevis fee")
            startActivity(paymentIntent)
        }

        binding.trackOrderCardView.setOnClickListener {
            startActivity(Intent(requireContext(), TrackOrderActivity::class.java))
        }

        binding.applicationFeeCardView.setOnClickListener {
            context?.toast(
                title = resources.getString(R.string.coming_soon),
                description = resources.getString(R.string.keep_your_eyes_glue),
                toastType = ToastType.INFO
            )
        }

        binding.tuitionFeeCardView.setOnClickListener {
            context?.toast(
                title = resources.getString(R.string.coming_soon),
                description = resources.getString(R.string.keep_your_eyes_glue),
                toastType = ToastType.INFO
            )
        }

        binding.wesEvaluationCardView.setOnClickListener {
            context?.toast(
                title = resources.getString(R.string.coming_soon),
                description = resources.getString(R.string.keep_your_eyes_glue),
                toastType = ToastType.INFO
            )
        }

        binding.othersCardView.setOnClickListener {
            context?.toast(
                title = resources.getString(R.string.coming_soon),
                description = resources.getString(R.string.keep_your_eyes_glue),
                toastType = ToastType.INFO
            )
        }

        binding.contactSupportTV.setOnClickListener {
            context?.toast(
                title = resources.getString(R.string.coming_soon),
                description = resources.getString(R.string.keep_your_eyes_glue),
                toastType = ToastType.INFO
            )
        }
    }
}