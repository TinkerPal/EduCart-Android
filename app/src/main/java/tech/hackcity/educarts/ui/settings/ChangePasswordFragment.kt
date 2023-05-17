package tech.hackcity.educarts.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentChangePasswordBinding

/**
 *Created by Victor Loveday on 2/28/23
 */
class ChangePasswordFragment: Fragment(R.layout.fragment_change_password) {

    private lateinit var binding: FragmentChangePasswordBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentChangePasswordBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setupNavigation()
    }

    private fun setupNavigation() {
        binding.updateBtn.setOnClickListener {
            activity?.finish()
        }
    }
}