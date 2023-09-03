package tech.hackcity.educarts.ui.settings.idv

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentIdentityVerificationBinding
import tech.hackcity.educarts.databinding.FragmentIdentityVerificationStep2Binding
import tech.hackcity.educarts.databinding.FragmentIdentityVerificationStep3Binding
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

/**
 *Created by Victor Loveday on 2/26/23
 */
class IdentityVerificationFragmentStep3: Fragment(R.layout.fragment_identity_verification_step3) {
    private lateinit var binding: FragmentIdentityVerificationStep3Binding

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentIdentityVerificationStep3Binding.bind(view)
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.updateHorizontalStepViewPosition(3)
    }

}