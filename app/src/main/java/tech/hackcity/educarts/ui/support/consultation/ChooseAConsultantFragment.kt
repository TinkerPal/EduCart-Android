package tech.hackcity.educarts.ui.support.consultation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.support.SupportRepository
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.databinding.FragmentChooseAConsultantBinding
import tech.hackcity.educarts.domain.model.support.Consultant
import tech.hackcity.educarts.domain.model.support.ConsultantsResponse
import tech.hackcity.educarts.ui.adapters.ConsultantsAdapter
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.toast

/**
 *Created by Victor Loveday on 9/11/23
 */
class ChooseAConsultantFragment: Fragment(R.layout.fragment_choose_a_consultant), ConsultantsListener {

    private lateinit var binging: FragmentChooseAConsultantBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binging = FragmentChooseAConsultantBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val api = RetrofitInstance(requireContext())
        val sharePreferencesManager = SharePreferencesManager(requireContext())
        val repository = SupportRepository(api, sharePreferencesManager)
        val factory = ConsultationViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[ConsultationViewModel::class.java]
        viewModel.consultantsListener = this

        lifecycleScope.launchWhenCreated {
            viewModel.fetchConsultants()
        }

    }

    private fun setupConsultants(consultants: List<Consultant>) {
        val consultantsAdapter = ConsultantsAdapter()
        binging.consultantRV.apply {
            adapter = consultantsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            consultantsAdapter.setData(consultants)

            consultantsAdapter.setOnItemClickListener {
                val action = ChooseAConsultantFragmentDirections.actionChooseAConsultantFragmentToConsultantProfileFragment(it.id)
                findNavController().navigate(action)
            }

        }
    }

    override fun onFetchConsultantsRequestStarted() {
        sharedViewModel.updateLoadingScreen(true)
    }

    override fun onRequestFailed(message: String) {
        context?.toast(message)
        sharedViewModel.updateLoadingScreen(false)
    }

    override fun onFetchConsultantsRequestSuccessful(response: ConsultantsResponse) {
        sharedViewModel.updateLoadingScreen(false)
        setupConsultants(response.data)
    }
}