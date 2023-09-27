package tech.hackcity.educarts.ui.auth.getStarted

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.RegionRepository
import tech.hackcity.educarts.data.storage.AppDatabase
import tech.hackcity.educarts.databinding.FragmentGetStartedBinding
import tech.hackcity.educarts.domain.model.location.Country
import tech.hackcity.educarts.domain.model.location.RegionResponse
import tech.hackcity.educarts.ui.settings.profile.ProfileViewModelFactory
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.Coroutines
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
        sharedViewModel.updateHorizontalStepViewPosition(1)
    }

}