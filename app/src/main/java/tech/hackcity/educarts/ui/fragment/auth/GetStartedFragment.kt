package tech.hackcity.educarts.ui.fragment.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentGetStartedBinding
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

/**
 *Created by Victor Loveday on 2/19/23
 */
class GetStartedFragment: Fragment(R.layout.fragment_get_started) {

    private lateinit var binding: FragmentGetStartedBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentGetStartedBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()

        binding.personalAccountBtn.setOnClickListener {
            findNavController().navigate(R.id.action_getStartedFragment_to_createPersonalAccountFragment)
        }
    }

    private fun setupToolbar() {
        sharedViewModel.setToolbarVisibility(true)
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }
}