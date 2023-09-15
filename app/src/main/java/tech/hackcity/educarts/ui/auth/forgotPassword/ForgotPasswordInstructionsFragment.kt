package tech.hackcity.educarts.ui.auth.forgotPassword

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentForgotPasswordInstructionsBinding
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

/**
 *Created by Victor Loveday on 8/3/23
 */
class ForgotPasswordInstructionsFragment : Fragment(R.layout.fragment_forgot_password_instructions) {

    private lateinit var binding: FragmentForgotPasswordInstructionsBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val args: ForgotPasswordInstructionsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentForgotPasswordInstructionsBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.nextBtn.setOnClickListener {
            val action =
                ForgotPasswordInstructionsFragmentDirections.actionForgotPasswordInstructionsFragmentToOTPFragment(
                    "reset password",
                    resources.getString(R.string.verification),
                    resources.getString(R.string.reset_instructions_has_been_sent_to, args.email),
                    2
                )
            findNavController().navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.setToolBarColor(ContextCompat.getColor(requireContext(), R.color.background_001))
        sharedViewModel.updateHorizontalStepViewPosition(1)
    }

}