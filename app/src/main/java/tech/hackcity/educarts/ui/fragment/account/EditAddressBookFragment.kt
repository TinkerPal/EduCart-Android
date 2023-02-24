package tech.hackcity.educarts.ui.fragment.account

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.model.AddressBook
import tech.hackcity.educarts.databinding.FragmentEditAddressBookBinding
import tech.hackcity.educarts.ui.viewmodels.AddressBookViewModel

/**
 *Created by Victor Loveday on 2/22/23
 */
class EditAddressBookFragment: Fragment(R.layout.fragment_edit_address_book) {

    private lateinit var binding: FragmentEditAddressBookBinding

    private lateinit var addressBookViewModel: AddressBookViewModel

    private val args: EditAddressBookFragmentArgs by navArgs ()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentEditAddressBookBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        //initialize view models
        addressBookViewModel = ViewModelProvider(this)[AddressBookViewModel::class.java]


        updateUI()

        binding.addAddressBookBtn.setOnClickListener {
            validateInput()
        }
    }

    private fun updateUI() {
        binding.homeAddressET.setText(args.address.homeAddress)
        binding.apartmentET.setText(args.address.apartmentNumber)
        binding.cityET.setText(args.address.city)
        binding.countryET.setText(args.address.country)
    }

    private fun validateInput() {
         val home = binding.homeAddressET.text.toString().trim()
         val apartment = binding.apartmentET.text.toString().trim()
         val city = binding.cityET.text.toString().trim()
         val country = binding.countryET.text.toString().trim()

        if (home.isNotEmpty() && city.isNotEmpty() && country.isNotEmpty()) {
            addHomeAddress(args.address.id, home, apartment, city, country, args.address.isAddressActive)
            findNavController().navigate(R.id.action_editAddressBookFragment_to_addressBookFragment)//go back to address book
        }else {
            Toast.makeText(requireContext(), "Empty fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addHomeAddress(id: Int, home: String, apartment: String, city: String, country: String, isAddressActive: Boolean) {
        val address = AddressBook(id, home, apartment, city, country, isAddressActive)
        addressBookViewModel.saveAddress(address)
    }
}