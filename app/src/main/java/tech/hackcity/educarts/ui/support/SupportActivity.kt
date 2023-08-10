package tech.hackcity.educarts.ui.support

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.ActivitySupportBinding
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

class SupportActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySupportBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var sharedViewModel: SharedViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupportBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_EduCarts)
        setContentView(binding.root)

        //keep app on light mode only
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.supportFragment)
        )

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.supportNavHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        //Toolbar
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupDestination()
        showStepIndicatorIfRequired()

    }

    private fun showStepIndicatorIfRequired() {
        val animate1 = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom_slow)
        val animate2 = AnimationUtils.loadAnimation(this, R.anim.slide_out_right)

        sharedViewModel.getStepIndicator().observe(this) { step ->
            if (step[0] > 0) {
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.stepIndicator.apply {
                        if (step[2] == 1) {
                            setTextColor(ContextCompat.getColor(this@SupportActivity, R.color.text_light))
                        }
                        visibility = View.VISIBLE
                        startAnimation(animate1)
                        binding.stepIndicator.text = "${step[0]}/${step[1]}"
                    }
                }, 100)

            } else {
                binding.stepIndicator.apply {
                    visibility = View.INVISIBLE
                    startAnimation(animate2)
                }

            }
        }
    }


    private fun setupDestination() {
        val navGraph = navController.navInflater.inflate(R.navigation.support_nav_graph)

        val destination = intent.getStringExtra("destination")
        if (destination != null) {
            when (destination) {
                "consultation" -> {
                    navGraph.setStartDestination(R.id.consultationFragment1)
                }
                "live chat" -> {
                    navGraph.setStartDestination(R.id.liveChatFragment)
                }
            }

            navController.graph = navGraph
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}