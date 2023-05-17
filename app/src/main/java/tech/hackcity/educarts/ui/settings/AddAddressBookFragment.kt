package tech.hackcity.educarts.ui.settings

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.model.AddressBook
import tech.hackcity.educarts.databinding.FragmentAddAddressBookBinding
import tech.hackcity.educarts.ui.viewmodels.AddressBookViewModel

/**
 *Created by Victor Loveday on 2/22/23
 */
class AddAddressBookFragment: Fragment(R.layout.fragment_add_address_book) {

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

        if (homeAddress.isNotEmpty() && apartmentAddress.isNotEmpty() && cityAddress.isNotEmpty() && countryAddress.isNotEmpty()) {
            addHomeAddress(homeAddress, apartmentAddress, cityAddress, countryAddress)
            findNavController().navigate(R.id.action_addAddressBookFragment_to_addressBookFragment)//go back to address book
        }else {
            Toast.makeText(requireContext(), "Empty fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addHomeAddress(home: String, apartment: String, city: String, country: String) {
        val address = AddressBook(0, home, apartment, city, country, false)
        addressBookViewModel.saveAddress(address)
    }
}