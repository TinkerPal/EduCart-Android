package tech.hackcity.educarts.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.DashboardRepository
import tech.hackcity.educarts.data.storage.UserInfoManager
import tech.hackcity.educarts.databinding.FragmentHomeBinding
import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.domain.model.history.OrderHistoryResponse
import tech.hackcity.educarts.domain.model.history.OrderHistoryResponseData
import tech.hackcity.educarts.ui.adapters.AllPaymentAdapter
import tech.hackcity.educarts.ui.adapters.NewsAdapter
import tech.hackcity.educarts.ui.browser.BrowserActivity
import tech.hackcity.educarts.ui.notifications.NotificationActivity
import tech.hackcity.educarts.ui.payment.AllPaymentActivity
import tech.hackcity.educarts.ui.payment.OrderDetailsActivity
import tech.hackcity.educarts.ui.payment.TrackOrderActivity
import tech.hackcity.educarts.ui.support.SupportActivity
import tech.hackcity.educarts.uitls.Constants
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.shortenString
import tech.hackcity.educarts.uitls.toast
import java.io.Serializable

/**
 *Created by Victor Loveday on 2/22/23
 */
class HomeFragment : Fragment(R.layout.fragment_home), DashboardListener {

    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

//        binding.closeExchangeRateCardView.setOnClickListener {
//            val slideOutTop = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out_top)
//            binding.exchangeRateCardView.startAnimation(slideOutTop)
//            binding.exchangeRateCardView.visibility = View.GONE
//            binding.banner.visibility = View.VISIBLE
//        }

        val api = RetrofitInstance(requireContext())
        val repository = DashboardRepository(api)
        val factory = HomeViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        viewModel.listener = this

        Coroutines.onMainWithScope(viewModel.viewModelScope) {
            viewModel.fetchOrderHistory()
        }

        setupUserInfo()

        binding.trackTV.setOnClickListener {
            startActivity(Intent(requireContext(), TrackOrderActivity::class.java))
        }

        binding.consultTV.setOnClickListener {

            startActivity(Intent(requireContext(), BrowserActivity::class.java))
        }

        binding.faqsTV.setOnClickListener {
            val intent = Intent(requireContext(), SupportActivity::class.java)
            intent.putExtra("destination", "faqs")
            startActivity(intent)
        }

        //This is only for presentation purpose.
        //Approach will change once real data from an endpoint is consumed
        setupNews()

        val menuHost: MenuHost = requireActivity() as MenuHost
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.notification -> {
                        startActivity(Intent(requireContext(), NotificationActivity::class.java))
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
            binding.userID.text = resources.getString(R.string.user_id, shortenString(user.id, 8))
        }
    }

    private fun setupNews() {
        val newsAdapter = NewsAdapter(requireContext())
        binding.educationalNewsRV.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        newsAdapter.setData(Constants.dummyNewsList)
    }

    private fun setupOrderHistory(data: List<OrderHistoryResponseData>) {
        if (data.isEmpty()) {
            binding.emptyHistoryLayout.visibility = View.VISIBLE
            binding.recentActivityRV.visibility = View.GONE

        }else {
            binding.recentActivityRV.visibility = View.VISIBLE
            val allPaymentAdapter = AllPaymentAdapter(requireContext())
            binding.recentActivityRV.apply {
                adapter = allPaymentAdapter
                layoutManager = LinearLayoutManager(requireContext())
                allPaymentAdapter.setData(data.take(4))
            }

            allPaymentAdapter.setOnItemClickListener { history ->
                val intent = Intent(requireContext(), OrderDetailsActivity::class.java)
                intent.putExtra("history", history)
                startActivity(intent)
            }
        }
    }

    override fun onFetchOrderHistoryStarted() {

    }

    override fun onFetchOrderHistoryFailed(message: List<ErrorMessage>) {
        context?.toast("$message")
    }

    override fun onFetchOrderHistorySuccessful(response: OrderHistoryResponse) {
        setupOrderHistory(response.date)

        binding.viewAllPayments.visibility = View.VISIBLE
        binding.viewAllPayments.setOnClickListener {
            val intent = Intent(requireContext(), AllPaymentActivity::class.java)
            intent.putExtra("allHistory", response)
            startActivity(intent)
        }
    }
}