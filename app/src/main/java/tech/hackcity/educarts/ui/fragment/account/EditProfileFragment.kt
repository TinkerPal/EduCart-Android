package tech.hackcity.educarts.ui.fragment.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentEditProfileBinding
import tech.hackcity.educarts.databinding.FragmentProfileBinding

/**
 *Created by Victor Loveday on 2/22/23
 */
class EditProfileFragment: Fragment(R.layout.fragment_edit_profile) {

    private lateinit var binding: FragmentEditProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentEditProfileBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
    }
}