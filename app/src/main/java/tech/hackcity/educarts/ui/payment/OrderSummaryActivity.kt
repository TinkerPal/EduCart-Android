package tech.hackcity.educarts.ui.payment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.ActivityOrderSummaryBinding

class OrderSummaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderSummaryBinding

    private var service = ""
    private var amount = ""
    private var currency = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderSummaryBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_EduCarts)
        setContentView(binding.root)

        val toolBarTitle = intent.getStringExtra("title")
        service = intent.getStringExtra("service").toString()
        amount = intent.getStringExtra("amount").toString()
        currency = intent.getStringExtra("currency").toString()

        val toolbar = binding.toolbar
        toolbar.title = toolBarTitle
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        displayService()

        binding.proceedToPaymentBtn.setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putExtra("service", service)
            intent.putExtra("amount", "269,500")
            intent.putExtra("currency", "NGN")
            startActivity(intent)
        }

        binding.modifyOrder.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            onBackPressed()
        }

    }

    private fun displayService() {
        binding.service.text = service
        when(service) {
            "Application Fee" -> {binding.serviceImage.setImageResource(R.drawable.application_fee)}
            "SEVIS Fee" -> binding.serviceImage.setImageResource(R.drawable.sevis_fee)
            "Tuition Fee" -> {binding.serviceImage.setImageResource(R.drawable.tution_fee)}
            "Visa Fee" -> {binding.serviceImage.setImageResource(R.drawable.visa_fee)}
            "Credential Evaluation" -> {binding.serviceImage.setImageResource(R.drawable.credential_evaluation)}
            "Admission Docs Shipment" -> {binding.serviceImage.setImageResource(R.drawable.admission_docs_shipment)}
            "Consultation fees" -> {binding.serviceImage.setImageResource(R.drawable.app_icon_foreground)}
            else -> {binding.serviceImage.setImageResource(R.drawable.others)}
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}