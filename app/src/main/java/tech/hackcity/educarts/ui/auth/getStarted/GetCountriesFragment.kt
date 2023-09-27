package tech.hackcity.educarts.ui.auth.getStarted

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.RegionRepository
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.databinding.FragmentGetCountriesBinding
import tech.hackcity.educarts.domain.model.location.RegionResponse
import tech.hackcity.educarts.ui.viewmodels.CountryViewModel
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.hideViews
import tech.hackcity.educarts.uitls.showViews

/**
 *Created by Victor Loveday on 8/1/23
 */
class GetCountriesFragment : Fragment(R.layout.fragment_get_countries), GetStartedListener {

    private lateinit var binding: FragmentGetCountriesBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var countryViewModel: CountryViewModel
    private lateinit var viewModel: RegionsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentGetCountriesBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val api = RetrofitInstance(requireContext())
        val sharePreferencesManager = SharePreferencesManager(requireContext())
        val repository = RegionRepository(api, sharePreferencesManager)
        val factory = RegionsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[RegionsViewModel::class.java]
        countryViewModel = ViewModelProvider(this)[CountryViewModel::class.java]
        viewModel.listener = this

        Coroutines.onMainWithScope(viewModel.viewModelScope) {
            viewModel.fetchRegions()
        }

        binding.retryBtn.setOnClickListener {
            Coroutines.onMainWithScope(viewModel.viewModelScope) {
                viewModel.fetchRegions()
            }
        }


    }

    override fun onFetchRegionsStarted() {
        sharedViewModel.updateScreenLoader(
            Pair(
                true,
                resources.getString(R.string.setting_the_app)
            )
        )
        hideViews(listOf(binding.retryLayout))
    }

    override fun onFetchRequestFailed(errorMessage: String) {
        sharedViewModel.updateScreenLoader(Pair(false, ""))
        showViews(listOf(binding.retryLayout))
    }

    override fun onFetchRegionSuccessful(response: RegionResponse) {
        viewModel.saveAppSetupStatus(true)
        sharedViewModel.updateScreenLoader(Pair(false, ""))
        hideViews(listOf(binding.retryLayout))
        lifecycleScope.launchWhenCreated { countryViewModel.saveCountry(response.data) }
//        Handler(Looper.getMainLooper()).postDelayed({
//        }, 200)
        findNavController().navigate(R.id.action_getCountriesFragment_to_getStartedFragment)

    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.updateHorizontalStepViewPosition(0)
    }


}