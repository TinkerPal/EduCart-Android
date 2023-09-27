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
import tech.hackcity.educarts.ui.canvas.CustomLineView
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.hideViews
import tech.hackcity.educarts.uitls.showViews

class SupportActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySupportBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var customLineView: CustomLineView

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
        setupScreenLoader()

    }

    private fun setupScreenLoader() {
        sharedViewModel.isScreenLoading().observe(this) { screenLoader ->
            with(binding) {
                val (isScreenLoading, message) = screenLoader
                when (isScreenLoading) {
                    true -> {
                        showViews(listOf(loadingScreen, progressBar))
                        messageTV.text = message
                    }

                    false -> {
                        hideViews(listOf(loadingScreen, progressBar))
                    }
                }
            }
        }
    }

    private fun showStepIndicatorIfRequired() {
        customLineView = binding.customLineView
        sharedViewModel.fetchHorizontalStepViewPosition().observe(this) { position ->
            if (position > 0) {
                binding.customLineView.visibility = View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        customLineView.setPosition(position)
                    }, 100
                )

            } else {
                binding.customLineView.visibility = View.INVISIBLE
            }
        }
    }

    private fun setupDestination() {
        val navGraph = navController.navInflater.inflate(R.navigation.support_nav_graph)

        val destination = intent.getStringExtra("destination")
        if (destination != null) {
            when (destination) {
                "consultation" -> {
                    navGraph.setStartDestination(R.id.consultationReasonFragment)
                }
                "faqs" -> {
                    navGraph.setStartDestination(R.id.FAQsCategoryFragment)
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