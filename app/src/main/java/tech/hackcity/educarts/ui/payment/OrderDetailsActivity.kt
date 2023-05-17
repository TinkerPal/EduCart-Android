package tech.hackcity.educarts.ui.payment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.ActivityOrderDetailsBinding
import tech.hackcity.educarts.uitls.showViews


class OrderDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_EduCarts)
        setContentView(binding.root)

        //keep app on light mode only
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val toolbar = binding.toolbar
        toolbar.title = resources.getString(R.string.track_order)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val currentStage = 4 // this is hardcoded temporarily for demonstration
        setupVerticalStepper(currentStage)


        if (currentStage == 4) { // this is hardcoded temporarily for demonstration
            showViews(
                listOf(
                    binding.downloadReceiptBtn,
                    binding.progressBar,
                    binding.rateUsTV
                )
            )

        }

    }

    private fun setupVerticalStepper(step: Int) {
        val imageIndicators = listOf(binding.step1, binding.step2, binding.step3, binding.step4)

        for (i in imageIndicators) {
            i.setImageResource(R.drawable.gray_circle)
        }

        for (i in 1..step) {
            imageIndicators[i - 1].setImageResource(R.drawable.done_with_circle_border)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}