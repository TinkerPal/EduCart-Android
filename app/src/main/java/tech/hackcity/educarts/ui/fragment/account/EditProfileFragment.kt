package tech.hackcity.educarts.ui.fragment.account

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.veton.countrypicker.Country
import com.veton.countrypicker.CountryPicker
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentEditProfileBinding
import tech.hackcity.educarts.databinding.FragmentProfileBinding

/**
 *Created by Victor Loveday on 2/22/23
 */
class EditProfileFragment: Fragment(R.layout.fragment_edit_profile) {

    private lateinit var binding: FragmentEditProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentEditProfileBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

//        val items = listOf("Afghanistan", "Albania", "Algeria",
//            "Afghanistan", "Albania", "Algeria", "Afghanistan",
//            "Albania", "Algeria", "Afghanistan", "Albania", "Algeria",
//            "Afghanistan", "Albania", "Algeria", "Afghanistan", "Albania", "Algeria",
//            "Afghanistan", "Albania", "Algeria", "Afghanistan", "Albania", "Algeria")
//        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.country_list_item, items)
//        binding.autoCompleteTextView.setAdapter(arrayAdapter)

        binding.showCountries.setOnClickListener {
            val countryPicker = CountryPicker(requireContext(), ::countrySelected)
            countryPicker.showCountryPickerInFragment(this)
        }
    }

    private fun countrySelected(country: Country) {
        binding.countryFlag.text = country.flag
        binding.countryName.text = country.name

    }

}