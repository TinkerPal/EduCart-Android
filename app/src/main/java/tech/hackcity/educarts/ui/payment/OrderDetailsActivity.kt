package tech.hackcity.educarts.ui.payment

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.ActivityOrderDetailsBinding
import tech.hackcity.educarts.domain.model.history.OrderDetails
import tech.hackcity.educarts.domain.model.history.OrderHistoryResponseData
import tech.hackcity.educarts.ui.adapters.OrderDetailsAdapter
import tech.hackcity.educarts.ui.alerts.ToastType
import tech.hackcity.educarts.uitls.copyToClipboard
import tech.hackcity.educarts.uitls.formatDateTime
import tech.hackcity.educarts.uitls.showViews
import tech.hackcity.educarts.uitls.toast


class OrderDetailsActivity : AppCompatActivity() {

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

        setupOrderDetails()

        binding.rateUsTV.setOnClickListener {
            toast(
                title = resources.getString(R.string.coming_soon),
                description = resources.getString(R.string.keep_your_eyes_glue),
                toastType = ToastType.INFO
            )
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setupOrderDetails() {
        val orderDetailsAdapter = OrderDetailsAdapter(this)
        val orderDetails = intent.getSerializableExtra("history") as? OrderHistoryResponseData
        orderDetails?.let {
            setCopyUserIDListener(it.order_id)
            showRateAndReceiptButton(it.status)

            val data = listOf(
                OrderDetails(it.amount, it.date, it.id, it.order_id, it.order_type, it.status, it.user, "${it.order_type} ${resources.getString(R.string.payment_pending)}", "${it.order_type} ${resources.getString(R.string.payment_is_yet_to_be_received)}", 1),
                OrderDetails(it.amount, it.date, it.id, it.order_id, it.order_type, it.status, it.user, "${it.order_type} ${resources.getString(R.string.payment_pending)}", "${it.order_type} ${resources.getString(R.string.payment_is_yet_to_be_received)}", 2),
                OrderDetails(it.amount, it.date, it.id, it.order_id, it.order_type, it.status, it.user, "${it.order_type} ${resources.getString(R.string.payment_pending)}", "${it.order_type} ${resources.getString(R.string.payment_is_yet_to_be_received)}", 3),
                OrderDetails(it.amount, it.date, it.id, it.order_id, it.order_type, it.status, it.user, "${it.order_type} ${resources.getString(R.string.payment_pending)}", "${it.order_type} ${resources.getString(R.string.payment_is_yet_to_be_received)}", 4)
            )

            with(binding) {
                orderIDTV.text = it.order_id
                dateTV.text = formatDateTime(it.date, showTime = false)

                orderDetailsRV.apply {
                    adapter = orderDetailsAdapter
                    layoutManager = LinearLayoutManager(this@OrderDetailsActivity)
                    orderDetailsAdapter.setData(data)
                }
            }
        }

    }

    private fun showRateAndReceiptButton(status: String) {
        if (status == "Order completed") {
            showViews(
                listOf(
                    binding.downloadReceiptBtn,
                    binding.progressBar,
                    binding.rateUsTV
                )
            )

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

}