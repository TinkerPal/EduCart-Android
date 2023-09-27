package tech.hackcity.educarts.ui.payment.orderdetails

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.ActivityTrackOrderBinding
import tech.hackcity.educarts.ui.alerts.ToastType
import tech.hackcity.educarts.uitls.toast

class TrackOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTrackOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackOrderBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_EduCarts)
        setContentView(binding.root)

        //keep app on light mode only
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val toolbar = binding.toolbar
        toolbar.title = resources.getString(R.string.track_order)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.trackOrderBtn.setOnClickListener {
            val orderId = binding.orderIDET.text.toString().trim()

            if (orderId.isEmpty()) {
                toast(description = resources.getString(R.string.field_can_not_be_empty), toastType = ToastType.ERROR)
                return@setOnClickListener
            }

            val intent = Intent(this, OrderDetailsActivity::class.java)
            intent.putExtra("orderId", orderId)
            startActivity(intent)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}