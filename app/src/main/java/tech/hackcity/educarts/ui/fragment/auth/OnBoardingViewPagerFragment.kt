package tech.hackcity.educarts.ui.fragment.auth

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.adapters.OnBoardingAdapter
import tech.hackcity.educarts.databinding.FragmentOnBoardingViewPagerBinding

class OnBoardingViewPagerFragment : Fragment(R.layout.fragment_on_boarding_view_pager) {

    private lateinit var binding: FragmentOnBoardingViewPagerBinding
    var sharedPreferences: SharedPreferences? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentOnBoardingViewPagerBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        if (isOnBoardingFinished()) {
            findNavController().navigate(R.id.action_onBoardingViewPagerFragment_to_getStartedFragment)
        }

        val fragmentList = arrayListOf<Fragment>(
            FirstOnBoardingFragment(),
            SecondOnBoardingFragment(),
            ThirdOnBoardingFragment()
        )

        val adapter = OnBoardingAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)

        binding.onBoardingViewPager.adapter = adapter
    }

    private fun isOnBoardingFinished(): Boolean {
        sharedPreferences = requireContext().getSharedPreferences("onBoardingPref", Context.MODE_PRIVATE)
        return sharedPreferences!!.getBoolean("isOnBoardingFinished", false)
    }

}