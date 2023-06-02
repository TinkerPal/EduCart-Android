package tech.hackcity.educarts.ui.payment.applicationfee

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentSchoolFormApplicationLinkBinding
import tech.hackcity.educarts.ui.payment.PaymentActivity
import tech.hackcity.educarts.uitls.clickableLink

/**
 *Created by Victor Loveday on 6/1/23
 */
class SchoolFormApplicationLinkFragment : Fragment(R.layout.fragment_school_form_application_link) {

    private lateinit var binding: FragmentSchoolFormApplicationLinkBinding

    private val args: SchoolFormApplicationLinkFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSchoolFormApplicationLinkBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        (activity as PaymentActivity).supportActionBar?.title = args.pageTitle

        binding.service.text = args.service
        val websiteLinkText = resources.getString(R.string.website_link, args.school)
        binding.websiteLinkTV.text = websiteLinkText

        clickableLink(
            requireContext(),
            websiteLinkText,
            "https://hackcity.tech/",
            binding.websiteLinkTV,
            0,
            args.school.length
        )

        binding.proceedBtn.setOnClickListener {
            val action =
                SchoolFormApplicationLinkFragmentDirections
                    .actionSchoolFormApplicationLinkFragmentToUniversityPortalLoginDetailsFragment(
                        args.pageTitle,
                        resources.getString(R.string.application_fee)
                    )
            findNavController().navigate(action)
        }
    }
}