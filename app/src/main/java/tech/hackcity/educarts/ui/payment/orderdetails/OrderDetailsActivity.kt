package tech.hackcity.educarts.ui.payment.orderdetails

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.payment.OrderRepository
import tech.hackcity.educarts.databinding.ActivityOrderDetailsBinding
import tech.hackcity.educarts.domain.model.history.OrderDetails
import tech.hackcity.educarts.domain.model.history.OrderDetailsResponse
import tech.hackcity.educarts.domain.model.payment.OrderSummary
import tech.hackcity.educarts.ui.adapters.OrderDetailsAdapter
import tech.hackcity.educarts.ui.alerts.ToastType
import tech.hackcity.educarts.ui.payment.checkout.CheckoutActivity
import tech.hackcity.educarts.ui.payment.ordersummary.OrderSummaryActivity
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.copyToClipboard
import tech.hackcity.educarts.uitls.formatDateTime
import tech.hackcity.educarts.uitls.showViews
import tech.hackcity.educarts.uitls.toast


class OrderDetailsActivity : AppCompatActivity(), OrderDetailsListener {

    private lateinit var binding: ActivityOrderDetailsBinding

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_EduCarts)
        setContentView(binding.root)

        //keep app on light mode only
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val toolbar = binding.toolbar
        toolbar.title = resources.getString(R.string.order_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val api = RetrofitInstance(this)
        val repository = OrderRepository(api)
        val factory = OrderDetailsViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[OrderDetailsViewModel::class.java]
        viewModel.orderDetailsListener = this

        val orderId = intent.getStringExtra("orderId")
        Coroutines.onMainWithScope(viewModel.viewModelScope) {
            orderId?.let {
                viewModel.trackOrder(this, it)
            }
        }

        binding.rateUsTV.setOnClickListener {
            toast(
                title = resources.getString(R.string.coming_soon),
                description = resources.getString(R.string.keep_your_eyes_glue),
                toastType = ToastType.INFO
            )
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setupOrderDetails(data: List<OrderDetails> ) {
        val orderDetailsAdapter = OrderDetailsAdapter(this)
        setCopyUserIDListener(data[0].order_id)
        setupActionButtons(data[0])
        with(binding) {
            orderIdLayout.visibility = View.VISIBLE
            orderIDTV.text = data[0].order_id

            if (data[0].status == "Order Completed") {
                dateLayout.visibility = View.VISIBLE
                orderStatusTV.text = resources.getString(R.string.order_completed)
                dateTV.text = data[0].order_stage[3].date?.let { formatDateTime(it, showTime = false) }
            }else {
                orderStatusTV.text = resources.getString(R.string.processing_order)
            }

            orderDetailsRV.apply {
                adapter = orderDetailsAdapter
                layoutManager = LinearLayoutManager(this@OrderDetailsActivity)
                orderDetailsAdapter.setData(data[0].order_stage)
            }
        }
    }

    private fun setupActionButtons(orderDetails: OrderDetails) {
        when (orderDetails.status) {
            "Payment Pending" -> {
                showViews(listOf(binding.proceedToPaymentBtnLayout))
                binding.proceedToPaymentBtn.setOnClickListener {
                    val intent = Intent(this, OrderSummaryActivity::class.java)
                    intent.putExtra("orderType", orderDetails.order_type)
                    startActivity(intent)
                    Toast.makeText(this, orderDetails.order_type, Toast.LENGTH_SHORT).show()
                }
            }
            "Order Completed" -> { showViews(listOf(binding.downloadReceiptBtnLayout)) }
        }
    }

    private fun setCopyUserIDListener(orderID: String) {
        binding.orderIDTV.setOnClickListener {
            copyToClipboard(this, resources.getString(R.string.copy), orderID)
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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onRequestSuccessful(response: OrderDetailsResponse) {
        binding.loadingScreen.visibility = View.GONE
        setupOrderDetails(response.data)
    }

}