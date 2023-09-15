package tech.hackcity.educarts.ui.browser

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import android.webkit.WebChromeClient
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.ActivityBrowserBinding
import tech.hackcity.educarts.ui.payment.OrderSummaryActivity
import tech.hackcity.educarts.ui.viewmodels.InternetConnectivityViewModel
import tech.hackcity.educarts.uitls.showCustomInfoDialog

class BrowserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBrowserBinding

    private lateinit var webView: WebView
    private lateinit var internetViewModel: InternetConnectivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBrowserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //keep app on light mode only
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val toolbar = binding.toolbar
        toolbar.title = resources.getString(R.string.consultation)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        internetViewModel = ViewModelProvider(this)[InternetConnectivityViewModel::class.java]

        internetViewModel.isInternetConnected.observe(this, Observer { isConnected ->
            if (!isConnected) {
                binding.noInternetLayout.visibility = View.VISIBLE
                Glide.with(this).asGif().load(R.drawable.no_internet_).into(binding.imageView)
            } else {
                binding.noInternetLayout.visibility = View.GONE
            }
        })

        internetViewModel.checkInternetConnectivity()

        setupWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        webView = binding.webView
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://www.calendly-embed.com/inline-2/")

        val progressBar = binding.progressBar

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = View.GONE
            }
        }

        setupNavigation()
    }

    private fun setupNavigation() {
        val destination = intent.getStringExtra("destination")
        if (destination == "Consultation") {
            showCustomInfoDialog(
                this,
                resources.getString(R.string.hi_there),
                resources.getString(R.string.click_on_the_finish_button_at_the_bottom_once_you_re_done_scheduling_a_consultation),
                resources.getString(R.string.got_it)
            )
        }

        binding.actionBtn.setOnClickListener {
            when (destination) {
                "Consultation" -> {
                    val intent = Intent(this, OrderSummaryActivity::class.java)
                    intent.putExtra("service", "Consultation")
                    startActivity(intent)
                    finish()
                }

                else -> {
                    finish()
                }
            }
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}