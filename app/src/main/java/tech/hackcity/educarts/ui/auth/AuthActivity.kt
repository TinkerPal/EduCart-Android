package tech.hackcity.educarts.ui.auth

import android.content.Intent
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
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.auth.AuthRepository
import tech.hackcity.educarts.data.storage.SessionManager
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.data.storage.UserInfoManager
import tech.hackcity.educarts.databinding.ActivityAuthBinding
import tech.hackcity.educarts.ui.canvas.CustomLineView
import tech.hackcity.educarts.ui.main.MainActivity
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.changeToolbarColor
import tech.hackcity.educarts.uitls.hideToolBar
import tech.hackcity.educarts.uitls.hideViews
import tech.hackcity.educarts.uitls.showToolBar
import tech.hackcity.educarts.uitls.showViews

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var customLineView: CustomLineView

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_EduCarts)
        setContentView(binding.root)

        //keep app on light mode only
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val api = RetrofitInstance(this)
        val sessionManager = SessionManager(this)
        val sharePreferencesManager = SharePreferencesManager(this)
        val userInfoManager = UserInfoManager(this)
        val repository = AuthRepository(api, sessionManager, sharePreferencesManager, userInfoManager)
        val factory = AuthViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        if (viewModel.fetchLoginStatus()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

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
        showStepIndicatorIfRequired()
        setupScreenLoader()

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
        sharedViewModel.isScreenLoading().observe(this) {isScreenLoading ->
            if (isScreenLoading) {
                showViews(listOf(binding.loadingScreen))
            }else {
                hideViews(listOf(binding.loadingScreen))
            }
        }
    }

    private fun setupDestination() {
        val navGraph = navController.navInflater.inflate(R.navigation.auth_nav_graph)

        val destination = intent.getStringExtra("destination")
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