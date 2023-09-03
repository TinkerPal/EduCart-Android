package tech.hackcity.educarts.ui.payment.sevisfee.sevispayment

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
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
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.payment.SEVISFeeRepository
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.domain.model.error.ErrorMessage
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep1Response
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.createFilePart
import tech.hackcity.educarts.uitls.disablePrimaryButtonState
import tech.hackcity.educarts.uitls.enablePrimaryButtonState
import tech.hackcity.educarts.uitls.hideButtonLoadingState
import tech.hackcity.educarts.uitls.showButtonLoadingState
import tech.hackcity.educarts.uitls.toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 *Created by Victor Loveday on 3/13/23
 */
class SEVISFeeStep1Fragment : Fragment(R.layout.fragment_sevis_fee_step_1), SEIVSFeeStep1Listener {

    private lateinit var binding: FragmentSevisFeeStep1Binding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var viewModel: SEVISFeeViewModel

    private val args: SEVISFeeStep1FragmentArgs by navArgs()

    private var formUri: Uri? = null
    private var passportUri: Uri? = null
    private var internationalPassportUri: Uri? = null

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
        val factory = SEVISFeeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[SEVISFeeViewModel::class.java]
        viewModel.listener1 = this

        binding.textGuide1.text = resources.getString(
            R.string.please_fill_the_following_form_as_it_is_in_your,
            args.formType
        )

        when (args.formType) {
            resources.getString(R.string.form_i_20) -> binding.textGuide2.text =
                resources.getString(R.string.upload_i_20_form_here)

            resources.getString(R.string.ds_2019) -> binding.textGuide2.text =
                resources.getString(R.string.upload_ds_2019_form_here)
        }

        setupDatePicker()
        setupFilePickers()

        binding.nextBtn.setOnClickListener {
            viewModel.sevisId = binding.sevisIDET.text.toString().trim()
            viewModel.lastName = binding.lastNameET.text.toString().trim()
            viewModel.givenName = binding.firstNameET.text.toString().trim()
            viewModel.dateOfBirth = binding.dateOfBirth.text.toString().trim()

            viewModel.form = createFilePart(requireContext(), "form", formUri)
            viewModel.passport = createFilePart(requireContext(), "passport", passportUri)
            viewModel.internationalPassport = createFilePart(requireContext(), "international_passport", internationalPassportUri)

            Coroutines.onMainWithScope(viewModel.viewModelScope) {
                viewModel.submitSevisFeeStep1(requireContext())
            }

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

    private fun setupDatePicker() {
        binding.dateOfBirth.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, monthOfYear, dayOfMonth ->
                    val formattedDate = formatDate(year, monthOfYear + 1, dayOfMonth)
                    binding.dateOfBirth.setText(formattedDate)
                },
                year,
                month,
                day
            )

            // Show the date picker dialog
            datePickerDialog.show()
        }
    }

    private fun formatDate(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day) // Note: month is 0-based
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return dateFormat.format(calendar.time)
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
                context?.toast(resources.getString(R.string.permission_granted_you_can_now_pick_files))
            } else {
                context?.toast(resources.getString(R.string.permission_denied_you_can_not_access_files))
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
                "application/pdf|application/msword|application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            intent.addCategory(Intent.CATEGORY_OPENABLE)

            val mimeTypes = arrayOf(
                "application/pdf",
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
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

    companion object {
        private const val PERMISSION_REQUEST_CODE = 123
    }

    override fun onRequestStarted() {
        sharedViewModel.updateLoadingScreen(true)
        showButtonLoadingState(binding.nextBtn, binding.progressBar, "")
        disablePrimaryButtonState(binding.nextBtn)
    }

    override fun onRequestFailed(message: String) {
        context?.toast("$message")
        hideButtonLoadingState(
            binding.nextBtn,
            binding.progressBar,
            resources.getString(R.string.next)
        )
        enablePrimaryButtonState(binding.nextBtn)
        sharedViewModel.updateLoadingScreen(false)
    }

    override fun onRequestSuccessful(response: SEVISFeeStep1Response) {
        hideButtonLoadingState(
            binding.nextBtn,
            binding.progressBar,
            resources.getString(R.string.next)
        )
        enablePrimaryButtonState(binding.nextBtn)
        sharedViewModel.updateLoadingScreen(false)

        val action =
            SEVISFeeStep1FragmentDirections.actionSevisFeeStep1FragmentToSevisFeeStep2Fragment(
                args.formType
            )
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.updateHorizontalStepViewPosition(1)
    }
}