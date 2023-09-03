package tech.hackcity.educarts.ui.settings.idv

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentIdentityVerificationBinding
import tech.hackcity.educarts.databinding.FragmentIdentityVerificationStep2Binding
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

/**
 *Created by Victor Loveday on 2/26/23
 */
class IdentityVerificationFragmentStep2: Fragment(R.layout.fragment_identity_verification_step2) {
    private lateinit var binding: FragmentIdentityVerificationStep2Binding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val args: IdentityVerificationFragmentStep2Args by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentIdentityVerificationStep2Binding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.title.text = args.pageTitle
        binding.textGuide2.text = args.guide

        binding.imageView.apply {
          when(args.pageTitle) {
              resources.getString(R.string.international_passport) -> setImageResource(R.drawable.flight_book_white_bg)
              resources.getString(R.string.voter_s_card) -> setImageResource(R.drawable.idv2_white_bg)
              resources.getString(R.string.national_id_nin) -> setImageResource(R.drawable.passport_white_bg)
          }
        }

        binding.nextBtn.setOnClickListener {
            findNavController().navigate(R.id.action_identityVerificationFragmentStep2_to_identityVerificationFragmentStep3)
        }

    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.updateHorizontalStepViewPosition(2)
    }

}