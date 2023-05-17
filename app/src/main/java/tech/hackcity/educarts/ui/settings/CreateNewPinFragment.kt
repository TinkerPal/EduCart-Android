package tech.hackcity.educarts.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.model.TransactionPin
import tech.hackcity.educarts.databinding.FragmentCreateNewPinBinding

/**
 *Created by Victor Loveday on 2/27/23
 */
class CreateNewPinFragment: Fragment(R.layout.fragment_create_new_pin) {

    private lateinit var binding: FragmentCreateNewPinBinding
    var sharedPreferences: SharedPreferences? = null

    private var pin = ""
    private var question1 = ""
    private var answer1 = ""
    private var question2 = ""
    private var answer2 = ""
    private var isAnswer1Provided = false
    private var isAnswer2Provided = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCreateNewPinBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)



        binding.createPinBtn.setOnClickListener {
           createTransactionalPin()
        }

        validatePinInput()
    }

    private fun createTransactionalPin() {
        val transactionPin = TransactionPin(pin, question1, answer1, question2, answer2)
        saveTransactionalPinCreationStatus()
        activity?.finish()
    }



    private fun validatePinInput() {

        binding.confirmNewPinET.doOnTextChanged { text, start, before, count ->
            pin = binding.newPinET.text.toString().trim()
            val confirmPin = binding.confirmNewPinET.text.toString().trim()
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

        binding.newPinET.doOnTextChanged { text, start, before, count ->
            pin = binding.newPinET.text.toString().trim()
            val confirmPin = binding.confirmNewPinET.text.toString().trim()
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


    private fun disableCreatePinButton() {
        binding.createPinBtn.isEnabled = false
        binding.createPinBtn.setBackgroundResource(R.drawable.disabled_button)
    }

    private fun enableCreatePinButton() {
        binding.createPinBtn.isEnabled = true
        binding.createPinBtn.setBackgroundResource(R.drawable.primary_button)
    }

    private fun saveTransactionalPinCreationStatus() {
        sharedPreferences = requireContext().getSharedPreferences("transactionalPinCreationPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences!!.edit()
        editor.putBoolean("isTransactionalPinCreated", true)
        editor.apply()
    }

}