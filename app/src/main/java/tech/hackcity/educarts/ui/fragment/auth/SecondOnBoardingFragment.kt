package tech.hackcity.educarts.ui.fragment.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentSecondOnBoardingBinding

/**
 *Created by Victor Loveday on 2/19/23
 */
class SecondOnBoardingFragment: Fragment(R.layout.fragment_second_on_boarding) {

    private lateinit var binding: FragmentSecondOnBoardingBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSecondOnBoardingBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.onBoardingViewPager)
        binding.nextBtn.setOnClickListener {
            viewPager?.currentItem = 2
        }

        //navigate to log in fragment
        binding.signInText.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingViewPagerFragment_to_loginFragment)
        }
    }
}