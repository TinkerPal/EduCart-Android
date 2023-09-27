package tech.hackcity.educarts.ui.payment.ordersummary

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.payment.OrderRepository
import tech.hackcity.educarts.databinding.ActivityOrderSummaryBinding
import tech.hackcity.educarts.domain.model.payment.OrderSummary
import tech.hackcity.educarts.domain.model.payment.OrderSummaryResponse
import tech.hackcity.educarts.ui.alerts.ToastType
import tech.hackcity.educarts.ui.payment.CheckoutActivity
import tech.hackcity.educarts.ui.payment.orderdetails.OrderDetailsViewModel
import tech.hackcity.educarts.ui.payment.orderdetails.OrderDetailsViewModelFactory
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.copyToClipboard
import tech.hackcity.educarts.uitls.toast

class OrderSummaryActivity : AppCompatActivity(), OrderSummaryListener {

    private lateinit var binding: ActivityOrderSummaryBinding

    private lateinit var orderType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderSummaryBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_EduCarts)
        setContentView(binding.root)

        //keep app on light mode only
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        orderType = intent.getStringExtra("orderType").toString()

        val toolbar = binding.toolbar
        toolbar.title = resources.getString(R.string.order_summary)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val api = RetrofitInstance(this)
        val repository = OrderRepository(api)
        val factory = OrderDetailsViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[OrderDetailsViewModel::class.java]
        viewModel.orderSummaryListener = this

        Coroutines.onMainWithScope(viewModel.viewModelScope) {
            orderType.let {
                viewModel.fetchOrderSummary(this, it)
            }
        }


        binding.modifyOrder.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            onBackPressed()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun displayOrderSummary(data: OrderSummary) {
        data.let {
            with(binding) {
                orderIDTV.text = it.order_id
                serviceTV.text = it.order_type
                chargesTV.text = "$ ${it.charges}"
                servicePriceTV.text = "$ ${it.total_in_dollars}"
                totalPriceTV.text = "$ ${it.total_in_dollars}"
                totalLocalPriceTV.text = "₦ ${it.total_in_naira}"
                exchangeRateTV.text = "$1 = ₦${it.rate}"

                serviceTV1.text = it.order_type
                serviceTV2.text = it.order_type
            }

            setCopyOrderIdListener(it.order_id)
        }
    }

    private fun setCopyOrderIdListener(orderId: String?) {
        binding.orderIDTV.setOnClickListener {
            copyToClipboard(this, resources.getString(R.string.copy), orderId)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onRequestStarted() {
        binding.loadingScreen.visibility = View.VISIBLE
    }

    override fun onRequestFailed(errorMessage: String) {
        binding.loadingScreen.visibility = View.GONE
        toast(description = errorMessage, toastType = ToastType.ERROR)
    }

    override fun onRequestSuccessful(response: OrderSummaryResponse) {
        binding.loadingScreen.visibility = View.GONE
        displayOrderSummary(response.data)

        binding.proceedToPaymentBtn.setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putExtra("oderSummary", response.data)
            startActivity(intent)
        }

    }
}