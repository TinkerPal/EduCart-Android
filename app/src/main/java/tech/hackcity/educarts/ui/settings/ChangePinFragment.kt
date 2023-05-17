package tech.hackcity.educarts.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentChangePinBinding

/**
 *Created by Victor Loveday on 2/28/23
 */
class ChangePinFragment: Fragment(R.layout.fragment_change_pin) {

    private lateinit var binding: FragmentChangePinBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentChangePinBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setupNavigation()
    }

    private fun setupNavigation() {
        binding.forgotPinTxt.setOnClickListener {
            findNavController().navigate(R.id.action_changePinFragment_to_forgotPinFragment)
        }
    }
}