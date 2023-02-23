package tech.hackcity.educarts.ui.fragment.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentAddressBookBinding
import tech.hackcity.educarts.databinding.FragmentProfileBinding

/**
 *Created by Victor Loveday on 2/22/23
 */
class AddressBookFragment: Fragment(R.layout.fragment_address_book) {

    private lateinit var binding: FragmentAddressBookBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAddressBookBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
    }
}