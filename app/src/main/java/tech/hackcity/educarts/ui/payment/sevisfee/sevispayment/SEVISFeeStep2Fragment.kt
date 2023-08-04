package tech.hackcity.educarts.ui.payment.sevisfee.sevispayment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentSevisFeeStep2Binding
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

/**
 *Created by Victor Loveday on 8/3/23
 */
class SEVISFeeStep2Fragment: Fragment(R.layout.fragment_sevis_fee_step_2) {

    private lateinit var binding: FragmentSevisFeeStep2Binding

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSevisFeeStep2Binding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.nextBtn.setOnClickListener {
            findNavController().navigate(R.id.action_sevisFeeStep2Fragment_to_sevisFeeStep3Fragment)
        }

    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.updateStepIndicator(arrayOf(2,3))
    }

}