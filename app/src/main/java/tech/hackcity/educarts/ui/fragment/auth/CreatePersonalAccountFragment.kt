package tech.hackcity.educarts.ui.fragment.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentCreatePersonalAccountBinding
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

/**
 *Created by Victor Loveday on 2/20/23
 */
class CreatePersonalAccountFragment : Fragment(R.layout.fragment_create_personal_account) {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var binding: FragmentCreatePersonalAccountBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCreatePersonalAccountBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()

    }

    private fun setupToolbar() {
        sharedViewModel.setToolbarVisibility(false)
    }
}