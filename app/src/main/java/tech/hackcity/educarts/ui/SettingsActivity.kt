package tech.hackcity.educarts.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.ActivitySettingsBinding
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel


class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.accountFragment)
        )

        //Toolbar
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.accountNavHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupDestination()
    }

    private fun setupDestination() {
        val navGraph = navController.navInflater.inflate(R.navigation.settings_nav_graph)

        val destination = "Profile"
        if (destination != null) {
            when(destination) {
                "Profile" -> {
                    navGraph.setStartDestination(R.id.profileFragment)
                }
                "IDV" -> {
                    Toast.makeText(this, "IDV", Toast.LENGTH_SHORT).show()
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