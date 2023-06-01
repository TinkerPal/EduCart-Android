package tech.hackcity.educarts.ui.payment.applicationfee

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentGradeEligibilityBinding

/**
 *Created by Victor Loveday on 6/1/23
 */
class GradeEligibilityFragment: Fragment(R.layout.fragment_grade_eligibility) {

    private lateinit var binding: FragmentGradeEligibilityBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentGradeEligibilityBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
    }
}