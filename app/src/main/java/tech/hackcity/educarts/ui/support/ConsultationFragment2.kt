package tech.hackcity.educarts.ui.support

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentConsultation2Binding
import tech.hackcity.educarts.ui.payment.OrderSummaryActivity
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

/**
 *Created by Victor Loveday on 5/17/23
 */
class ConsultationFragment2 : Fragment(R.layout.fragment_consultation_2) {

    private lateinit var binding: FragmentConsultation2Binding

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentConsultation2Binding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.updateStepIndicator(arrayOf(2, 2))

        binding.nextBtn.setOnClickListener {
            val intent = Intent(requireContext(), OrderSummaryActivity::class.java)
            intent.putExtra("title", resources.getString(R.string.consultation_fees))
            intent.putExtra("service", resources.getString(R.string.consultation_fees))
            startActivity(intent)
        }

        binding.callTypeRG.setOnCheckedChangeListener { group, selectedTab ->
            when (selectedTab) {
                R.id.phoneCallRadioButton -> {
                    binding.phoneCallRadioButton.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.checked_cirlcle,
                        0,
                        0,
                        0
                    )
                    binding.zoomCallRadioButton.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.unchecked_circle,
                        0,
                        0,
                        0
                    )
                }
                R.id.zoomCallRadioButton -> {
                    binding.phoneCallRadioButton.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.unchecked_circle,
                        0,
                        0,
                        0
                    )
                    binding.zoomCallRadioButton.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.checked_cirlcle,
                        0,
                        0,
                        0
                    )
                }
            }

        }
        binding.timeTypeRG.setOnCheckedChangeListener { group, selectedTab ->
            when (selectedTab) {
                R.id.nowRadioButton -> {
                    binding.nowRadioButton.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.checked_cirlcle,
                        0,
                        0,
                        0
                    )
                    binding.laterRadioButton.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.unchecked_circle,
                        0,
                        0,
                        0
                    )
                }
                R.id.laterRadioButton -> {
                    binding.nowRadioButton.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.unchecked_circle,
                        0,
                        0,
                        0
                    )
                    binding.laterRadioButton.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.checked_cirlcle,
                        0,
                        0,
                        0
                    )
                }
            }

        }


    }
}