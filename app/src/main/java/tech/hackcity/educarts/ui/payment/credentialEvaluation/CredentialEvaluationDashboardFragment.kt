package tech.hackcity.educarts.ui.payment.credentialEvaluation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentCredentialEvaluationDashboardBinding

/**
 *Created by Victor Loveday on 5/6/23
 */
class CredentialEvaluationDashboardFragment :
    Fragment(R.layout.fragment_credential_evaluation_dashboard) {

    private lateinit var binding: FragmentCredentialEvaluationDashboardBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCredentialEvaluationDashboardBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.credentialsEvaluation.setOnClickListener {
            findNavController().navigate(
                R.id.action_credentialEvaluationDashboardFragment_to_credentialEvaluationFragment
            )
        }
    }
}