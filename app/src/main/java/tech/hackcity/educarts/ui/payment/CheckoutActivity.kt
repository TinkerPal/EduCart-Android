package tech.hackcity.educarts.ui.payment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.ActivityCheckoutBinding
import tech.hackcity.educarts.domain.model.payment.OrderSummary

class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutBinding

    private lateinit var orderSummary: OrderSummary

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_EduCarts)
        setContentView(binding.root)

        //keep app on light mode only
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val toolbar = binding.toolbar
        toolbar.title = resources.getString(R.string.checkout)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        orderSummary = intent.getSerializableExtra("oderSummary") as OrderSummary

        displayServiceImages()
        displayPaymentMethods()
    }

    private fun displayPaymentMethods() {
        binding.payWithCreditOrDebitBtn.text =
            resources.getString(R.string.paying_amount, "$${orderSummary.total_in_dollars}")

        binding.debitOrCreditCard.setOnClickListener {
            binding.payWithBankTransferLayout.visibility = View.GONE
            binding.payWithCardLayout.visibility = View.VISIBLE
        }

        binding.bankTransfer.setOnClickListener {
            binding.payWithCardLayout.visibility = View.GONE
            binding.payWithBankTransferLayout.visibility = View.VISIBLE
        }
    }

    private fun displayServiceImages() {
        binding.serviceTV.text = orderSummary.order_type
        when (orderSummary.order_type) {
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
            "Consultation" -> {
                binding.serviceImage.setImageResource(R.drawable.consultation)
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