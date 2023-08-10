package tech.hackcity.educarts.ui.payment.sevisfee.sevispayment.step1

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentSevisFeeStep1Binding
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.payment.SEVISFeeRepository
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep1Response
import tech.hackcity.educarts.ui.payment.sevisfee.SEIVSFeeStep1Listener
import tech.hackcity.educarts.uitls.FileConverter
import tech.hackcity.educarts.uitls.disableButtonState
import tech.hackcity.educarts.uitls.enableButtonState
import tech.hackcity.educarts.uitls.hideButtonLoadingState
import tech.hackcity.educarts.uitls.showButtonLoadingState
import tech.hackcity.educarts.uitls.toast

/**
 *Created by Victor Loveday on 3/13/23
 */
class SEVISFeeStep1Fragment : Fragment(R.layout.fragment_sevis_fee_step_1), SEIVSFeeStep1Listener {

    private lateinit var binding: FragmentSevisFeeStep1Binding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var viewModel: SEVISFeeStep1ViewModel

    private  var formUri: Uri? = null
    private  var passportUri: Uri? = null
    private  var internationalPassportUri: Uri? = null

    private val pickFormResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                data?.data?.let { uri ->
                    handleSelectedFormFile(uri)
                }
            }
        }

    private val pickPhotoResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                data?.data?.let { uri ->
                    handleSelectedPhotoFile(uri)
                }
            }
        }

    private val pickPassportResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                data?.data?.let { uri ->
                    handleSelectedPassportFile(uri)
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSevisFeeStep1Binding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val api = RetrofitInstance(requireContext())
        val sharePreferencesManager = SharePreferencesManager(requireContext())
        val repository = SEVISFeeRepository(api, sharePreferencesManager)
        val factory = SEVISFeeStep1ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[SEVISFeeStep1ViewModel::class.java]
        viewModel.listener = this

        setupFilePickers()

        binding.nextBtn.setOnClickListener {
            viewModel.user = sharePreferencesManager.fetchUserId()
            viewModel.sevis_id = binding.sevisIDET.text.toString().trim()
            viewModel.last_name = binding.lastNameET.text.toString().trim()
            viewModel.given_name = binding.firstNameET.text.toString().trim()
            viewModel.date_of_birth = "2023-08-03"

            if (formUri == null || passportUri == null || internationalPassportUri == null) {
                context?.toast(resources.getString(R.string.some_files_are_missing))
                return@setOnClickListener
            }


            viewModel.form = formUri!!
            viewModel.passport = passportUri!!
            viewModel.international_passport = internationalPassportUri!!
            viewModel.submitStep1(requireContext())

        }
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

    private fun setupFilePickers() {
        binding.pickFormButton.setOnClickListener { openFilePickerForForm() }
        binding.pickRecentPassportButton.setOnClickListener { openFilePickerForPhoto() }
        binding.pickInternationalPassportButton.setOnClickListener { openFilePickerForPassport() }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showToast("Permission granted. You can now pick files.")
            } else {
                showToast("Permission denied. Cannot access files.")
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

    private fun openFilePickerForPhoto() {
        if (checkPermission()) {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickPhotoResultLauncher.launch(intent)
        }
    }

    private fun openFilePickerForPassport() {
        if (checkPermission()) {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type =
                "application/pdf|application/msword|application/vnd.openxmlformats-officedocument.wordprocessingml.document|image/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)

            val mimeTypes = arrayOf(
                "application/pdf",
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "image/*"
            )
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)

            pickPassportResultLauncher.launch(intent)
        }
    }

    private fun handleSelectedFormFile(uri: Uri) {
        formUri = uri
    }

    private fun handleSelectedPhotoFile(uri: Uri) {
        passportUri = uri
    }

    private fun handleSelectedPassportFile(uri: Uri) {
        internationalPassportUri = uri
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 123
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.updateStepIndicator(arrayOf(1, 3))
    }

    override fun onRequestStarted() {
        showButtonLoadingState(binding.nextBtn, binding.progressBar, "")
        disableButtonState(binding.nextBtn)
    }

    override fun onRequestFailed(message: List<ErrorMessage>) {
        Toast.makeText(requireContext(), "$message", Toast.LENGTH_SHORT).show()
        hideButtonLoadingState(binding.nextBtn, binding.progressBar, resources.getString(R.string.next))
        enableButtonState(binding.nextBtn)
    }

    override fun onRequestSuccessful(response: SEVISFeeStep1Response) {
        hideButtonLoadingState(binding.nextBtn, binding.progressBar, resources.getString(R.string.next))
        enableButtonState(binding.nextBtn)

        findNavController().navigate(R.id.action_sevisFeeStep1Fragment_to_sevisFeeStep2Fragment)
    }

}