package tech.hackcity.educarts.ui.payment.sevisfee

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentSevisFeeBinding
import tech.hackcity.educarts.databinding.FragmentSevisFormTypeBinding
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.toast

/**
 *Created by Victor Loveday on 3/13/23
 */
class SEVISFormTypeFragment : Fragment(R.layout.fragment_sevis_form_type) {

    private lateinit var binding: FragmentSevisFormTypeBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val args: SEVISFormTypeFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSevisFormTypeBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setDestination()
    }

    private fun setDestination() {
        var formType = ""

        binding.formI20Btn.setOnClickListener {
            formType = resources.getString(R.string.form_i_20)
            binding.formI20RadioBtn.isChecked = true
            binding.ds2019RadioBtn.isChecked = false
        }
        binding.ds2019Btn.setOnClickListener {
            formType = resources.getString(R.string.ds_2019)
            binding.formI20RadioBtn.isChecked = false
            binding.ds2019RadioBtn.isChecked = true
        }

        binding.nextBtn.setOnClickListener {
            if (formType.isNotEmpty()) {
                when (args.destination) {
                    "sevisFeePayment" -> {
                        val action =
                            SEVISFormTypeFragmentDirections
                                .actionSEVISFormTypeFragmentToSevisFeeStep1Fragment(
                                    formType
                                )
                        findNavController().navigate(action)
                    }

                    "sevisCoupon" -> {
                        val action =
                            SEVISFormTypeFragmentDirections
                                .actionSEVISFormTypeFragmentToSevisCouponFragment(
                                    formType
                                )
                        findNavController().navigate(action)
                    }

                    else -> context?.toast(resources.getString(R.string.select_a_service))
                }

            } else {
                context?.toast(resources.getString(R.string.select_a_form_type))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.updateHorizontalStepViewPosition(0)
    }

}