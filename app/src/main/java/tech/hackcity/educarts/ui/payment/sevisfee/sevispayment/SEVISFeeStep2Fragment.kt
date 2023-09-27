package tech.hackcity.educarts.ui.payment.sevisfee.sevispayment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.payment.SEVISFeeRepository
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.databinding.FragmentSevisFeeStep2Binding
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep2Response
import tech.hackcity.educarts.domain.model.support.MultipleChoiceResponse
import tech.hackcity.educarts.domain.model.support.MultipleChoiceResponseData
import tech.hackcity.educarts.ui.adapters.CountriesAdapter
import tech.hackcity.educarts.ui.alerts.RegionsBottomSheetFragment
import tech.hackcity.educarts.ui.alerts.ToastType
import tech.hackcity.educarts.ui.viewmodels.CountryViewModel
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.disablePrimaryButtonState
import tech.hackcity.educarts.uitls.enablePrimaryButtonState
import tech.hackcity.educarts.uitls.hideButtonLoadingState
import tech.hackcity.educarts.uitls.showButtonLoadingState
import tech.hackcity.educarts.uitls.toast

/**
 *Created by Victor Loveday on 8/3/23
 */
class SEVISFeeStep2Fragment : Fragment(R.layout.fragment_sevis_fee_step_2), SEIVSFeeStep2Listener {

    private lateinit var binding: FragmentSevisFeeStep2Binding

    private var currentBottomSheetFragment: RegionsBottomSheetFragment? = null

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var viewModel: SEVISFeeViewModel
    private lateinit var countryViewModel: CountryViewModel
    private val args: SEVISFeeStep2FragmentArgs by navArgs()

    private var countryOfCitizenship = ""
    private var countryOfBirth = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSevisFeeStep2Binding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val api = RetrofitInstance(requireContext())
        val sharePreferencesManager = SharePreferencesManager(requireContext())
        val repository = SEVISFeeRepository(api, sharePreferencesManager)
        val factory = SEVISFeeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[SEVISFeeViewModel::class.java]
        countryViewModel = ViewModelProvider(this)[CountryViewModel::class.java]
        viewModel.listener2 = this

        binding.textGuide1.text = resources.getString(
            R.string.please_fill_the_following_form_as_it_is_in_your,
            args.formType
        )

        if (args.formType == resources.getString(R.string.ds_2019)) {
            viewModel.fetchSevisCategory()
            binding.categoryTextInputLayout.visibility = View.VISIBLE
        }

        binding.countryOfCitizenshipET.setOnClickListener {
            showCountriesBottomSheet(1)
        }
        binding.countryOfBirthET.setOnClickListener {
            showCountriesBottomSheet(2)
        }


        binding.nextBtn.setOnClickListener {
            viewModel.formType = args.formType
            viewModel.category = binding.category.text.toString()
            viewModel.email = binding.emailET.text.toString().trim()
            viewModel.phoneNumber = binding.phoneNumberET.text.toString().trim()
            viewModel.countryOfCitizenship = countryOfCitizenship
            viewModel.countryOfBirth =  countryOfBirth

            Coroutines.onMainWithScope(viewModel.viewModelScope) {
                viewModel.submitSevisFeeStep2(requireContext())
            }

        }

    }

    private fun setupSevisCategory(data: List<MultipleChoiceResponseData>) {
        val options = mutableListOf<String>()

        for (i in data) {
            options.add(i.choice)
        }

        val arrayAdapter1 =
            ArrayAdapter(
                requireContext(),
                R.layout.security_questions_item,
                options
            )
        binding.category.setAdapter(arrayAdapter1)
    }

    private fun showCountriesBottomSheet(type: Int) {
        countryViewModel.readAllCountries.observe(viewLifecycleOwner) { countries ->
            if (countries.isEmpty()) {
                context?.toast(description = resources.getString(R.string.no_country_available), toastType = ToastType.ERROR)
                return@observe
            }

            val bottomSheetFragment = RegionsBottomSheetFragment()
            currentBottomSheetFragment = bottomSheetFragment // Store the reference

            bottomSheetFragment.onBindingReady { bottomSheetBinding ->
                val recyclerView = bottomSheetBinding.regionsRV

                val countriesAdapter = CountriesAdapter()
                recyclerView.apply {
                    adapter = countriesAdapter
                    layoutManager = LinearLayoutManager(requireContext())
                    countriesAdapter.setData(countries.toMutableList())

                    countriesAdapter.setOnItemClickListener {
                        when(type) {
                            1 -> {
                                countryOfCitizenship = it.name
                                binding.countryOfCitizenshipET.setText(it.name)
                                currentBottomSheetFragment?.closeBottomSheet()
                            }
                            2 -> {
                                countryOfBirth = it.name
                                binding.countryOfBirthET.setText(it.name)
                                currentBottomSheetFragment?.closeBottomSheet()
                            }
                        }
                    }
                }
            }
            activity?.supportFragmentManager?.let {
                bottomSheetFragment.show(it, bottomSheetFragment.tag)
            }

        }
    }

    override fun onRequestStarted() {
        sharedViewModel.updateScreenLoader(Pair(true, ""))
        showButtonLoadingState(binding.nextBtn, binding.progressBar, "")
        disablePrimaryButtonState(binding.nextBtn)
    }

    override fun onFetchSevisCategoryStarted() {
        sharedViewModel.updateScreenLoader(Pair(true, ""))
    }

    override fun onRequestFailed(message: String) {
        context?.toast(description = message, toastType = ToastType.ERROR)
        hideButtonLoadingState(
            binding.nextBtn,
            binding.progressBar,
            resources.getString(R.string.next)
        )
        enablePrimaryButtonState(binding.nextBtn)
        sharedViewModel.updateScreenLoader(Pair(false, ""))
    }

    override fun onFetchSevisCategorySuccessful(response: MultipleChoiceResponse) {
        setupSevisCategory(response.data)
        sharedViewModel.updateScreenLoader(Pair(false, ""))
    }

    override fun onRequestSuccessful(response: SEVISFeeStep2Response) {
        hideButtonLoadingState(
            binding.nextBtn,
            binding.progressBar,
            resources.getString(R.string.next)
        )
        enablePrimaryButtonState(binding.nextBtn)
        sharedViewModel.updateScreenLoader(Pair(false, ""))

        val action =
            SEVISFeeStep2FragmentDirections.actionSevisFeeStep2FragmentToSevisFeeStep3Fragment(args.formType)
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.updateHorizontalStepViewPosition(2)
    }
}