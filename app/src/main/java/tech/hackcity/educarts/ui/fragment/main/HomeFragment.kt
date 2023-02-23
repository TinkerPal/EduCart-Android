package tech.hackcity.educarts.ui.fragment.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentAccountBinding
import tech.hackcity.educarts.databinding.FragmentHomeBinding

/**
 *Created by Victor Loveday on 2/22/23
 */
class HomeFragment: Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
    }
}