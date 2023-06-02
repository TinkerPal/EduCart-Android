package tech.hackcity.educarts.ui.settings.profile

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentProfileBinding

/**
 *Created by Victor Loveday on 2/22/23
 */
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentProfileBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        //navigate to address book
        binding.addressBook.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_addressBookFragment)
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