package tech.hackcity.educarts.ui.fragment.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentAccountBinding
import tech.hackcity.educarts.databinding.FragmentSupportBinding

/**
 *Created by Victor Loveday on 2/22/23
 */
class SupportFragment: Fragment(R.layout.fragment_support) {

    private lateinit var binding: FragmentSupportBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSupportBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
    }
}