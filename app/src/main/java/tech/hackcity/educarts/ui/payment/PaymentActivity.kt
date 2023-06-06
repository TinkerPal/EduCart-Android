package tech.hackcity.educarts.ui.payment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.ActivityPaymentBinding
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var sharedViewModel: SharedViewModel

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
        val animate1 = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom_slow)
        val animate2 = AnimationUtils.loadAnimation(this, R.anim.slide_out_right)

        sharedViewModel.getStepIndicator().observe(this) { step ->
            if (step[0] > 0) {
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.stepIndicator.apply {
                        visibility = View.VISIBLE
                        startAnimation(animate1)
                        binding.stepIndicator.text = "${step[0]}/${step[1]}"
                    }
                }, 100)

            }else {
                binding.stepIndicator.apply {
                    visibility = View.INVISIBLE
                    startAnimation(animate2)
                }

            }
        }
    }

    private fun setupDestination() {
        val navGraph = navController.navInflater.inflate(R.navigation.payment_nav_graph)

        val destination = intent.getStringExtra("destination")
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