package tech.hackcity.educarts.ui.payment.sevisfee.seviscoupon

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.payment.SEVISFeeRepository
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.databinding.FragmentHaveSevisPaymentCouponBinding
import tech.hackcity.educarts.domain.model.payment.sevis.SevisCouponStep1Response
import tech.hackcity.educarts.ui.alerts.ToastType
import tech.hackcity.educarts.ui.payment.sevisfee.sevispayment.SEVISFeeStep1Fragment
import tech.hackcity.educarts.ui.payment.sevisfee.sevispayment.SEVISFeeViewModel
import tech.hackcity.educarts.ui.payment.sevisfee.sevispayment.SEVISFeeViewModelFactory
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.createFilePart
import tech.hackcity.educarts.uitls.disablePrimaryButtonState
import tech.hackcity.educarts.uitls.enablePrimaryButtonState
import tech.hackcity.educarts.uitls.hideButtonLoadingState
import tech.hackcity.educarts.uitls.showButtonLoadingState
import tech.hackcity.educarts.uitls.toast
import tech.hackcity.educarts.uitls.uriToFile

/**
 *Created by Victor Loveday on 3/18/23
 */
class SEVISCouponFragment : Fragment(R.layout.fragment_have_sevis_payment_coupon), SEIVSCouponStep1Listener {

    private lateinit var binding: FragmentHaveSevisPaymentCouponBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var viewModel: SEVISFeeViewModel
    private val args: SEVISCouponFragmentArgs by navArgs()
    private var formUri: Uri? = null

    private val pickFormResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                data?.data?.let { uri ->
                    handleSelectedFormFile(uri)
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHaveSevisPaymentCouponBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val api = RetrofitInstance(requireContext())
        val sharePreferencesManager = SharePreferencesManager(requireContext())
        val repository = SEVISFeeRepository(api, sharePreferencesManager)
        val factory = SEVISFeeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[SEVISFeeViewModel::class.java]
        viewModel.listener4 = this

        binding.nextBtn.setOnClickListener {
            viewModel.formType = args.formType
            viewModel.sevisCoupon = createFilePart(requireContext(), "sevis_coupon", formUri)

            Coroutines.onMainWithScope(viewModel.viewModelScope) {
                viewModel.submitSevisCouponStep1(requireContext())
            }

        }

        binding.pickSevisCouponButton.setOnClickListener { openFilePickerForForm() }

    }

    private fun checkPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
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
                context?.toast(description = resources.getString(R.string.permission_granted_you_can_now_pick_files), toastType = ToastType.ERROR)
            } else {
                context?.toast(description = resources.getString(R.string.permission_denied_you_can_not_access_files), toastType = ToastType.ERROR)
            }
        }
    }

    private fun openFilePickerForForm() {
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

            pickFormResultLauncher.launch(intent)
        }
    }

    private fun handleSelectedFormFile(uri: Uri) {
        formUri = uri
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 123
    }

    override fun onRequestStarted() {
        sharedViewModel.updateScreenLoader(Pair(true, ""))
        showButtonLoadingState(binding.nextBtn, binding.progressBar, "")
        disablePrimaryButtonState(binding.nextBtn)
    }

    override fun onRequestFailed(message: String) {
        context?.toast(description = message, toastType = ToastType.ERROR)
        hideButtonLoadingState(
            binding.nextBtn,
            binding.progressBar,
            resources.getString(R.string.next)
        )
        enablePrimaryButtonState(binding.nextBtn)
        sharedViewModel.updateScreenLoader(Pair(false, ""))
    }

    override fun onRequestSuccessful(response: SevisCouponStep1Response) {
        hideButtonLoadingState(
            binding.nextBtn,
            binding.progressBar,
            resources.getString(R.string.next)
        )
        enablePrimaryButtonState(binding.nextBtn)
        sharedViewModel.updateScreenLoader(Pair(false, ""))

        val action = SEVISCouponFragmentDirections.actionSevisCouponFragmentToSevisFeeStep1Fragment(
            args.formType,
            resources.getString(R.string.i_have_generated_sevis_payment_coupon)
        )
        findNavController().navigate(action)

    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.updateHorizontalStepViewPosition(0)
    }

}