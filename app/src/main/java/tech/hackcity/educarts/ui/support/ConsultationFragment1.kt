package tech.hackcity.educarts.ui.support

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentConsultation1Binding
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.Constants

/**
 *Created by Victor Loveday on 5/17/23
 */
class ConsultationFragment1 : Fragment(R.layout.fragment_consultation_1) {

    private lateinit var binding: FragmentConsultation1Binding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var question = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentConsultation1Binding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.updateStepIndicator(arrayOf(1, 2, 1))

        setupConsultationFAQs()

        binding.nextBtn.setOnClickListener {
            findNavController().navigate(R.id.action_consultationFragment1_to_consultationFragment2)
        }
    }

    private fun setupConsultationFAQs() {
        val arrayAdapter1 =
            ArrayAdapter(
                requireContext(),
                R.layout.security_questions_item,
                Constants.dummyConsultationFAQs
            )
        binding.question.setAdapter(arrayAdapter1)
        question = binding.question.text.toString()
    }

}