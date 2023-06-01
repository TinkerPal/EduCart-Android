package tech.hackcity.educarts.ui.payment.applicationfee

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentProgramInformationBinding
import tech.hackcity.educarts.ui.payment.PaymentActivity

/**
 *Created by Victor Loveday on 6/1/23
 */
class ProgramInformationFragment: Fragment(R.layout.fragment_program_information) {

    private lateinit var binding: FragmentProgramInformationBinding

    private val args: ProgramInformationFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentProgramInformationBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        (activity as PaymentActivity).supportActionBar?.title = args.program.course

        binding.convertTV.setOnClickListener{
            findNavController().navigate(R.id.action_programInformationFragment_to_gradeEligibilityFragment)
        }

        binding.applyNowBtn.setOnClickListener {
            val action = ProgramInformationFragmentDirections.actionProgramInformationFragmentToSchoolFormApplicationLinkFragment(
                args.program.course,
                "https://hackcity.tech/",
                resources.getString(R.string.application_fee),
                args.program.university
            )
            findNavController().navigate(action)
        }
    }
}