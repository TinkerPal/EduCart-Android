package tech.hackcity.educarts.ui.payment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.ActivityAllPaymentBinding
import tech.hackcity.educarts.ui.adapters.AllPaymentAdapter
import tech.hackcity.educarts.uitls.Constants

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

        //This is only for presentation purpose.
        //Approach will change once real data from an endpoint is consumed
        val allPaymentAdapter = AllPaymentAdapter(this)
        binding.allPaymentActivitiesRV.apply {
            adapter = allPaymentAdapter
            layoutManager = LinearLayoutManager(this@AllPaymentActivity)
            allPaymentAdapter.setData(Constants.dummyTransactionList)
        }

        allPaymentAdapter.setOnItemClickListener {

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}