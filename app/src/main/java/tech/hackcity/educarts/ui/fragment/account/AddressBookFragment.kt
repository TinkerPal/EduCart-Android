package tech.hackcity.educarts.ui.fragment.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import tech.hackcity.educarts.R
import tech.hackcity.educarts.adapters.AddressBookAdapter
import tech.hackcity.educarts.data.model.AddressBook
import tech.hackcity.educarts.databinding.FragmentAddressBookBinding
import tech.hackcity.educarts.databinding.FragmentProfileBinding
import tech.hackcity.educarts.ui.viewmodels.AddressBookViewModel
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

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
            val bundle = Bundle().apply {
                putSerializable("address", it)
            }
            findNavController().navigate(
                R.id.action_addressBookFragment_to_editAddressBookFragment,
                bundle
            )
//            val action = AddressBookFragmentDirections.actionAddressBookFragmentToEditAddressBookFragment(it)
//            findNavController().navigate(action)
        }
    }

    private fun selectAddress() {
        addressBookAdapter.setOnActivateClickListener { selectedAddress ->

            val isAddressSelected: Boolean = !selectedAddress.activeAddress
            val address = AddressBook(selectedAddress.id, selectedAddress.homeAddress, selectedAddress.apartmentNumber, selectedAddress.city, selectedAddress.country, isAddressSelected)
            addressBookViewModel.saveAddress(address)

//            sharedViewModel.updateAddress(selectedAddress)
//
//            sharedViewModel.getAddress().observe(viewLifecycleOwner) {
//
//
//            }

//            addressBookViewModel.readAllAddresses.observe(viewLifecycleOwner){ allAddress ->
//                for (a in allAddress) {
//                    addressBookViewModel.saveAddress(a)
//                }
//            }
        }
    }
}