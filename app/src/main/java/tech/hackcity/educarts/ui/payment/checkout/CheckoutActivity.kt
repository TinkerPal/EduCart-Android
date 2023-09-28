package tech.hackcity.educarts.ui.payment.checkout

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.payment.CheckoutRepository
import tech.hackcity.educarts.databinding.ActivityCheckoutBinding
import tech.hackcity.educarts.domain.model.payment.OrderSummary
import tech.hackcity.educarts.domain.model.payment.paymentreceipt.TransferReceiptResponse
import tech.hackcity.educarts.ui.alerts.ToastType
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.createFilePart
import tech.hackcity.educarts.uitls.disablePrimaryButtonState
import tech.hackcity.educarts.uitls.enablePrimaryButtonState
import tech.hackcity.educarts.uitls.getFileNameFromUri
import tech.hackcity.educarts.uitls.hideButtonLoadingState
import tech.hackcity.educarts.uitls.showButtonLoadingState
import tech.hackcity.educarts.uitls.toast

class CheckoutActivity : AppCompatActivity(), CheckoutListener {

    private lateinit var binding: ActivityCheckoutBinding

    private lateinit var orderSummary: OrderSummary
    private var receiptUri: Uri? = null
    private lateinit var sharedViewModel: SharedViewModel

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

        val api = RetrofitInstance(this)
        val repository = CheckoutRepository(api)
        val factory = CheckoutViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[CheckoutViewModel::class.java]
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        viewModel.listener = this

        orderSummary = intent.getSerializableExtra("oderSummary") as OrderSummary

        binding.serviceTV.text = orderSummary.order_type
        displayPaymentMethods()

        binding.pickReceiptBtn.setOnClickListener { openFilePickerForReceipt() }

        binding.confirmPaymentBtn.setOnClickListener {
            val receipt = createFilePart(this, "receipt", receiptUri)
            Coroutines.onMainWithScope(viewModel.viewModelScope) {
                viewModel.uploadPaymentReceipt(this, receipt, orderSummary.order_id)
            }
        }
    }

    private fun displayPaymentMethods() {
        binding.payWithCardBtn.text =
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

    private fun checkPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                toast(description = resources.getString(R.string.permission_granted_you_can_now_pick_files), toastType = ToastType.ERROR)
            } else {
                toast(description = resources.getString(R.string.permission_denied_you_can_not_access_files), toastType = ToastType.ERROR)
            }
        }
    }

    private fun openFilePickerForReceipt() {
        if (checkPermission()) {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type =
                "application/pdf|application/msword|application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            intent.addCategory(Intent.CATEGORY_OPENABLE)

            val mimeTypes = arrayOf(
                "application/pdf",
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            )
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)

            startActivityForResult(intent, PERMISSION_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PERMISSION_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            uri?.let {
                handleSelectedReceiptFile(it)
            }
        }
    }

    private fun handleSelectedReceiptFile(uri: Uri) {
        receiptUri = uri
        binding.receiptGuide.text = getFileNameFromUri(this, uri)
        binding.pickReceiptBtn.setBackgroundResource(R.drawable.file_selected_bg)
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 123
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onUploadReceiptStarted() {
        sharedViewModel.updateScreenLoader(Pair(true, ""))
        showButtonLoadingState(binding.confirmPaymentBtn, binding.progressBar, "")
        disablePrimaryButtonState(binding.confirmPaymentBtn)
    }

    override fun onUploadReceiptFailed(errorMessage: String) {
        toast(description = errorMessage, toastType = ToastType.ERROR)
        hideButtonLoadingState(binding.confirmPaymentBtn, binding.progressBar, resources.getString(R.string.confirm_payment))
        enablePrimaryButtonState(binding.confirmPaymentBtn)
        sharedViewModel.updateScreenLoader(Pair(false, ""))
    }

    override fun onUploadReceiptSuccessful(response: TransferReceiptResponse) {
        toast(description = response.message, toastType = ToastType.SUCCESS)
        hideButtonLoadingState(binding.confirmPaymentBtn, binding.progressBar, resources.getString(R.string.confirm_payment))
        enablePrimaryButtonState(binding.confirmPaymentBtn)
        sharedViewModel.updateScreenLoader(Pair(false, ""))
    }
}