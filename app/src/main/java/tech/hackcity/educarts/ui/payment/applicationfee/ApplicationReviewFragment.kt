package tech.hackcity.educarts.ui.payment.applicationfee

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentApplicationReviewBinding

/**
 *Created by Victor Loveday on 6/2/23
 */
class ApplicationReviewFragment : Fragment(R.layout.fragment_application_review) {

    private lateinit var binding: FragmentApplicationReviewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentApplicationReviewBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.nextBtn.setOnClickListener {
            val action =
                ApplicationReviewFragmentDirections
                    .actionApplicationReviewFragmentToUniversityPortalLoginDetailsFragment(
                        resources.getString(R.string.application_review),
                        resources.getString(R.string.application_review)
                    )
            findNavController().navigate(action)
        }

    }
}