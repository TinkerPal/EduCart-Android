package tech.hackcity.educarts.ui.auth.getStarted

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentGetStartedBinding
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.enablePrimaryButtonState

/**
 *Created by Victor Loveday on 8/1/23
 */
class GetStartedFragment: Fragment(R.layout.fragment_get_started) {

    private lateinit var binding: FragmentGetStartedBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentGetStartedBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.personalAccountBtn.setOnClickListener {
            binding.personalRadioButton.isChecked = true
            enablePrimaryButtonState(binding.nextBtn)
        }

        binding.personalRadioButton.setOnClickListener {
            if (!binding.personalRadioButton.isChecked) {
                binding.personalRadioButton.isChecked = true
                enablePrimaryButtonState(binding.nextBtn)
            }
        }

        binding.signInTextView.setOnClickListener{
            findNavController().navigate(R.id.action_getStartedFragment_to_loginFragment)
        }
        binding.nextBtn.setOnClickListener{
            findNavController().navigate(R.id.action_getStartedFragment_to_createPersonalAccountFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.setToolBarColor(ContextCompat.getColor(requireContext(), R.color.background_001))
        sharedViewModel.updateHorizontalStepViewPosition(1)
    }

}