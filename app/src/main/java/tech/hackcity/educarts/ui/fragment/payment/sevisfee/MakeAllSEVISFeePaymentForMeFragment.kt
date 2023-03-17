package tech.hackcity.educarts.ui.fragment.payment.sevisfee

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentMakeAllSevisFeePaymentForMeBinding
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 *Created by Victor Loveday on 3/13/23
 */
class MakeAllSEVISFeePaymentForMeFragment :
    Fragment(R.layout.fragment_make_all_sevis_fee_payment_for_me) {

    private lateinit var binding: FragmentMakeAllSevisFeePaymentForMeBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMakeAllSevisFeePaymentForMeBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.updateStepIndicator(1)

        setupDatePicker()
        setupFormStepNavigation()
    }

    private fun setupFormStepNavigation() {
        val animate = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_bottom_slow)

        binding.nextBtn1.setOnClickListener {
            binding.step1Layout.visibility = View.GONE
            Handler(Looper.getMainLooper()).postDelayed({
                binding.step2Layout.apply {
                    visibility = View.VISIBLE
                    startAnimation(animate)
                }
                sharedViewModel.updateStepIndicator(2)
            }, 100)
        }

        binding.nextBtn2.setOnClickListener {
            binding.step2Layout.visibility = View.GONE
            Handler(Looper.getMainLooper()).postDelayed({
                binding.step3Layout.apply {
                    visibility = View.VISIBLE
                    startAnimation(animate)
                }
                sharedViewModel.updateStepIndicator(3)
            }, 100)
        }

        binding.nextBtn3.setOnClickListener {
            findNavController().navigate(R.id.action_makeAllSEVISFeePaymentForMeFragment_to_orderSummaryFragment)
            sharedViewModel.updateStepIndicator(0)
        }

    }

    @SuppressLint("SimpleDateFormat")
    private fun setupDatePicker() {
        binding.dateOfBirth.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setPositiveButtonText("Apply")
                .build()
            datePicker.show(requireActivity().supportFragmentManager, "DatePicker")

            // Setting up the event for when ok is clicked
            datePicker.addOnPositiveButtonClickListener {
                // formatting date in dd-mm-yyyy format.
                val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
                val date = dateFormatter.format(Date(it))
            }

            // Setting up the event for when cancelled is clicked
            datePicker.addOnNegativeButtonClickListener {
            }
            // Setting up the event for when back button is pressed
            datePicker.addOnCancelListener {
            }

        }
    }

}