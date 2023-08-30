package tech.hackcity.educarts.ui.main.account

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.settings.SettingsRepository
import tech.hackcity.educarts.data.storage.SessionManager
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.data.storage.UserInfoManager
import tech.hackcity.educarts.databinding.FragmentAccountBinding
import tech.hackcity.educarts.ui.auth.AuthActivity
import tech.hackcity.educarts.ui.settings.SettingsActivity
import tech.hackcity.educarts.ui.settings.SettingsViewModel
import tech.hackcity.educarts.ui.settings.SettingsViewModelFactory
import tech.hackcity.educarts.ui.support.SupportActivity
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.shortenString

/**
 *Created by Victor Loveday on 2/22/23
 */
class AccountFragment: Fragment(R.layout.fragment_account) {

    private lateinit var binding: FragmentAccountBinding

    private lateinit var viewModel: SettingsViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAccountBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val api = RetrofitInstance(requireContext())
        val userInfoManager = UserInfoManager(requireContext())
        val sharePreferencesManager = SharePreferencesManager(requireContext())
        val sessionManager = SessionManager(requireContext())
        val repository = SettingsRepository(api,sessionManager , sharePreferencesManager, userInfoManager)
        val factory = SettingsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[SettingsViewModel::class.java]

        setupUserInfo()
        setupNavigation()
        securitySettings()

        binding.signOutBtn.setOnClickListener {
            logOutCurrentUser()
        }
    }

    private fun securitySettings() {
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


    private fun setupNavigation() {
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
        binding.faqs.setOnClickListener {
            val intent = Intent(requireContext(), SupportActivity::class.java)
            intent.putExtra("destination", "faqs")
            startActivity(intent)
        }
        binding.createPin.setOnClickListener {
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            intent.putExtra("destination", "Password and PIN")
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupUserInfo() {
        val userInfoManager = UserInfoManager(requireContext())

        userInfoManager.fetchUserInfo().asLiveData().observe(viewLifecycleOwner) { user ->
            binding.fullNameTV.text = "${user.firstName} ${user.lastName}"
            binding.userIDTV.text = resources.getString(R.string.user_id, shortenString(user.id, 8))
        }
    }

    private fun logOutCurrentUser() {
        viewModel.clearAllTokens()
        viewModel.clearSharedPreferences()
        viewModel.clearUser()

        val intent = Intent(requireContext(), AuthActivity::class.java)
        intent.putExtra("destination", "login")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

}