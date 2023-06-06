package tech.hackcity.educarts.ui.payment.credentialEvaluation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentCredentialEvaluationBinding
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

/**
 *Created by Victor Loveday on 5/6/23
 */
class CredentialEvaluationFragment : Fragment(R.layout.fragment_credential_evaluation) {

    private lateinit var binding: FragmentCredentialEvaluationBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCredentialEvaluationBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.radioGroup.setOnCheckedChangeListener { group, selectedTab ->
            when (selectedTab) {
                R.id.yesRadioButton -> {
                    binding.yesRadioButton.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.checked_cirlcle,
                        0,
                        0,
                        0
                    )
                    binding.noRadioButton.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.unchecked_circle,
                        0,
                        0,
                        0
                    )
                }
                R.id.noRadioButton -> {
                    binding.yesRadioButton.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.unchecked_circle,
                        0,
                        0,
                        0
                    )
                    binding.noRadioButton.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.checked_cirlcle,
                        0,
                        0,
                        0
                    )
                }
            }

        }


        binding.nextBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_credentialEvaluationFragment_to_credentialEvaluationPersonalInformationFragment
            )
        }
    }

    override fun onResume() {
        sharedViewModel.updateStepIndicator(arrayOf(1, 4))
        super.onResume()
    }
}