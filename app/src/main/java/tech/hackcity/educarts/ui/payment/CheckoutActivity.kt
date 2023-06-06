package tech.hackcity.educarts.ui.payment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
        toolbar.title = resources.getString(R.string.checkout)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        service = intent.getStringExtra("service").toString()
        amount = intent.getStringExtra("amount").toString()
        currency = intent.getStringExtra("currency").toString()

        displayService()
        displayPaymentMethods()
    }

    private fun displayPaymentMethods() {
        binding.payWithCreditOrDebitBtn.text =
            resources.getString(R.string.paying_amount, currency, amount)

        binding.cards.setOnClickListener {
            binding.payWithBankTransferLayout.visibility = View.GONE
            binding.payWithCardLayout.apply {
                visibility = View.VISIBLE
            }

            binding.payWithCreditOrDebitBtn.text =
                resources.getString(R.string.paying_amount, currency, amount)
        }

        binding.bankTransfer.setOnClickListener {
            binding.payWithCardLayout.visibility = View.GONE
            binding.payWithBankTransferLayout.visibility = View.VISIBLE
        }
    }

    private fun displayService() {
        binding.title.text = service
        when (service) {
            "Application fee" -> {
                binding.serviceImage.setImageResource(R.drawable.application_fee)
            }
            "Application review" -> {
                binding.serviceImage.setImageResource(R.drawable.application_fee)
            }
            "SEVIS fee" -> binding.serviceImage.setImageResource(R.drawable.sevis_fee)

            "Tuition fee" -> {
                binding.serviceImage.setImageResource(R.drawable.tution_fee)
            }
            "Visa fee" -> {
                binding.serviceImage.setImageResource(R.drawable.visa_fee)
            }
            "Credentials evaluation" -> {
                binding.serviceImage.setImageResource(R.drawable.credential_evaluation)
            }
            "Admission docs shipment" -> {
                binding.serviceImage.setImageResource(R.drawable.admission_docs_shipment)
            }
            "Consultation fees" -> {
                binding.serviceImage.setImageResource(R.drawable.app_icon_foreground)
            }
            else -> {
                binding.serviceImage.setImageResource(R.drawable.others)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}