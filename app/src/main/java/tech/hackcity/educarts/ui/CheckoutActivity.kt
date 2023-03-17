package tech.hackcity.educarts.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.ActivityCheckoutBinding

class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutBinding

    private var service = ""
    private var amount = ""
    private var currency = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_EduCarts)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        toolbar.title = "Checkout"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        service = intent.getStringExtra("service").toString()
        amount = intent.getStringExtra("amount").toString()
        currency = intent.getStringExtra("currency").toString()

        displayService()

        displayPaymentMethods()
    }

    private fun displayPaymentMethods() {
//        val animate = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom)
        binding.payWithCreditOrDebitBtn.text = "Pay $currency $amount"

        binding.cards.setOnClickListener {
            binding.payWithBankTransferLayout.visibility = View.GONE
            binding.payWithCardLayout.apply {
                visibility = View.VISIBLE
//                startAnimation(animate)
            }

            binding.payWithCreditOrDebitBtn.text = "Pay $currency $amount"
        }

        binding.bankTransfer.setOnClickListener {
            binding.payWithCardLayout.visibility = View.GONE

            binding.payWithBankTransferLayout.apply {
                visibility = View.VISIBLE
//                startAnimation(animate)
            }
        }
    }

    private fun displayService() {
        binding.title.text = service
        when(service) {
            "Application Fee" -> {binding.serviceImage.setImageResource(R.drawable.application_fee)}
            "SEVIS Fee" -> binding.serviceImage.setImageResource(R.drawable.sevis_fee)
            "Tuition Fee" -> {binding.serviceImage.setImageResource(R.drawable.tution_fee)}
            "Visa Fee" -> {binding.serviceImage.setImageResource(R.drawable.visa_fee)}
            "Credential Evaluation" -> {binding.serviceImage.setImageResource(R.drawable.credential_evaluation)}
            "Admission Docs Shipment" -> {binding.serviceImage.setImageResource(R.drawable.admission_docs_shipment)}
            else -> {binding.serviceImage.setImageResource(R.drawable.others)}
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}