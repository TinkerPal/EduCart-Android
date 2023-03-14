package tech.hackcity.educarts.ui.fragment.payment.sevisfee

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentSevisFeeBinding
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

/**
 *Created by Victor Loveday on 3/13/23
 */
class SEVISFeeFragment: Fragment(R.layout.fragment_sevis_fee) {

    private lateinit var binding: FragmentSevisFeeBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var destination = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSevisFeeBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setDestination()
    }

    private fun setDestination() {
        binding.carryAllSevisFeeForMe.setOnClickListener {
            destination = 1
        }
        binding.iHaveSevisPaymentCoupon.setOnClickListener {
            destination = 2
        }

        binding.getStartedBtn.setOnClickListener {
            when(destination) {
                1 -> {
                    binding.progressBar.visibility = View.VISIBLE
                    findNavController().navigate(R.id.action_sevisFeeFragment_to_makeAllSEVISFeePaymentForMeFragment)
                }
                2 -> Toast.makeText(requireContext(), "Coming soon", Toast.LENGTH_SHORT).show()

                else -> Toast.makeText(requireContext(), "Select a service", Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.updateStepIndicator(0)
    }

}