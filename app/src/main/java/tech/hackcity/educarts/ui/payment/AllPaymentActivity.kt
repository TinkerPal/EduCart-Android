package tech.hackcity.educarts.ui.payment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.ActivityAllPaymentBinding
import tech.hackcity.educarts.domain.model.history.OrderHistoryResponse
import tech.hackcity.educarts.ui.adapters.AllPaymentAdapter

class AllPaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllPaymentBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_EduCarts)
        setContentView(binding.root)

        //keep app on light mode only
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val toolbar = binding.toolbar
        toolbar.title = resources.getString(R.string.payment_activities)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupOrderHistory()
    }

    private fun setupOrderHistory() {
        val allHistory = intent.getSerializableExtra("allHistory") as OrderHistoryResponse

        val allPaymentAdapter = AllPaymentAdapter(this)
        binding.allPaymentActivitiesRV.apply {
            adapter = allPaymentAdapter
            layoutManager = LinearLayoutManager(this@AllPaymentActivity)
            allHistory.data?.let { allPaymentAdapter.setData(it) }
        }

        allPaymentAdapter.setOnItemClickListener { history->
            val intent = Intent(this, OrderDetailsActivity::class.java)
            intent.putExtra("history", history)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}