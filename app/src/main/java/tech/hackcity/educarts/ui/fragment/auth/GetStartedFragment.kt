package tech.hackcity.educarts.ui.fragment.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentGetStartedBinding

/**
 *Created by Victor Loveday on 2/19/23
 */
class GetStartedFragment: Fragment(R.layout.fragment_get_started) {

    private lateinit var binding: FragmentGetStartedBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentGetStartedBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

    }
}