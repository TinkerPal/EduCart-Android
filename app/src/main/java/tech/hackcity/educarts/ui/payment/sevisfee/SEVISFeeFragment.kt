package tech.hackcity.educarts.ui.payment.sevisfee

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentSevisFeeBinding
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.toast

/**
 *Created by Victor Loveday on 3/13/23
 */
class SEVISFeeFragment : Fragment(R.layout.fragment_sevis_fee) {

    private lateinit var binding: FragmentSevisFeeBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var destination = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSevisFeeBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setDestination()
    }

    private fun setDestination() {
        binding.sevisPaymentBtn.setOnClickListener {
            destination = 1
            binding.sevisPaymentRadioBtn.isChecked = true
            binding.sevisCouponRadioBtn.isChecked = false
        }
        binding.sevisCouponBtn.setOnClickListener {
            destination = 2
            binding.sevisPaymentRadioBtn.isChecked = false
            binding.sevisCouponRadioBtn.isChecked = true
        }

        binding.nextBtn.setOnClickListener {
            when (destination) {
                1 -> {
                    val action = SEVISFeeFragmentDirections.actionSevisFeeFragmentToSEVISFormTypeFragment("sevisFeePayment")
                    findNavController().navigate(action)
                }
                2 -> {
                    val action = SEVISFeeFragmentDirections.actionSevisFeeFragmentToSEVISFormTypeFragment("sevisCoupon")
                    findNavController().navigate(action)
                }
                else -> context?.toast(resources.getString(R.string.select_a_service))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.updateHorizontalStepViewVisibility(false)
    }


}