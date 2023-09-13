package tech.hackcity.educarts.ui.main.support

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentSupportBinding
import tech.hackcity.educarts.ui.browser.BrowserActivity
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

        binding.bookConsultationBtn.setOnClickListener {
//            startActivity(Intent(requireContext(), BrowserActivity::class.java))

            val intent = Intent(requireContext(), SupportActivity::class.java)
            intent.putExtra("destination", "consultation")
            startActivity(intent)
        }

        binding.faqsBtn.setOnClickListener {
            val intent = Intent(requireContext(), SupportActivity::class.java)
            intent.putExtra("destination", "faqs")
            startActivity(intent)
        }
    }
}