package tech.hackcity.educarts.ui.settings

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.ActivitySettingsBinding
import tech.hackcity.educarts.ui.canvas.CustomLineView
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.hideViews
import tech.hackcity.educarts.uitls.showViews


class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var customLineView: CustomLineView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_EduCarts)
        setContentView(binding.root)

        //keep app on light mode only
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.accountFragment)
        )

        //Toolbar
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.accountNavHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        val toolbar = binding.toolbar
        toolbar.title = ""
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupDestination()
        setupScreenLoader()
        showStepIndicatorIfRequired()
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

    private fun setupDestination() {
        val navGraph = navController.navInflater.inflate(R.navigation.settings_nav_graph)

        val destination = intent.getStringExtra("destination")
        if (destination != null) {
            when (destination) {
                "profile" -> {
                    navGraph.setStartDestination(R.id.profileFragment)
                }
                "edit_profile" -> {
                    navGraph.setStartDestination(R.id.editProfileFragment)
                }
                "IDV" -> {
                    navGraph.setStartDestination(R.id.identityVerificationFragmentStep1)
                }
                "password_and_PIN" -> {
                    navGraph.setStartDestination(R.id.passwordAndPinFragment)
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