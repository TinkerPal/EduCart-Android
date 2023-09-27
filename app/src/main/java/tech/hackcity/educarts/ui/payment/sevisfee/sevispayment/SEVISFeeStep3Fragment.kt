package tech.hackcity.educarts.ui.payment.sevisfee.sevispayment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.payment.SEVISFeeRepository
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.databinding.FragmentSevisFeeStep3Binding
import tech.hackcity.educarts.domain.model.location.State
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep3Response
import tech.hackcity.educarts.ui.adapters.CountriesAdapter
import tech.hackcity.educarts.ui.adapters.StatesAdapter
import tech.hackcity.educarts.ui.alerts.RegionsBottomSheetFragment
import tech.hackcity.educarts.ui.alerts.ToastType
import tech.hackcity.educarts.ui.payment.ordersummary.OrderSummaryActivity
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
class SEVISFeeStep3Fragment : Fragment(R.layout.fragment_sevis_fee_step_3), SEIVSFeeStep3Listener {

    private lateinit var binding: FragmentSevisFeeStep3Binding

    private var currentBottomSheetFragment: RegionsBottomSheetFragment? = null

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var viewModel: SEVISFeeViewModel
    private lateinit var countryViewModel: CountryViewModel
    private val args: SEVISFeeStep3FragmentArgs by navArgs()
    private var country = ""
    private var state = ""
    private var city = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSevisFeeStep3Binding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val api = RetrofitInstance(requireContext())
        val sharePreferencesManager = SharePreferencesManager(requireContext())
        val repository = SEVISFeeRepository(api, sharePreferencesManager)
        val factory = SEVISFeeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[SEVISFeeViewModel::class.java]
        countryViewModel = ViewModelProvider(this)[CountryViewModel::class.java]
        viewModel.listener3 = this


        binding.textGuide1.text = resources.getString(
            R.string.please_fill_the_following_form_as_it_is_in_your,
            args.formType
        )

        binding.country.setOnClickListener {
            showCountriesBottomSheet()
        }

        binding.nextBtn.setOnClickListener {
            viewModel.streetAddress1 = binding.streetAddress1ET.text.toString().trim()
            viewModel.streetAddress2 = binding.streetAddress2ET.text.toString().trim()
            viewModel.country = country
            viewModel.state = state
            viewModel.city = binding.city.text.toString().trim()

            Coroutines.onMainWithScope(viewModel.viewModelScope) {
                viewModel.submitSevisFeeStep3(requireContext())
            }
        }
    }

    private fun showCountriesBottomSheet() {
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
                        country = it.name
                        binding.country.setText(it.name)
                        showStatesBottomSheet(it.states)
                        currentBottomSheetFragment?.closeBottomSheet()
                    }
                }
            }
            activity?.supportFragmentManager?.let {
                bottomSheetFragment.show(it, bottomSheetFragment.tag)
            }

        }
    }

    private fun showStatesBottomSheet(states: List<State>) {
        binding.state.setOnClickListener {
            val bottomSheetFragment = RegionsBottomSheetFragment()
            currentBottomSheetFragment = bottomSheetFragment // Store the reference

            bottomSheetFragment.onBindingReady { bottomSheetBinding ->
                val recyclerView = bottomSheetBinding.regionsRV

                val statesAdapter = StatesAdapter()
                recyclerView.apply {
                    adapter = statesAdapter
                    layoutManager = LinearLayoutManager(requireContext())
                    statesAdapter.setData(states.toMutableList())
                    statesAdapter.setOnItemClickListener {
                        state = it.name
                        binding.state.setText(it.name)
                        currentBottomSheetFragment?.closeBottomSheet()
                    }
                }
            }
            activity?.supportFragmentManager?.let { bottomSheetFragment.show(it, bottomSheetFragment.tag) }
        }
    }


    override fun onRequestStarted() {
        sharedViewModel.updateScreenLoader(Pair(true, ""))
        showButtonLoadingState(binding.nextBtn, binding.progressBar, "")
        disablePrimaryButtonState(binding.nextBtn)
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

    override fun onRequestSuccessful(response: SEVISFeeStep3Response) {
        hideButtonLoadingState(
            binding.nextBtn,
            binding.progressBar,
            resources.getString(R.string.next)
        )
        enablePrimaryButtonState(binding.nextBtn)
        sharedViewModel.updateScreenLoader(Pair(false, ""))

        val intent = Intent(requireContext(), OrderSummaryActivity::class.java)
        intent.putExtra("orderType", "sevis")
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.updateHorizontalStepViewPosition(3)
    }


}