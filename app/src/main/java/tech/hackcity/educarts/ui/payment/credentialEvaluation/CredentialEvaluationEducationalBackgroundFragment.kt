package tech.hackcity.educarts.ui.payment.credentialEvaluation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentCredentialEvaluationEducationalBackgroundBinding
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

/**
 *Created by Victor Loveday on 5/6/23
 */
class CredentialEvaluationEducationalBackgroundFragment :
    Fragment(R.layout.fragment_credential_evaluation_educational_background) {

    private lateinit var binding: FragmentCredentialEvaluationEducationalBackgroundBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCredentialEvaluationEducationalBackgroundBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.nextBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_credentialEvaluationEducationalBackgroundFragment_to_credentialEvaluationRecipientInformationFragment
            )
        }
    }

    override fun onResume() {
        sharedViewModel.updateStepIndicator(arrayOf(3, 4))
        super.onResume()
    }
}