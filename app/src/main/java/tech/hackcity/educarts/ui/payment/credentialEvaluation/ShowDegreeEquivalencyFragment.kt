package tech.hackcity.educarts.ui.payment.credentialEvaluation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentCheckDegreeEquivalencyBinding
import tech.hackcity.educarts.databinding.FragmentShowDegreeEquivalencyBinding
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

/**
 *Created by Victor Loveday on 6/6/23
 */
class ShowDegreeEquivalencyFragment: Fragment(R.layout.fragment_show_degree_equivalency) {

    private lateinit var binding: FragmentShowDegreeEquivalencyBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentShowDegreeEquivalencyBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.startEvaluationNowBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_showDegreeEquivalencyFragment_to_credentialEvaluationFragment
            )
        }

        binding.evaluationDashboardBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_showDegreeEquivalencyFragment_to_credentialEvaluationDashboardFragment
            )
        }
    }

    override fun onResume() {
        sharedViewModel.updateStepIndicator(arrayOf(0, 4))
        super.onResume()
    }
}