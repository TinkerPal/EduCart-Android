package tech.hackcity.educarts.ui.auth

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
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.ActivityAuthBinding
import tech.hackcity.educarts.ui.canvas.CustomLineView
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.changeToolbarColor
import tech.hackcity.educarts.uitls.hideToolBar
import tech.hackcity.educarts.uitls.showToolBar

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var customLineView: CustomLineView

    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_EduCarts)
        setContentView(binding.root)

        //keep app on light mode only
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.getStartedFragment,
                R.id.createPersonalAccountFragment,
                R.id.loginFragment
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

        sharedViewModel.fetchToolBarVisibility().observe(this) { isVisible ->
            if (isVisible) {
                showToolBar(binding.toolbar)
            } else {
                hideToolBar(binding.toolbar)
            }
        }

        sharedViewModel.fetchToolbarColor().observe(this) { color ->
            changeToolbarColor(binding.toolbar, color)
        }

        sharedViewModel.fetchHorizontalStepVisibility().observe(this) { isVisible ->
            if (isVisible) {
                binding.customLineView.visibility = View.VISIBLE
            }else {
                binding.customLineView.visibility = View.INVISIBLE
            }
        }

        customLineView = binding.customLineView
        sharedViewModel.fetchHorizontalStepViewPosition().observe(this) { position ->
            Handler(Looper.getMainLooper()).postDelayed(
                { customLineView.setPosition(position) },
                100
            )
        }

    }

    private fun setupDestination() {
        val navGraph = navController.navInflater.inflate(R.navigation.auth_nav_graph)

        val destination = "get started"
//        val destination = intent.getStringExtra("destination")
        if (destination != null) {
            when (destination) {
                "get started" -> {
                    navGraph.setStartDestination(R.id.getStartedFragment)
                }

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