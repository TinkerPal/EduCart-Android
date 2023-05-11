package tech.hackcity.educarts.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 *Created by Victor Loveday on 2/19/23
 */
class OnBoardingAdapter(list: ArrayList<Fragment>, fm: FragmentManager, lifCycle: Lifecycle): FragmentStateAdapter(fm, lifCycle) {

    private val fragmentList = list

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return  fragmentList[position]
    }
}