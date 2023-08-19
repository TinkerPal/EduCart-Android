package tech.hackcity.educarts.ui.support.consultation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentConsultation3Binding

/**
 *Created by Victor Loveday on 5/17/23
 */
class ConsultationFragment3 : Fragment(R.layout.fragment_consultation_3) {

    private lateinit var binding: FragmentConsultation3Binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentConsultation3Binding.bind(view)
        super.onViewCreated(view, savedInstanceState)
    }
}