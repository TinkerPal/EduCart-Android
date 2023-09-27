package tech.hackcity.educarts.ui.payment.credentialEvaluation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentCredentialEvaluationRecipientInformationBinding
import tech.hackcity.educarts.ui.payment.ordersummary.OrderSummaryActivity
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

/**
 *Created by Victor Loveday on 5/6/23
 */
class CredentialEvaluationRecipientInformationFragment :
    Fragment(R.layout.fragment_credential_evaluation_recipient_information) {

    private lateinit var binding: FragmentCredentialEvaluationRecipientInformationBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCredentialEvaluationRecipientInformationBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.nextBtn.setOnClickListener {
            val intent = Intent(requireContext(), OrderSummaryActivity::class.java)
            intent.putExtra("title", resources.getString(R.string.credentials_evaluation))
            intent.putExtra("service", resources.getString(R.string.credentials_evaluation))
            startActivity(intent)
        }
    }

    override fun onResume() {
        sharedViewModel.updateStepIndicator(arrayOf(4, 4))
        super.onResume()
    }
}