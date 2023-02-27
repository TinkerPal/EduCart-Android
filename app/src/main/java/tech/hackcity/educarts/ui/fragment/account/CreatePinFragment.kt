package tech.hackcity.educarts.ui.fragment.account

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.View
import androidx.fragment.app.Fragment
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentCreatePinBinding

/**
 *Created by Victor Loveday on 2/27/23
 */
class CreatePinFragment: Fragment(R.layout.fragment_create_pin) {

    private lateinit var binding: FragmentCreatePinBinding
    var sharedPreferences: SharedPreferences? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCreatePinBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        if (!isIntroGuideSeen()) {
            binding.guideLayout.visibility = View.VISIBLE
        }else{
            binding.guideLayout.visibility = View.GONE
        }

        guideTexts()

        binding.getStartedBtn.setOnClickListener {
            binding.guideLayout.visibility = View.GONE
            hideIntroGuide()
        }
    }

    private fun guideTexts() {
        val guideTxt1 = SpannableStringBuilder("Your transaction PIN will be used to confirm all transactions made through our system. The security questions will be used to verify your identity in case you forget your transaction PIN.")
        val style = StyleSpan(Typeface.BOLD)
        guideTxt1.setSpan(style, 0, 20, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        binding.guideTxt1.text = guideTxt1
    }

    private fun hideIntroGuide() {
        sharedPreferences = requireContext().getSharedPreferences("createPinIntroGuidPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences!!.edit()
        editor.putBoolean("isIntroGuideSeen", true)
        editor.apply()

    }

    private fun isIntroGuideSeen(): Boolean {
        sharedPreferences = requireContext().getSharedPreferences("createPinIntroGuidPref", Context.MODE_PRIVATE)
        return sharedPreferences!!.getBoolean("isIntroGuideSeen", false)
    }

}