package tech.hackcity.educarts.ui.auth

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentThirdOnBoardingBinding

/**
 *Created by Victor Loveday on 2/19/23
 */
class ThirdOnBoardingFragment: Fragment(R.layout.fragment_third_on_boarding) {

    private lateinit var binding: FragmentThirdOnBoardingBinding
    var sharedPreferences: SharedPreferences? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentThirdOnBoardingBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.getStarted.setOnClickListener {
            finishOnBoarding()
            findNavController().navigate(R.id.action_onBoardingViewPagerFragment_to_getStartedFragment)
        }

        //navigate to log in fragment
        binding.signInText.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingViewPagerFragment_to_loginFragment)
        }
    }

    private fun finishOnBoarding() {
        sharedPreferences = requireContext().getSharedPreferences("onBoardingPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences!!.edit()
        editor.putBoolean("isOnBoardingFinished", true)
        editor.apply()
    }
}