package tech.hackcity.educarts.ui.intro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.ActivityOnBoardingBinding
import tech.hackcity.educarts.domain.model.OnBoarding
import tech.hackcity.educarts.ui.adapters.OnBoardingAdapter
import tech.hackcity.educarts.ui.auth.AuthActivity

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    var onBoardingAdapter: OnBoardingAdapter? = null
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    var position = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_EduCarts)
        setContentView(binding.root)

        //keep app on light mode only
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        tabLayout = binding.tabs
        setupSliders()
    }

    private fun setupSliders() {
        val onBoardingList: MutableList<OnBoarding> = ArrayList()
        onBoardingList.add(
            OnBoarding(
                resources.getString(R.string.welcome_to_educarts),
                resources.getString(R.string.educarts_delivers_fast_payment_to_schools),
                R.drawable.holding_card
            )
        )
        onBoardingList.add(
            OnBoarding(
                resources.getString(R.string.welcome_to_educarts),
                resources.getString(R.string.educarts_provide_reasonable_exchange_rates),
                R.drawable.holding_card
            )
        )
        onBoardingList.add(
            OnBoarding(
                resources.getString(R.string.welcome_to_educarts),
                resources.getString(R.string.educarts_provide_support_to_complex_student_transaction),
                R.drawable.holding_card
            )
        )

        setupIntroSliderViewPagerAdapter(onBoardingList)

        position = viewPager!!.currentItem

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                position = tab!!.position
                if (tab.position == onBoardingList.size - 1) {
                    binding.nextBtn.apply {
                        text = resources.getString(R.string.get_started)
                        setOnClickListener {
                            startActivity(Intent(this@OnBoardingActivity, GetStartedActivity::class.java))
                            finish()
                        }
                    }
                } else {
                    binding.nextBtn.apply {
                        text = resources.getString(R.string.next)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        binding.signInText.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            intent.putExtra("destination", "login")
            startActivity(intent)
            finish()
        }
    }

    private fun setupIntroSliderViewPagerAdapter(introSliderData: List<OnBoarding>) {
        viewPager = binding.onBoardingViewPager
        onBoardingAdapter = OnBoardingAdapter(this, introSliderData)
        viewPager!!.adapter = onBoardingAdapter
        tabLayout!!.setupWithViewPager(viewPager)
    }


}