package tech.hackcity.educarts.ui.support.faqs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentFaqsBinding
import tech.hackcity.educarts.ui.adapters.FAQsAdapter
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.Constants

/**
 *Created by Victor Loveday on 2/26/23
 */
class FAQsFragment : Fragment(R.layout.fragment_faqs) {

    private lateinit var binding: FragmentFaqsBinding

    private val args: FAQsFragmentArgs by navArgs()
    private lateinit var faqsAdapter: FAQsAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentFaqsBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.bookConsultationBtn.setOnClickListener {
            findNavController().navigate(R.id.action_FAQsFragment_to_consultationReasonFragment)
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        faqsAdapter = FAQsAdapter(requireContext())
        binding.faqsRV.apply {
            adapter = faqsAdapter
            layoutManager = LinearLayoutManager(requireContext())

            faqsAdapter.setData(args.faqs.data)
        }
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.updateStepIndicator(arrayOf(0, 0, 0))
    }

}