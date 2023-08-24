package tech.hackcity.educarts.ui.payment

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
import tech.hackcity.educarts.databinding.ActivityPaymentBinding
import tech.hackcity.educarts.ui.canvas.CustomLineView
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var customLineView: CustomLineView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_EduCarts)
        setContentView(binding.root)

        //keep app on light mode only
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.paymentFragment)
        )

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.paymentNavHostFragment) as NavHostFragment
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
        sharedViewModel.fetchHorizontalStepVisibility().observe(this) { isVisible ->
            if (isVisible) {
                binding.customLineView.visibility = View.VISIBLE
            } else {
                binding.customLineView.visibility = View.INVISIBLE
            }
        }

        customLineView = binding.customLineView
        sharedViewModel.fetchHorizontalStepViewPosition().observe(this) { position ->
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    customLineView.setPosition(position)
                }, 100
            )
        }
    }

    private fun setupDestination() {
        val navGraph = navController.navInflater.inflate(R.navigation.payment_nav_graph)

        val destination = "sevis fee"
//        val destination = intent.getStringExtra("destination")
        if (destination != null) {
            when(destination) {
                "sevis fee" -> {
                    navGraph.setStartDestination(R.id.sevisFeeFragment)
                }
                "application fee" -> {
                    navGraph.setStartDestination(R.id.applicationFeeDashboardFragment)
                }
                "credential evaluation" -> {
                    navGraph.setStartDestination(R.id.credentialEvaluationDashboardFragment)
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