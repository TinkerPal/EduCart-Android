package tech.hackcity.educarts.ui.intro

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.repositories.intro.OnBoardingRepository
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.databinding.ActivityOnBoardingBinding
import tech.hackcity.educarts.domain.model.OnBoarding
import tech.hackcity.educarts.ui.adapters.OnBoardingAdapter
import tech.hackcity.educarts.ui.auth.AuthActivity
import tech.hackcity.educarts.uitls.animateButtonFadeIn

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    private lateinit var viewModel: OnBoardingViewModel
    var onBoardingAdapter: OnBoardingAdapter? = null
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    var next: Button? = null
    var position = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_EduCarts)
        setContentView(binding.root)

        //keep app on light mode only
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val sharePreferencesManager = SharePreferencesManager(this)
        val repository = OnBoardingRepository(sharePreferencesManager)
        val factory = OnBoardingViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[OnBoardingViewModel::class.java]

        if (viewModel.fetchIsGetStartedPressed() || viewModel.fetchLoginStatus()) {
            val intent = Intent(this, AuthActivity::class.java)
            intent.putExtra("destination", "login")
            startActivity(intent)
            finish()
        }


        tabLayout = binding.tabs
        next = binding.nextBtn
        binding.skipTextView.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            intent.putExtra("destination", "get countries")
            startActivity(intent)
            finish()
        }

        setupSliders()
    }

    private fun setupSliders() {
        val onBoardingList: MutableList<OnBoarding> = ArrayList()
        onBoardingList.add(
            OnBoarding(
                resources.getString(R.string.on_boarding_title_1),
                resources.getString(R.string.on_boarding_description_1),
                R.drawable.consulting
            )
        )
        onBoardingList.add(
            OnBoarding(
                resources.getString(R.string.on_boarding_title_2),
                resources.getString(R.string.on_boarding_description_2),
                R.drawable.college_campus
            )
        )
        onBoardingList.add(
            OnBoarding(
                resources.getString(R.string.on_boarding_title_3),
                resources.getString(R.string.on_boarding_description_3),
                R.drawable.plain_credit_card_amico
            )
        )

        setupIntroSliderViewPagerAdapter(onBoardingList)

        position = viewPager!!.currentItem

        next?.setOnClickListener {
            if (position < onBoardingList.size) {
                position++
                viewPager!!.currentItem = position
            }
            if (position == onBoardingList.size) {
                val intent = Intent(this, AuthActivity::class.java)
                intent.putExtra("destination", "get countries")
                startActivity(intent)
                finish()
            }
        }

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                position = tab!!.position
                if (tab.position == onBoardingList.size - 1) {
                    next!!.text = resources.getString(R.string.get_started)
                    binding.skipTextView.visibility = View.INVISIBLE
                    binding.nextBtn.animateButtonFadeIn()
                } else {
                    next!!.text = resources.getString(R.string.next)
                    binding.skipTextView.visibility = View.VISIBLE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

    }

    private fun setupIntroSliderViewPagerAdapter(introSliderData: List<OnBoarding>) {
        viewPager = binding.onBoardingViewPager
        onBoardingAdapter = OnBoardingAdapter(this, introSliderData)
        viewPager!!.adapter = onBoardingAdapter
        tabLayout!!.setupWithViewPager(viewPager)
    }


}