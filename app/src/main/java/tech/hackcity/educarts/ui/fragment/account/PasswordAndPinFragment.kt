package tech.hackcity.educarts.ui.fragment.account

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentPasswordAndPinBinding

/**
 *Created by Victor Loveday on 2/28/23
 */
class PasswordAndPinFragment: Fragment(R.layout.fragment_password_and_pin) {

    private lateinit var binding: FragmentPasswordAndPinBinding
    var sharedPreferences: SharedPreferences? = null

    private var isTransactionalPinCreated = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentPasswordAndPinBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        isTransactionalPinCreated = retrieveTransactionalPinCreationStatus()

        setupNavigation()
    }

    private fun setupNavigation() {
        if (isTransactionalPinCreated) {
            binding.heading.text = "Change PIN"
            binding.description.text = "Change your transactional pin"
        }

        binding.changePassword.setOnClickListener {
            findNavController().navigate(R.id.action_passwordAndPinFragment_to_changePasswordFragment)
        }
        binding.createOrChangePin.setOnClickListener {
            if (isTransactionalPinCreated) {
                findNavController().navigate(R.id.action_passwordAndPinFragment_to_changePinFragment)
            }else {
                findNavController().navigate(R.id.action_passwordAndPinFragment_to_createPinFragment)
            }
        }
    }


    private fun retrieveTransactionalPinCreationStatus(): Boolean {
        sharedPreferences = requireContext().getSharedPreferences("transactionalPinCreationPref", Context.MODE_PRIVATE)
        return sharedPreferences!!.getBoolean("isTransactionalPinCreated", false)
    }

}