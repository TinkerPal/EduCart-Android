package tech.hackcity.educarts.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.DashboardRepository
import tech.hackcity.educarts.data.storage.UserInfoManager
import tech.hackcity.educarts.databinding.FragmentHomeBinding
import tech.hackcity.educarts.domain.model.history.OrderHistoryResponse
import tech.hackcity.educarts.domain.model.history.OrderHistoryResponseData
import tech.hackcity.educarts.domain.model.settings.ProfileResponse
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

/**
 *Created by Victor Loveday on 2/22/23
 */
class HomeFragment : Fragment(R.layout.fragment_home), DashboardListener {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var userInfoManager: UserInfoManager
    private lateinit var viewModel:HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val api = RetrofitInstance(requireContext())
        userInfoManager = UserInfoManager(requireContext())
        val repository = DashboardRepository(api, userInfoManager)
        val factory = HomeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        viewModel.listener = this

        Coroutines.onMainWithScope(viewModel.viewModelScope) {
            viewModel.fetchProfile()
            viewModel.fetchOrderHistory()
        }

        setupUserInfo()
        setupProfileCompletionBanner()


        binding.trackTV.setOnClickListener {
            startActivity(Intent(requireContext(), TrackOrderActivity::class.java))
        }

        binding.consultTV.setOnClickListener {
            startActivity(Intent(requireContext(), BrowserActivity::class.java))

//            val intent = Intent(requireContext(), SupportActivity::class.java)
//            intent.putExtra("destination", "consultation")
//            startActivity(intent)
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
        userInfoManager = UserInfoManager(requireContext())
        userInfoManager.fetchUserInfo().asLiveData().observe(viewLifecycleOwner) { user ->

            with(binding) {
                val profileUrl = "${Constants.EDU_CARTS_MEDIA_URL}${user.profilePhoto}"
                val requestOptions = RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()


                if (user.profilePhoto.isNullOrEmpty()) {
                    profilePhoto.setImageResource(R.drawable.default_profile)
                    Log.d("UserInfo", "profile : ${user.profilePhoto}")

                } else {
                    Glide.with(requireContext())
                        .load(profileUrl)
//                        .apply(requestOptions)
                        .into(profilePhoto)
                }

                greetingsAndNameTV.text = resources.getString(R.string.hello_, user.firstName)
                userIDTV.text = resources.getString(R.string.user_id, shortenString(user.id, 8))
            }
        }
    }

    private fun setupProfileCompletionBanner() {
        userInfoManager.fetchUserInfo().asLiveData().observe(viewLifecycleOwner) { user ->
            with(binding) {

                incompleteProfileCardView.visibility =
                    if (user.isProfileCompleted) {
                        View.GONE
                    } else {
                        val fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
                        incompleteProfileCardView.startAnimation(fadeIn)
                        View.VISIBLE
                    }
            }
        }
    }

    private fun setupNews() {
        val newsAdapter = NewsAdapter(requireContext())
        binding.educationalNewsRV.apply {
            adapter = newsAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        newsAdapter.setData(Constants.dummyNewsList)
    }

    private fun setupOrderHistory(data: List<OrderHistoryResponseData>) {
        if (data.isEmpty()) {
            binding.emptyHistoryLayout.visibility = View.VISIBLE
            binding.recentActivityRV.visibility = View.GONE
            binding.viewAllPayments.visibility = View.GONE

        } else {
            binding.viewAllPayments.visibility = View.VISIBLE
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
        binding.orderHistoryLoader.visibility = View.VISIBLE
    }

    override fun onFetchProfileRequestStarted() {

    }

    override fun onFetchOrderHistoryFailed(message: String) {
        context?.toast("$message")
        binding.orderHistoryLoader.visibility = View.GONE
    }

    override fun onFetchProfileFailed(message: String) {
        context?.toast(message)
    }

    override fun onFetchProfileRequestSuccessful(response: ProfileResponse) {

    }

    override fun onFetchOrderHistorySuccessful(response: OrderHistoryResponse) {
        binding.orderHistoryLoader.visibility = View.GONE

        val orderHistoryData = response.date ?: emptyList()
        setupOrderHistory(orderHistoryData)

        binding.viewAllPayments.setOnClickListener {
            val intent = Intent(requireContext(), AllPaymentActivity::class.java)
            intent.putExtra("allHistory", response)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        Coroutines.onMainWithScope(viewModel.viewModelScope) {
            viewModel.fetchProfile()
            viewModel.fetchOrderHistory()
        }
    }
}