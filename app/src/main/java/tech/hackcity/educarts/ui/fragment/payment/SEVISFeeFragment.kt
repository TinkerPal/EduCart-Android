package tech.hackcity.educarts.ui.fragment.payment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentSevisFeeBinding

/**
 *Created by Victor Loveday on 3/13/23
 */
class SEVISFeeFragment: Fragment(R.layout.fragment_sevis_fee) {

    private lateinit var binding: FragmentSevisFeeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSevisFeeBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
    }
}