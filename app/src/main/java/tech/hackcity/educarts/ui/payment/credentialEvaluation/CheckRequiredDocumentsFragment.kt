package tech.hackcity.educarts.ui.payment.credentialEvaluation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentCheckRequiredDocumentsBinding
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

/**
 *Created by Victor Loveday on 5/6/23
 */
class CheckRequiredDocumentsFragment : Fragment(R.layout.fragment_check_required_documents) {

    private lateinit var binding: FragmentCheckRequiredDocumentsBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCheckRequiredDocumentsBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)


        binding.checkNowBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_checkRequiredDocumentsFragment_to_showRequiredDocumentsFragment
            )
        }
    }

    override fun onResume() {
        sharedViewModel.updateStepIndicator(arrayOf(0, 4))
        super.onResume()
    }

}