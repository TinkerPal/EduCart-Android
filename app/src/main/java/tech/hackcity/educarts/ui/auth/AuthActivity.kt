package tech.hackcity.educarts.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.ActivityAuthBinding
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.hideToolBar
import tech.hackcity.educarts.uitls.showToolBar

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_EduCarts)
        setContentView(binding.root)

        //keep app on light mode only
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.createPersonalAccountFragment, R.id.loginFragment,
            )
        )

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.authNavHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        //Toolbar
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        setupDestination()

        sharedViewModel.fetchToolBarVisibility().observe(this) { visible ->
            if (visible) {
                showToolBar(binding.toolbar)
            } else {
                hideToolBar(binding.toolbar)

            }
        }
    }

    private fun setupDestination() {
        val navGraph = navController.navInflater.inflate(R.navigation.auth_nav_graph)

        val destination = intent.getStringExtra("destination")
        if (destination != null) {
            when (destination) {
                "login" -> {
                    navGraph.setStartDestination(R.id.loginFragment)
                }
                "create personal account" -> {
                    navGraph.setStartDestination(R.id.createPersonalAccountFragment)
                }

            }

            navController.graph = navGraph
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}