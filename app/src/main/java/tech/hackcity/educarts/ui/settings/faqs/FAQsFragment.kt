package tech.hackcity.educarts.ui.settings.faqs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentFaqsBinding
import tech.hackcity.educarts.ui.adapters.FAQsAdapter
import tech.hackcity.educarts.uitls.Constants

/**
 *Created by Victor Loveday on 2/26/23
 */
class FAQsFragment : Fragment(R.layout.fragment_faqs) {
    private lateinit var binding: FragmentFaqsBinding

    private lateinit var faqsAdapter: FAQsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentFaqsBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        faqsAdapter = FAQsAdapter(requireContext())
        binding.faqsRV.apply {
            adapter = faqsAdapter
            layoutManager = LinearLayoutManager(requireContext())

            faqsAdapter.setData(Constants.dummyFAQ)
        }
    }
}