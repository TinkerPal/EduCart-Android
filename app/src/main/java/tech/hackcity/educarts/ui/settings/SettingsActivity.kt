package tech.hackcity.educarts.ui.settings

import android.os.Bundle
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
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.hideViews
import tech.hackcity.educarts.uitls.showViews


class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var sharedViewModel: SharedViewModel

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
    }

    private fun setupScreenLoader() {
        sharedViewModel.isScreenLoading().observe(this) {isScreenLoading ->
            if (isScreenLoading) {
                showViews(listOf(binding.loadingScreen))
            }else {
                hideViews(listOf(binding.loadingScreen))
            }
        }
    }


    private fun setupDestination() {
        val navGraph = navController.navInflater.inflate(R.navigation.settings_nav_graph)

        val destination = intent.getStringExtra("destination")
        if (destination != null) {
            when (destination) {
                "Profile" -> {
                    navGraph.setStartDestination(R.id.profileFragment)
                }
                "IDV" -> {
                    navGraph.setStartDestination(R.id.identityVerificationFragment)
                }
                "Password and PIN" -> {
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