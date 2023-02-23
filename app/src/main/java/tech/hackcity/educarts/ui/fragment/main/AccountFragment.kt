package tech.hackcity.educarts.ui.fragment.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentAccountBinding
import tech.hackcity.educarts.ui.SettingsActivity
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

/**
 *Created by Victor Loveday on 2/22/23
 */
class AccountFragment: Fragment(R.layout.fragment_account) {

    private lateinit var binding: FragmentAccountBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAccountBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.viewProfile.setOnClickListener {
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            intent.putExtra("destination", "Profile")
            startActivity(intent)
        }
        binding.identityVerification.setOnClickListener {
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            intent.putExtra("destination", "IDV")
            startActivity(intent)
        }

        showSecuritySetting()
    }

    private fun showSecuritySetting() {
        binding.securitySettings.setOnClickListener {
            if (binding.hiddenView.visibility == View.VISIBLE) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(binding.securitySettings, AutoTransition())
                }
                binding.hiddenView.visibility = View.GONE
                binding.arrowButton.setImageResource(R.drawable.arrow_forward)
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(binding.securitySettings, AutoTransition())
                }
                binding.hiddenView.visibility = View.VISIBLE
                binding.arrowButton.setImageResource(R.drawable.arrow_down)
            }
        }

    }
}