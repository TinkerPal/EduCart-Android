package tech.hackcity.educarts.ui.settings.profile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.hbb20.CountryCodePicker
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.settings.SettingsRepository
import tech.hackcity.educarts.data.storage.SessionManager
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.data.storage.UserInfoManager
import tech.hackcity.educarts.databinding.FragmentEditProfileBinding
import tech.hackcity.educarts.domain.model.settings.ProfileResponse
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.Constants.EDU_CARTS_IMAGE_URL
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.observeOnce
import tech.hackcity.educarts.uitls.toImageFile
import tech.hackcity.educarts.uitls.toast
import java.net.URLEncoder

/**
 *Created by Victor Loveday on 2/22/23
 */
class EditProfileFragment : Fragment(R.layout.fragment_edit_profile), EditProfileListener {

    private lateinit var binding: FragmentEditProfileBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var profileUri: Uri? = null
    private lateinit var ccpGetNumber: CountryCodePicker
    private var dialCode = 0
    private var isNumberValid = false
    private lateinit var userInfoManager: UserInfoManager
    private lateinit var viewModel : ProfileViewModel

    private var firstName = ""
    private var lastName = ""
    private var phoneNumber = ""
    private var countryOfResisdence = ""
    private var institutionOfStudy = ""
    private var countryOfBirth = ""
    private var state = ""
    private var city = ""

    private val pickPhotoResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                data?.data?.let { uri ->
                    handleSelectedPhotoFile(uri)
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentEditProfileBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.profilePhoto.setOnClickListener { openFilePickerForPhoto() }

        val api = RetrofitInstance(requireContext())
        val sessionManager = SessionManager(requireContext())
        val sharePreferencesManager = SharePreferencesManager(requireContext())
        userInfoManager = UserInfoManager(requireContext())
        val repository =
            SettingsRepository(api, sessionManager, sharePreferencesManager, userInfoManager)
        val factory = ProfileViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[ProfileViewModel::class.java]
        viewModel.editListener = this

        setupUserInfo()
        validatePhoneNumber()

        binding.doneBtn.setOnClickListener {
            viewModel.profilePicture = createProfilePicturePart()
            viewModel.firstName = binding.firstNameET.text.toString().trim()
            viewModel.lastName = binding.lastNameET.text.toString().trim()
            viewModel.countryOfResidence = "Nigeria"
            viewModel.countryCode = dialCode
            viewModel.phoneNumber = binding.phoneNumberET.text.toString()
            viewModel.institutionOfStudy = "Bayero University, Kano"

            Coroutines.onMainWithScope(viewModel.viewModelScope) {
                viewModel.editProfile(requireContext())
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setupUserInfo() {
        userInfoManager.fetchUserInfo().asLiveData().observeOnce(viewLifecycleOwner) { user ->
            firstName = user.firstName
            lastName = user.lastName
            dialCode = user.countryCode
            phoneNumber = user.phoneNumber
            countryOfResisdence = user.countryOfResidence
            institutionOfStudy = user.institutionOfStudy.toString()
            countryOfBirth = user.countryOfBirth.toString()
            state = user.state.toString()
            city = user.city.toString()

            val profileUrl = "${EDU_CARTS_IMAGE_URL}${user.profilePhoto}"

            with(binding) {
                if (user.profilePhoto.isNullOrEmpty()) {
                    profilePhoto.setImageResource(R.drawable.default_profile)
                }else {
                    Log.d("ProfilePhoto", profileUrl)
                    Glide.with(requireContext()).load(profileUrl).into(profilePhoto)
                }

                firstNameET.setText(firstName)
                lastNameET.setText(lastName)
                phoneNumberET.setText(phoneNumber)
                countryOfResidenceET.setText(countryOfResisdence)
                institutionOfStudyET.setText( institutionOfStudy.takeIf { it.isNotEmpty() } ?: resources.getString(R.string.nil))
                countryOfBirthET.setText( countryOfBirth.takeIf { it.isNotEmpty() } ?: resources.getString(R.string.nil))
                stateET.setText( state.takeIf { it.isNotEmpty() } ?: resources.getString(R.string.nil))
                cityET.setText( city.takeIf { it.isNotEmpty() } ?: resources.getString(R.string.nil))
            }

        }
    }

    private fun createProfilePicturePart(): MultipartBody.Part? {
        if (profileUri != null) {
            val profilePictureFile = profileUri?.toImageFile(requireContext())
            val profilePictureRequestBody = profilePictureFile?.let {
                RequestBody.create(
                    "image/*".toMediaTypeOrNull(),
                    it
                )
            }
            return profilePictureRequestBody?.let {
                MultipartBody.Part.createFormData(
                    "profile_picture",
                    profilePictureFile.name,
                    it
                )
            }
        }
        return null
    }

    private fun validatePhoneNumber() {
        ccpGetNumber = binding.ccp

        userInfoManager.fetchUserInfo().asLiveData().observe(viewLifecycleOwner) {
            ccpGetNumber.setDefaultCountryUsingPhoneCode(it.countryCode)
            ccpGetNumber.resetToDefaultCountry()
        }

        ccpGetNumber.registerCarrierNumberEditText(binding.phoneNumberET)
        ccpGetNumber.setPhoneNumberValidityChangeListener { isValidNumber ->
            if (isValidNumber) {
                binding.verifyIcon.visibility = View.VISIBLE
                binding.verifyIcon.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.success_green
                    )
                )

                isNumberValid = true

            } else {
                binding.verifyIcon.visibility = View.INVISIBLE
                isNumberValid = false

                dialCode = ccpGetNumber.selectedCountryCode.toInt()
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

    private fun openFilePickerForPhoto() {
        if (checkPermission()) {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickPhotoResultLauncher.launch(intent)
        }
    }

    private fun handleSelectedPhotoFile(uri: Uri) {
        profileUri = uri
        Glide.with(requireContext()).load(uri).into(binding.profilePhoto)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 123
    }

    override fun onEditProfileRequestStarted() {
        sharedViewModel.updateLoadingScreen(true)
    }

    override fun onRequestFailed(message: String) {
        context?.toast(message)
        Log.d("EditProfile", message)
        sharedViewModel.updateLoadingScreen(false)
    }

    override fun onEditProfileRequestSuccessful(response: ProfileResponse) {
        response.message?.let { context?.toast(it) }
        sharedViewModel.updateLoadingScreen(false)
    }

}