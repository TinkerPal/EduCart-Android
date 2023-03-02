package tech.hackcity.educarts.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment, R.id.walletFragment,
                R.id.paymentFragment, R.id.supportFragment,
                R.id.accountFragment
            )
        )

        //Toolbar
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainNavHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        //bottom navigation bar
        binding.bottomNav.setupWithNavController(navController)
        binding.bottomNav.setOnItemReselectedListener {
            return@setOnItemReselectedListener
        }
        binding.bottomNav.itemIconTintList = null
    }

}