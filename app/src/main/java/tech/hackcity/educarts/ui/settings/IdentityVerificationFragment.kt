package tech.hackcity.educarts.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentIdentityVerificationBinding

/**
 *Created by Victor Loveday on 2/26/23
 */
class IdentityVerificationFragment: Fragment(R.layout.fragment_identity_verification) {
    private lateinit var binding: FragmentIdentityVerificationBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentIdentityVerificationBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)


    }
}