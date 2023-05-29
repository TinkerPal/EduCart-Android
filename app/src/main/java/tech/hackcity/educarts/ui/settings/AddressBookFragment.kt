package tech.hackcity.educarts.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import tech.hackcity.educarts.R
import tech.hackcity.educarts.ui.adapters.AddressBookAdapter
import tech.hackcity.educarts.domain.model.AddressBook
import tech.hackcity.educarts.databinding.FragmentAddressBookBinding
import tech.hackcity.educarts.ui.viewmodels.AddressBookViewModel
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.observeOnce

/**
 *Created by Victor Loveday on 2/22/23
 */
class AddressBookFragment: Fragment(R.layout.fragment_address_book) {

    private lateinit var binding: FragmentAddressBookBinding

    private lateinit var addressBookViewModel: AddressBookViewModel
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var addressBookAdapter: AddressBookAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAddressBookBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        //initialize view models
        addressBookViewModel = ViewModelProvider(this)[AddressBookViewModel::class.java]

        setupAddressBookRecyclerView()

        //navigate to add address fragment
        binding.addAddressBookBtn.setOnClickListener {
            findNavController().navigate(R.id.action_addressBookFragment_to_addAddressBookFragment)
        }
    }

    private fun setupAddressBookRecyclerView() {
        addressBookAdapter = AddressBookAdapter(requireContext())
        binding.addressBookRV.apply {
            adapter = addressBookAdapter
            layoutManager = LinearLayoutManager(requireContext())

            addressBookViewModel.readAllAddresses.observe(viewLifecycleOwner) { addresses ->
                if (addresses.isNotEmpty()) {
                    binding.addressBookRV.visibility = View.VISIBLE
                    binding.emptyAddressLayout.visibility = View.GONE
                    addressBookAdapter.setData(addresses)
                }else {
                    binding.addressBookRV.visibility = View.GONE
                    binding.emptyAddressLayout.visibility = View.VISIBLE
                }
            }

            selectAddress()
            editAddress()
        }
    }

    private fun editAddress() {
        addressBookAdapter.setOnEditClickListener {
            val action = AddressBookFragmentDirections.actionAddressBookFragmentToEditAddressBookFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun selectAddress() {
        addressBookAdapter.setOnActivateClickListener {

            //save selected address
            val isAddressSelected: Boolean = !it.isAddressActive
            val selectedAddress = AddressBook(it.id, it.homeAddress, it.apartmentNumber, it.city, it.country, isAddressSelected)
            addressBookViewModel.saveAddress(selectedAddress)

            //unselect previously selected addresses
            addressBookViewModel.readAllAddresses.observeOnce(viewLifecycleOwner) { allAddress ->
                for (address in allAddress) {
                    if (it.id != address.id) {
                        val previousAddress = AddressBook(address.id, address.homeAddress, address.apartmentNumber, address.city, address.country, false)
                        addressBookViewModel.saveAddress(previousAddress)
                    }
                }
            }

        }
    }
}