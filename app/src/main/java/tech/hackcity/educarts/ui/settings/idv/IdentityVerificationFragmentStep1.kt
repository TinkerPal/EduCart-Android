package tech.hackcity.educarts.ui.settings.idv

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentIdentityVerificationBinding
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

/**
 *Created by Victor Loveday on 2/26/23
 */
class IdentityVerificationFragmentStep1 : Fragment(R.layout.fragment_identity_verification) {
    private lateinit var binding: FragmentIdentityVerificationBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var docType = ""
    private var guide = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentIdentityVerificationBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.internationalPassportButton.setOnClickListener {
            docType = resources.getString(R.string.international_passport)
            guide = resources.getString(R.string.upload_the_front_side_of_international_passport)
            binding.intPassportRadioBtn.isChecked = true
            binding.voterCardRadioBtn.isChecked = false
            binding.ninRadioBtn.isChecked = false
        }
        binding.voterCardButton.setOnClickListener {
            docType = resources.getString(R.string.voter_s_card)
            guide = resources.getString(R.string.upload_the_front_side_of_voter_card)
            binding.intPassportRadioBtn.isChecked = false
            binding.voterCardRadioBtn.isChecked = true
            binding.ninRadioBtn.isChecked = false
        }
        binding.ninButton.setOnClickListener {
            docType = resources.getString(R.string.national_id_nin)
            guide = resources.getString(R.string.upload_the_front_side_of_nin)
            binding.intPassportRadioBtn.isChecked = false
            binding.voterCardRadioBtn.isChecked = false
            binding.ninRadioBtn.isChecked = true
        }

        binding.nextBtn.setOnClickListener {
            val action =
                IdentityVerificationFragmentStep1Directions
                    .actionIdentityVerificationFragmentStep1ToIdentityVerificationFragmentStep2(
                        docType,
                        guide
                    )
            findNavController().navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.updateHorizontalStepViewPosition(1)
    }

}