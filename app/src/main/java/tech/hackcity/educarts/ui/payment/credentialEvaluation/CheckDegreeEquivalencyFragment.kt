package tech.hackcity.educarts.ui.payment.credentialEvaluation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentCheckDegreeEquivalencyBinding

/**
 *Created by Victor Loveday on 6/6/23
 */
class CheckDegreeEquivalencyFragment: Fragment(R.layout.fragment_check_degree_equivalency) {

    private lateinit var binding: FragmentCheckDegreeEquivalencyBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCheckDegreeEquivalencyBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.checkNowBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_checkDegreeEquivalencyFragment_to_showDegreeEquivalencyFragment
            )
        }
    }
}