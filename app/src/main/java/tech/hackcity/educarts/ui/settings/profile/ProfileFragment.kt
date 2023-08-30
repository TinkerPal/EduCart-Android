package tech.hackcity.educarts.ui.settings.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.storage.UserInfoManager
import tech.hackcity.educarts.databinding.FragmentProfileBinding
import tech.hackcity.educarts.uitls.observeOnce
import tech.hackcity.educarts.uitls.shortenString

/**
 *Created by Victor Loveday on 2/22/23
 */
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentProfileBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        setupUserInfo()

        binding.addressBook.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_addressBookFragment)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupUserInfo() {
        val userInfoManager = UserInfoManager(requireContext())
        userInfoManager.fetchUserInfo().asLiveData().observe(viewLifecycleOwner) { user ->
            with(binding) {
                val fullName = "${user.firstName} ${user.lastName}"
                title.text = resources.getString(R.string.hello__, fullName)
                userIDTV.text = resources.getString(R.string.user_id, shortenString(user.id, 8))
                firstNameTV.text = user.firstName
                lastNameTV.text = user.lastName
                emailTV.text = user.email
                phoneNumberTV.text = user.phoneNumber
                countryOfResidenceTV.text = user.countryOfResidence
                institutionOfStudyTV.text = user.institutionOfStudy?.takeIf { it.isNotEmpty() } ?: resources.getString(R.string.nil)
                addressBookTV.text = user.addressBook?.takeIf { it.isNotEmpty() } ?: resources.getString(R.string.nil)
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.profile_toolbar_menu, menu)
        menu.findItem(R.id.editProfileIcon).isVisible = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.editProfileIcon -> {
                findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}