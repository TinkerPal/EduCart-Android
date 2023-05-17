package tech.hackcity.educarts.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.model.TransactionPin
import tech.hackcity.educarts.databinding.FragmentCreatePinBinding

/**
 *Created by Victor Loveday on 2/27/23
 */
class CreatePinFragment : Fragment(R.layout.fragment_create_pin) {

    private lateinit var binding: FragmentCreatePinBinding
    var sharedPreferences: SharedPreferences? = null

    private var pin = ""
    private var question1 = ""
    private var answer1 = ""
    private var question2 = ""
    private var answer2 = ""
    private var isAnswer1Provided = false
    private var isAnswer2Provided = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCreatePinBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        if (isIntroGuideSeen()) {
            binding.guideLayout.visibility = View.GONE
            binding.enter4DigitLayout.visibility = View.VISIBLE
        }

        guideTexts()

        binding.getStartedBtn.setOnClickListener {
            val slideInBottom =
                AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_bottom_slow)
            binding.guideLayout.visibility = View.GONE
            binding.enter4DigitLayout.apply {
                visibility = View.VISIBLE
                startAnimation(slideInBottom)
            }

            hideIntroGuide()
        }

        binding.createPinBtn.setOnClickListener {
            val slideInBottom =
                AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_bottom_slow)
            binding.enter4DigitLayout.visibility = View.GONE
            binding.securityQuestionsLayout.apply {
                visibility = View.VISIBLE
                startAnimation(slideInBottom)
            }
        }

        binding.submitBtn.setOnClickListener {
            createTransactionalPin()
        }

        validatePinInput()
        setupSecurityQuestions()
        validateSecurityAnswers()
    }

    private fun createTransactionalPin() {
        val transactionPin = TransactionPin(pin, question1, answer1, question2, answer2)
        saveTransactionalPinCreationStatus()
        activity?.finish()
    }

    private fun validateSecurityAnswers() {
        binding.answer1ET.doOnTextChanged { text, start, before, count ->
            answer1 = binding.answer1ET.text.toString().trim()
            if (answer1.isEmpty()) {
                isAnswer1Provided = false
                binding.answer1TextInputLayout.error = "Field can't be empty"
                disableSubmitButton()
            } else {
                isAnswer1Provided = true
                binding.answer1TextInputLayout.error = null
            }

            if (isAnswer1Provided && isAnswer2Provided) {
                enableSubmitButton()
            } else {
                disableSubmitButton()
            }
        }

        binding.answer2ET.doOnTextChanged { text, start, before, count ->
            answer2 = binding.answer2ET.text.toString().trim()
            if (answer2.isEmpty()) {
                isAnswer2Provided = false
                binding.answer2TextInputLayout.error = "Field can't be empty"
                disableSubmitButton()
            } else {
                isAnswer2Provided = true
                binding.answer2TextInputLayout.error = null
            }

            if (isAnswer1Provided && isAnswer2Provided) {
                enableSubmitButton()
            } else {
                disableSubmitButton()
            }
        }

    }

    private fun setupSecurityQuestions() {
        val securityQuestions1 = arrayListOf(
            "Your favourite childhood book is?",
            "What was your first pet’s name?",
            "The first concert you attended is?",
            "Your mother’s maiden name is?",
            "The name of your first teacher is?"
        )

        val securityQuestions2 = arrayListOf(
            "What was your first pet’s name?",
            "Your favourite childhood book is?",
            "The first concert you attended is?",
            "Your mother’s maiden name is?",
            "The name of your first teacher is?"
        )

        val arrayAdapter1 =
            ArrayAdapter(requireContext(), R.layout.security_questions_item, securityQuestions1)
        val arrayAdapter2 =
            ArrayAdapter(requireContext(), R.layout.security_questions_item, securityQuestions2)
        binding.question1.setAdapter(arrayAdapter1)
        binding.question2.setAdapter(arrayAdapter2)

        question1 = binding.question1.text.toString()
        question2 = binding.question2.text.toString()
    }

    private fun validatePinInput() {

        binding.confirmPinET.doOnTextChanged { text, start, before, count ->
            pin = binding.pinET.text.toString().trim()
            val confirmPin = binding.confirmPinET.text.toString().trim()
            if (pin != confirmPin) {
                binding.confirmPinTextInputLayout.error = "Pin does not match"
                disableCreatePinButton()
                if (pin.length < 4 || pin.length > 4 || confirmPin.length < 4 || confirmPin.length > 4) {
                    disableCreatePinButton()
                }
            } else {
                if (pin.length < 4 || pin.length > 4) {
                    disableCreatePinButton()
                } else {
                    enableCreatePinButton()
                }
                binding.confirmPinTextInputLayout.error = null
            }
        }

        binding.pinET.doOnTextChanged { text, start, before, count ->
            pin = binding.pinET.text.toString().trim()
            val confirmPin = binding.confirmPinET.text.toString().trim()
            if (pin != confirmPin) {
                binding.confirmPinTextInputLayout.error = "Pin does not match"
                disableCreatePinButton()
                if (pin.length < 4 || pin.length > 4 || confirmPin.length < 4 || confirmPin.length > 4) {
                    disableCreatePinButton()
                }
            } else {
                if (pin.length < 4 || pin.length > 4) {
                    disableCreatePinButton()
                } else {
                    enableCreatePinButton()
                }
                binding.confirmPinTextInputLayout.error = null
            }
        }
    }

    private fun guideTexts() {
        val guideTxt1 =
            SpannableStringBuilder("Your transaction PIN will be used to confirm all transactions made through our system. The security questions will be used to verify your identity in case you forget your transaction PIN.")
        val style = StyleSpan(Typeface.BOLD)
        guideTxt1.setSpan(style, 0, 20, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        binding.guideTxt1.text = guideTxt1
    }

    private fun disableCreatePinButton() {
        binding.createPinBtn.isEnabled = false
        binding.createPinBtn.setBackgroundResource(R.drawable.disabled_button)
    }

    private fun enableCreatePinButton() {
        binding.createPinBtn.isEnabled = true
        binding.createPinBtn.setBackgroundResource(R.drawable.primary_button)
    }

    private fun disableSubmitButton() {
        binding.submitBtn.isEnabled = false
        binding.submitBtn.setBackgroundResource(R.drawable.disabled_button)
    }

    private fun enableSubmitButton() {
        binding.submitBtn.isEnabled = true
        binding.submitBtn.setBackgroundResource(R.drawable.primary_button)
    }

    private fun hideIntroGuide() {
        sharedPreferences =
            requireContext().getSharedPreferences("createPinIntroGuidPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences!!.edit()
        editor.putBoolean("isIntroGuideSeen", true)
        editor.apply()

    }

    private fun isIntroGuideSeen(): Boolean {
        sharedPreferences =
            requireContext().getSharedPreferences("createPinIntroGuidPref", Context.MODE_PRIVATE)
        return sharedPreferences!!.getBoolean("isIntroGuideSeen", false)
    }

    private fun saveTransactionalPinCreationStatus() {
        sharedPreferences = requireContext().getSharedPreferences(
            "transactionalPinCreationPref",
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences!!.edit()
        editor.putBoolean("isTransactionalPinCreated", true)
        editor.apply()
    }

}