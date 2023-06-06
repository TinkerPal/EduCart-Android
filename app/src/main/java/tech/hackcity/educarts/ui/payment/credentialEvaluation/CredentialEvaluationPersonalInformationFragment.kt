package tech.hackcity.educarts.ui.payment.credentialEvaluation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentCredentialEvaluationPersonalInformationBinding
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

/**
 *Created by Victor Loveday on 5/6/23
 */
class CredentialEvaluationPersonalInformationFragment :
    Fragment(R.layout.fragment_credential_evaluation_personal_information) {

    private lateinit var binding: FragmentCredentialEvaluationPersonalInformationBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCredentialEvaluationPersonalInformationBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.nextBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_credentialEvaluationPersonalInformationFragment_to_credentialEvaluationEducationalBackgroundFragment
            )
        }
    }

    override fun onResume() {
        sharedViewModel.updateStepIndicator(arrayOf(2, 4))
        super.onResume()
    }

}