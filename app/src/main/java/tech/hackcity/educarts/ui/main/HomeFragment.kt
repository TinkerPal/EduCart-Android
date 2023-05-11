package tech.hackcity.educarts.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentHomeBinding

/**
 *Created by Victor Loveday on 2/22/23
 */
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.closeExchangeRateCardView.setOnClickListener {
            val slideOutTop = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out_top)
            binding.exchangeRateCardView.startAnimation(slideOutTop)
            binding.exchangeRateCardView.visibility = View.GONE
            binding.banner.visibility = View.VISIBLE
        }


        val menuHost: MenuHost = requireActivity() as MenuHost

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.exchangeRate -> {
                        if (!binding.exchangeRateCardView.isVisible) {
                            val slideInTop =
                                AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_top)
                            binding.exchangeRateCardView.startAnimation(slideInTop)
                            binding.exchangeRateCardView.visibility = View.VISIBLE
                            binding.banner.visibility = View.GONE
                        }
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}