package tech.hackcity.educarts.ui.fragment.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentAccountBinding
import tech.hackcity.educarts.databinding.FragmentWalletBinding

/**
 *Created by Victor Loveday on 2/22/23
 */
class WalletFragment: Fragment(R.layout.fragment_wallet) {

    private lateinit var binding: FragmentWalletBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentWalletBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
    }
}