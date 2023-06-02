package tech.hackcity.educarts.ui.settings.addressBook

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentAddAddressBookBinding
import tech.hackcity.educarts.domain.model.AddressBook
import tech.hackcity.educarts.uitls.toast

/**
 *Created by Victor Loveday on 2/22/23
 */
class AddAddressBookFragment : Fragment(R.layout.fragment_add_address_book) {

    private lateinit var binding: FragmentAddAddressBookBinding

    private lateinit var addressBookViewModel: AddressBookViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAddAddressBookBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        //initialize view models
        addressBookViewModel = ViewModelProvider(this)[AddressBookViewModel::class.java]

        binding.addAddressBookBtn.setOnClickListener {
            validateInput()
        }
    }

    private fun validateInput() {
        val homeAddress = binding.homeAddressET.text.toString().trim()
        val apartmentAddress = binding.apartmentET.text.toString().trim()
        val cityAddress = binding.cityET.text.toString().trim()
        val countryAddress = binding.countryET.text.toString().trim()

        if (homeAddress.isNotEmpty() && apartmentAddress.isNotEmpty()
            && cityAddress.isNotEmpty() && countryAddress.isNotEmpty()
        ) {
            addHomeAddress(homeAddress, apartmentAddress, cityAddress, countryAddress)
            findNavController().navigate(R.id.action_addAddressBookFragment_to_addressBookFragment)//go back to address book

        } else {
            context?.toast(resources.getString(R.string.field_can_not_be_empty))
        }
    }

    private fun addHomeAddress(home: String, apartment: String, city: String, country: String) {
        val address = AddressBook(0, home, apartment, city, country, false)
        addressBookViewModel.saveAddress(address)
    }
}