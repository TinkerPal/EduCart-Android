package com.veton.countrypicker

import android.content.Context
import android.telephony.TelephonyManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleObserver
import androidx.recyclerview.widget.LinearLayoutManager
import com.veton.countrypicker.databinding.CountryPickerBinding
import com.veton.countrypicker.utils.Countries
import com.veton.countrypicker.utils.equalIgnoreCase
import com.veton.countrypicker.utils.toastMsg

class CountryPicker(
    private val context: Context,
    private var onCountryClicked: (Country) -> Unit,
    private var sortBy: Sort? = Sort.NAME,
) : LifecycleObserver, CountryPickerListener, OnCountrySelectedListener {
    private lateinit var countryPickerBinding: CountryPickerBinding
    private val countriesAdapter: CountriesAdapter by lazy {
        CountriesAdapter(this)
    }

    private lateinit var countryPickersView: CountryPickersView

    private var searchResults: ArrayList<Country>? = null

    val countries = Countries.countries

    init {
        sortCountries(countries)
    }

    fun sortCountries(countries: ArrayList<Country>) {
        when (sortBy) {
            Sort.NAME -> countries.sortBy { it.name }
            Sort.ISO -> countries.sortBy { it.code }
            Sort.DIAL_CODE -> countries.sortBy { it.dialCode }
            else -> {}
        }
    }

    fun showCountryPicker(activity: AppCompatActivity) {
        activity.lifecycle.addObserver(this)
        countryPickersView = CountryPickersView.newInstance(countryPickerListener = this, onDismissDialog = {
            searchResults?.clear()
            searchResults?.addAll(countries)
            countryPickersView.dismissAllowingStateLoss()
        }).apply {
            show(activity.supportFragmentManager, "Country Picker View")
        }
    }

    fun showCountryPickerInFragment(frag: Fragment) {
        frag.lifecycle.addObserver(this)
        countryPickersView = CountryPickersView.newInstance(countryPickerListener = this, onDismissDialog = {
            searchResults?.clear()
            searchResults?.addAll(countries)
            countryPickersView.dismissAllowingStateLoss()
        }).apply {
            show(frag.requireActivity().supportFragmentManager, "Country Picker View")
        }
    }

    override fun initPickerView(binding: CountryPickerBinding) {
        countryPickerBinding = binding
    }

    override fun setSearch() {
        countryPickerBinding.searchET.doAfterTextChanged {
            search(it.toString())
        }
    }

    override fun sort() {
        countryPickerBinding.sortBtn.setOnClickListener {
            when (sortBy) {
                Sort.NAME -> sortBy = Sort.DIAL_CODE
                Sort.DIAL_CODE -> sortBy = Sort.ISO
                Sort.ISO -> sortBy = Sort.NAME
                else -> {}
            }
            sortCountries(searchResults ?: countries)
            countriesAdapter.notifyDataSetChanged()

            context.toastMsg(context.getString(R.string.sorted_by, sortBy?.sortName))
        }
    }

    private fun search(query: String) {
        searchResults?.clear()

        countries.forEach {
            if (it.name.lowercase().contains(query.lowercase()))
                searchResults?.add(it)
        }
        countriesAdapter.notifyDataSetChanged()
    }

    override fun setupRecyclerView() {
        searchResults = ArrayList<Country>().apply {
            ensureCapacity(countries.size)
            addAll(countries)
        }

        countryPickerBinding.countriesRV.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
        }

        countriesAdapter.submitList(searchResults)
    }

    override fun onCountrySelected(country: Country) {
        onCountryClicked(country)
        countryPickersView.dismissAllowingStateLoss()
    }


    fun getCountryByCode(code: String) = countries.find {
        it.code equalIgnoreCase code
    }

    fun getCountryByName(name: String) = countries.find {
        it.name equalIgnoreCase name
    }

    fun getCountryByDialCode(dialCode: String) = countries.find {
        it.dialCodes.contains(dialCode)
    }

    val countryFromSIM: Country?
        get() {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            return if (telephonyManager.simState != TelephonyManager.SIM_STATE_ABSENT) {
                getCountryByCode(telephonyManager.simCountryIso)
            } else getCountryByCode("MK")
        }

    fun getCountryByDialCodeForFullNumber(fullPhoneNumber: String): Country? {
        val phoneNumberDigitsOnly = fullPhoneNumber.filter { it.isDigit() }
        var bestMatch: Country? = null
        var bestMatchLength = 0
        countries.forEach { country ->
            country.dialCodes.forEach { prefix ->
                val prefixDigitsOnly = prefix.filter { it.isDigit() }
                if (prefixDigitsOnly.length > bestMatchLength && phoneNumberDigitsOnly.startsWith(
                        prefixDigitsOnly
                    )
                ) {
                    bestMatch = country
                    bestMatchLength = prefix.length
                }
            }
        }
        return bestMatch
    }

    enum class Sort(val sortName: String) {
        NAME("Name"),
        ISO("ISO code"),
        DIAL_CODE("Dial code")
    }
}