package tech.hackcity.educarts.ui.fragment.auth

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentFirstOnBoardingBinding

/**
 *Created by Victor Loveday on 2/19/23
 */
class FirstOnBoardingFragment: Fragment(R.layout.fragment_first_on_boarding) {

    private lateinit var binding: FragmentFirstOnBoardingBinding
    var sharedPreferences: SharedPreferences? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentFirstOnBoardingBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

//        if (isOnBoardingFinished()) {
//            findNavController().navigate(R.id.action_onBoardingViewPagerFragment_to_getStartedFragment)
//        }

        val viewPager = activity?.findViewById<ViewPager2>(R.id.onBoardingViewPager)
        binding.nextBtn.setOnClickListener {
            viewPager?.currentItem = 1
        }
    }

//    private fun isOnBoardingFinished(): Boolean {
//        sharedPreferences = requireContext().getSharedPreferences("onBoardingPref", Context.MODE_PRIVATE)
//        return sharedPreferences!!.getBoolean("isOnBoardingFinished", false)
//    }
}