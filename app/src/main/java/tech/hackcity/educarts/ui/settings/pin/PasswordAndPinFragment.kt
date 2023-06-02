package tech.hackcity.educarts.ui.settings.pin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentPasswordAndPinBinding

/**
 *Created by Victor Loveday on 2/28/23
 */
class PasswordAndPinFragment : Fragment(R.layout.fragment_password_and_pin) {

    private lateinit var binding: FragmentPasswordAndPinBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentPasswordAndPinBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.changePassword.setOnClickListener {
            findNavController().navigate(R.id.action_passwordAndPinFragment_to_changePasswordFragment)
        }
    }

}