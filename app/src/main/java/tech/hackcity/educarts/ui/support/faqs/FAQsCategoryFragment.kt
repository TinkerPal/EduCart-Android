package tech.hackcity.educarts.ui.support.faqs

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.support.SupportRepository
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.databinding.FragmentFaqCategoryBinding
import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.domain.model.support.FaqCategory
import tech.hackcity.educarts.domain.model.support.FaqsResponse
import tech.hackcity.educarts.ui.adapters.FAQsCategoryAdapter
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.showViews
import tech.hackcity.educarts.uitls.startShimmerLoader
import tech.hackcity.educarts.uitls.stopShimmerLoader

/**
 *Created by Victor Loveday on 2/26/23
 */
class FAQsCategoryFragment : Fragment(R.layout.fragment_faq_category), FaqListener {
    private lateinit var binding: FragmentFaqCategoryBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentFaqCategoryBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val api = RetrofitInstance(requireContext())
        val sharePreferencesManager = SharePreferencesManager(requireContext())
        val repository = SupportRepository(api, sharePreferencesManager)
        val factory = FaqViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[FaqViewModel::class.java]
        viewModel.listener = this

        Coroutines.onMainWithScope(viewModel.viewModelScope) {
            viewModel.fetchFAQs()
        }

    }

    private fun setupRecyclerView(faqs: List<FaqCategory>) {
        val faqsAdapter = FAQsCategoryAdapter(requireContext())
        binding.faqsRV.apply {
            adapter = faqsAdapter
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

            faqsAdapter.setData(faqs)
        }

        faqsAdapter.setOnItemClickListener {
            val action = FAQsCategoryFragmentDirections.actionFAQsCategoryFragmentToFAQsFragment(it)
            findNavController().navigate(action)
        }
    }

    override fun onRequestStarted() {
//        startShimmerLoader(binding.shimmerLayout)
        binding.faqsProgressBar.visibility = View.VISIBLE
    }

    override fun onRequestFailed(message: String) {
//        stopShimmerLoader(binding.shimmerLayout)
        binding.faqsProgressBar.visibility = View.GONE
        Toast.makeText(requireContext(), "$message", Toast.LENGTH_SHORT).show()
    }

    override fun onRequestSuccessful(response: FaqsResponse) {
//        stopShimmerLoader(binding.shimmerLayout)
        binding.faqsProgressBar.visibility = View.GONE
        showViews(listOf(binding.faqsRV))
        setupRecyclerView(response.data)
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.updateStepIndicator(arrayOf(0, 0, 0))
    }

}