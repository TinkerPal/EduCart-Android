package tech.hackcity.educarts.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentSupportBinding
import tech.hackcity.educarts.ui.settings.SettingsActivity
import tech.hackcity.educarts.ui.support.SupportActivity

/**
 *Created by Victor Loveday on 2/22/23
 */
class SupportFragment : Fragment(R.layout.fragment_support) {

    private lateinit var binding: FragmentSupportBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSupportBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.consultationCV.setOnClickListener {
            val intent = Intent(requireContext(), SupportActivity::class.java)
            intent.putExtra("destination", "consultation")
            startActivity(intent)
        }

        binding.liveChatCV.setOnClickListener {
            val intent = Intent(requireContext(), SupportActivity::class.java)
            intent.putExtra("destination", "live chat")
            startActivity(intent)
        }

        binding.faqsTV.setOnClickListener {
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            intent.putExtra("destination", "FAQS")
            startActivity(intent)
        }
    }
}