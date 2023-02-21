package tech.hackcity.educarts.ui

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
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

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var sharedViewModel: SharedViewModel

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize viewModel(s)
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.onBoardingViewPagerFragment, R.id.getStartedFragment,
                R.id.createPersonalAccountFragment, R.id.loginFragment,
            )
        )

        //Toolbar
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.authNavHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupToolbar()
    }

    private fun setupToolbar() {
        val slideInTop = AnimationUtils.loadAnimation(this, R.anim.slide_in_top)
        val slideOutTop = AnimationUtils.loadAnimation(this, R.anim.slide_out_top)

        sharedViewModel.isToolbarVisible().observe(this) {isToolbarVisible ->
            if (!isToolbarVisible) {
                binding.toolbar.visibility = View.GONE
                binding.toolbar.startAnimation(slideOutTop)
            }else {
                binding.toolbar.visibility = View.VISIBLE
                binding.toolbar.startAnimation(slideInTop)
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}