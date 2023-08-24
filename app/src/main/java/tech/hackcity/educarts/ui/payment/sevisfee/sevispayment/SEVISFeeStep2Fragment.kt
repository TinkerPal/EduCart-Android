package tech.hackcity.educarts.ui.payment.sevisfee.sevispayment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentSevisFeeStep2Binding
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

/**
 *Created by Victor Loveday on 8/3/23
 */
class SEVISFeeStep2Fragment: Fragment(R.layout.fragment_sevis_fee_step_2) {

    private lateinit var binding: FragmentSevisFeeStep2Binding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val args: SEVISFeeStep2FragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSevisFeeStep2Binding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.textGuide1.text = resources.getString(
            R.string.please_fill_the_following_form_as_it_is_in_your,
            args.formType
        )

        if (args.formType == resources.getString(R.string.ds_2019)) {
            binding.categoryTextInputLayout.visibility = View.VISIBLE
        }

        binding.nextBtn.setOnClickListener {
            val action = SEVISFeeStep2FragmentDirections.actionSevisFeeStep2FragmentToSevisFeeStep3Fragment(args.formType)
            findNavController().navigate(action)
        }

    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.updateHorizontalStepViewPosition(2)
        sharedViewModel.updateHorizontalStepViewVisibility(true)
    }
}