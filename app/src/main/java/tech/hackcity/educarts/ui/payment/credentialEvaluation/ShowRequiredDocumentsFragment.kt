package tech.hackcity.educarts.ui.payment.credentialEvaluation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentCheckRequiredDocumentsBinding
import tech.hackcity.educarts.databinding.FragmentShowRequiredDocumentsBinding
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

/**
 *Created by Victor Loveday on 5/6/23
 */
class ShowRequiredDocumentsFragment : Fragment(R.layout.fragment_show_required_documents) {

    private lateinit var binding: FragmentShowRequiredDocumentsBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentShowRequiredDocumentsBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.startEvaluationNowBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_showRequiredDocumentsFragment_to_credentialEvaluationFragment
            )
        }

        binding.evaluationDashboardBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_showRequiredDocumentsFragment_to_credentialEvaluationDashboardFragment
            )
        }
    }

    override fun onResume() {
        sharedViewModel.updateStepIndicator(arrayOf(0, 4))
        super.onResume()
    }

}