package tech.hackcity.educarts.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentMyOrderBinding

/**
 *Created by Victor Loveday on 2/22/23
 */
class MyOrderFragment : Fragment(R.layout.fragment_my_order) {

    private lateinit var binding: FragmentMyOrderBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMyOrderBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
    }
}