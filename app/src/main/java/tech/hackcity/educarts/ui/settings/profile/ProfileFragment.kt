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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.storage.UserInfoManager
import tech.hackcity.educarts.databinding.FragmentProfileBinding
import tech.hackcity.educarts.uitls.Constants
import tech.hackcity.educarts.uitls.shortenString
import java.lang.StringBuilder

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
    }

    @SuppressLint("SetTextI18n")
    private fun setupUserInfo() {
        val userInfoManager = UserInfoManager(requireContext())
        userInfoManager.fetchUserInfo().asLiveData().observe(viewLifecycleOwner) { user ->

            with(binding) {
                val profileUrl = "${Constants.EDU_CARTS_MEDIA_URL}${user.profilePhoto}"
                val requestOptions = RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()

                if (user.profilePhoto.isNullOrEmpty()) {
                    profilePhoto.setImageResource(R.drawable.default_profile)
                } else {
                    Glide.with(requireContext())
                        .load(profileUrl)
                        .apply(requestOptions)
                        .into(profilePhoto)
                }

                val fullName = "${user.firstName} ${user.lastName}"
                fullNameTV.text = resources.getString(R.string.hello__, fullName)
                userIDTV.text = resources.getString(R.string.user_id, shortenString(user.id, 8))
                firstNameTV.text = user.firstName
                lastNameTV.text = user.lastName
                emailTV.text = user.email
                phoneNumberTV.text = user.phoneNumber
                countryOfResidenceTV.text = user.countryOfResidence
                institutionOfStudyTV.text = user.institutionOfStudy?.takeIf { it.isNotEmpty() } ?: resources.getString(R.string.nil)

                addressTV.text = if (user.city.isNullOrEmpty() || user.state.isNullOrEmpty())
                    user.countryOfResidence
                else "${user.city}, ${user.state}, ${user.countryOfResidence}"
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