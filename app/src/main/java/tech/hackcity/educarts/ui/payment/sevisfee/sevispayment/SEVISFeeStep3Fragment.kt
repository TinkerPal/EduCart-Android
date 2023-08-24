package tech.hackcity.educarts.ui.payment.sevisfee.sevispayment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentSevisFeeStep3Binding
import tech.hackcity.educarts.ui.payment.OrderSummaryActivity
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

/**
 *Created by Victor Loveday on 8/3/23
 */
class SEVISFeeStep3Fragment: Fragment(R.layout.fragment_sevis_fee_step_3) {

    private lateinit var binding: FragmentSevisFeeStep3Binding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val args: SEVISFeeStep3FragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSevisFeeStep3Binding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.textGuide1.text = resources.getString(
            R.string.please_fill_the_following_form_as_it_is_in_your,
            args.formType
        )

        binding.nextBtn.setOnClickListener {
            val intent = Intent(requireContext(), OrderSummaryActivity::class.java)
            intent.putExtra("service", resources.getString(R.string.sevis_fee))
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.updateHorizontalStepViewPosition(3)
        sharedViewModel.updateHorizontalStepViewVisibility(true)
    }

}