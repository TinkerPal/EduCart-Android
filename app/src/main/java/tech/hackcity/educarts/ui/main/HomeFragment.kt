package tech.hackcity.educarts.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.storage.SessionManager
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.data.storage.UserInfoManager
import tech.hackcity.educarts.databinding.FragmentHomeBinding
import tech.hackcity.educarts.ui.adapters.AllPaymentAdapter
import tech.hackcity.educarts.ui.payment.AllPaymentActivity
import tech.hackcity.educarts.ui.payment.OrderDetailsActivity
import tech.hackcity.educarts.ui.payment.TrackOrderActivity
import tech.hackcity.educarts.ui.settings.SettingsActivity
import tech.hackcity.educarts.ui.support.SupportActivity
import tech.hackcity.educarts.uitls.Constants

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

        setupUserInfo()

        binding.trackOrderTV.setOnClickListener {
            startActivity(Intent(requireContext(), TrackOrderActivity::class.java))
        }

        binding.consultationTV.setOnClickListener {
            val intent = Intent(requireContext(), SupportActivity::class.java)
            intent.putExtra("destination", "consultation")
            startActivity(intent)
        }

        binding.faqsTV.setOnClickListener {
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            intent.putExtra("destination", "FAQS")
            startActivity(intent)
        }

        binding.viewAllPayments.setOnClickListener {
            startActivity(Intent(requireContext(), AllPaymentActivity::class.java))
        }

        //This is only for presentation purpose.
        //Approach will change once real data from an endpoint is consumed
        val allPaymentAdapter = AllPaymentAdapter(requireContext())
        binding.recentActivityRV.apply {
            adapter = allPaymentAdapter
            layoutManager = LinearLayoutManager(requireContext())
            allPaymentAdapter.setData(Constants.dummyTransactionList.take(4))
        }

        allPaymentAdapter.setOnItemClickListener {
            startActivity(Intent(requireContext(), OrderDetailsActivity::class.java))
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

    private fun setupUserInfo() {
        val userInfoManager = UserInfoManager(requireContext())

        userInfoManager.fetchUserInfo().asLiveData().observe(viewLifecycleOwner) { user ->
            binding.greetingsAndNameTextView.text = resources.getString(R.string.hello_, user.firstName)
        }
    }
}