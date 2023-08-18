package tech.hackcity.educarts.ui.settings.pin

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentForgotPinBinding

/**
 *Created by Victor Loveday on 2/28/23
 */
class ForgotPinFragment : Fragment(R.layout.fragment_forgot_pin) {

    private lateinit var binding: FragmentForgotPinBinding

    private var question1 = ""
    private var question2 = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentForgotPinBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setupDummySecurityQuestions()

        setupNavigation()
    }

    private fun setupNavigation() {
        binding.submitBtn.setOnClickListener {
            val action =
                ForgotPinFragmentDirections.actionForgotPinFragmentToOTPFragment(
                    "create personal account",
                    resources.getString(R.string.verification),
                    "",
                    0
                )
            findNavController().navigate(action)
        }
    }

    private fun setupDummySecurityQuestions() {
        val securityQuestions1 = arrayListOf("Your favourite childhood book is?")
        val securityQuestions2 = arrayListOf("What was your first pet’s name?")

        val arrayAdapter1 =
            ArrayAdapter(requireContext(), R.layout.security_questions_item, securityQuestions1)
        val arrayAdapter2 =
            ArrayAdapter(requireContext(), R.layout.security_questions_item, securityQuestions2)
        binding.question1.setAdapter(arrayAdapter1)
        binding.question2.setAdapter(arrayAdapter2)

        question1 = binding.question1.text.toString()
        question2 = binding.question2.text.toString()
    }

}