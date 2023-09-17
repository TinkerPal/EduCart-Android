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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.hbb20.CountryCodePicker
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.settings.SettingsRepository
import tech.hackcity.educarts.data.storage.SessionManager
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.data.storage.UserInfoManager
import tech.hackcity.educarts.databinding.FragmentEditProfileBinding
import tech.hackcity.educarts.domain.model.location.Country
import tech.hackcity.educarts.domain.model.location.RegionResponse
import tech.hackcity.educarts.domain.model.location.State
import tech.hackcity.educarts.domain.model.settings.ProfileResponse
import tech.hackcity.educarts.ui.adapters.CountriesAdapter
import tech.hackcity.educarts.ui.adapters.StatesAdapter
import tech.hackcity.educarts.ui.alerts.RegionsBottomSheetFragment
import tech.hackcity.educarts.ui.alerts.ToastType
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.Coroutines
import tech.hackcity.educarts.uitls.createFilePart
import tech.hackcity.educarts.uitls.toast

/**
 *Created by Victor Loveday on 2/22/23
 */
class EditProfileFragment : Fragment(R.layout.fragment_edit_profile), EditProfileListener {

    private lateinit var binding: FragmentEditProfileBinding

    private var currentBottomSheetFragment: RegionsBottomSheetFragment? = null

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var profileUri: Uri? = null
    private lateinit var ccpGetNumber: CountryCodePicker
    private var dialCode = 0
    private var isNumberValid = false
    private lateinit var userInfoManager: UserInfoManager
    private lateinit var viewModel: ProfileViewModel

    private var firstName = ""
    private var lastName = ""
    private var phoneNumber = ""
    private var countryOfResidence = ""
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

        Coroutines.onMainWithScope(viewModel.viewModelScope) {
            viewModel.fetchRegions()
        }

        binding.doneBtn.setOnClickListener {
            viewModel.profilePicture = createFilePart(requireContext(), "profile_picture", profileUri)
            viewModel.firstName = binding.firstNameET.text.toString().trim()
            viewModel.lastName = binding.lastNameET.text.toString().trim()
            viewModel.countryOfResidence = binding.countryOfResidenceET.text.toString().trim()
            viewModel.countryCode = dialCode
            viewModel.phoneNumber = binding.phoneNumberET.text.toString()
            viewModel.institutionOfStudy = "Bayero University, Kano"
            viewModel.countryOfBirth = countryOfBirth
            viewModel.state = state
            viewModel.city = binding.cityET.text.toString().trim()

            Log.d("DEMO", city)

            Coroutines.onMainWithScope(viewModel.viewModelScope) {
                viewModel.editProfile(requireContext())
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setupUserInfo() {
        userInfoManager.fetchUserInfo().asLiveData().observe(viewLifecycleOwner) { user ->
            firstName = user.firstName
            lastName = user.lastName
            dialCode = user.countryCode
            phoneNumber = user.phoneNumber
            countryOfResidence = user.countryOfResidence
            institutionOfStudy = user.institutionOfStudy.toString()
            countryOfBirth = user.countryOfBirth.toString()
            state = user.state.toString()
            city = user.city.toString()

            with(binding) {
                val requestOptions = RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()

                if (user.profilePhoto.isNullOrEmpty()) {
                    profilePhoto.setImageResource(R.drawable.default_profile)
                } else {
                    Glide.with(requireContext())
                        .load(user.profilePhoto)
                        .apply(requestOptions)
                        .into(profilePhoto)
                }

                firstNameET.setText(firstName)
                lastNameET.setText(lastName)
                phoneNumberET.setText(phoneNumber)
                countryOfResidenceET.setText(countryOfResidence)
                institutionOfStudyET.setText(institutionOfStudy.takeIf { it.isNotEmpty() } ?: "")
                countryOfBirthET.setText(countryOfBirth.takeIf { it.isNotEmpty() } ?: "")
                stateET.setText(state.takeIf { it.isNotEmpty() } ?: "")
                cityET.setText(city.takeIf { it.isNotEmpty() } ?: "")
            }

        }
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
                context?.toast(description = resources.getString(R.string.permission_granted_you_can_now_pick_files), toastType = ToastType.SUCCESS)
            } else {
                context?.toast(description = resources.getString(R.string.permission_denied_you_can_not_access_files), toastType = ToastType.ERROR)
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

    private fun showCountriesBottomSheet(countries: List<Country>) {
        binding.countryOfBirthET.setOnClickListener {
            val bottomSheetFragment = RegionsBottomSheetFragment()
            currentBottomSheetFragment = bottomSheetFragment // Store the reference

            bottomSheetFragment.onBindingReady { bottomSheetBinding ->
                val recyclerView = bottomSheetBinding.regionsRV

                val countriesAdapter = CountriesAdapter()
                recyclerView.apply {
                    adapter = countriesAdapter
                    layoutManager = LinearLayoutManager(requireContext())
                    countriesAdapter.setData(countries.toMutableList())

                    countriesAdapter.setOnItemClickListener {
                        countryOfBirth = it.name
                        binding.countryOfBirthET.setText(it.name)
                        showStatesBottomSheet(it.states)
                        currentBottomSheetFragment?.closeBottomSheet()
                    }
                }
            }
            activity?.supportFragmentManager?.let { bottomSheetFragment.show(it, bottomSheetFragment.tag) }
        }

    }

    private fun showStatesBottomSheet(states: List<State>) {
        binding.stateET.setOnClickListener {
            val bottomSheetFragment = RegionsBottomSheetFragment()
            currentBottomSheetFragment = bottomSheetFragment // Store the reference

            bottomSheetFragment.onBindingReady { bottomSheetBinding ->
                val recyclerView = bottomSheetBinding.regionsRV

                val statesAdapter = StatesAdapter()
                recyclerView.apply {
                    adapter = statesAdapter
                    layoutManager = LinearLayoutManager(requireContext())
                    statesAdapter.setData(states.toMutableList())
                    statesAdapter.setOnItemClickListener {
                        state = it.name
                        binding.stateET.setText(it.name)
                        currentBottomSheetFragment?.closeBottomSheet()
                    }
                }
            }
            activity?.supportFragmentManager?.let { bottomSheetFragment.show(it, bottomSheetFragment.tag) }
        }
    }

    override fun onEditProfileRequestStarted() {
        sharedViewModel.updateLoadingScreen(true)
    }

    override fun onRequestFailed(message: String) {
        context?.toast(description = message, toastType = ToastType.ERROR)
        sharedViewModel.updateLoadingScreen(false)
    }

    override fun onFetchRegionsRequestFailed(message: String) {
        context?.toast(description = resources.getString(R.string.failed_to_fetch_countries), toastType = ToastType.ERROR)
    }

    override fun onFetchRegionsRequestSuccessful(response: RegionResponse) {
        showCountriesBottomSheet(response.data)
    }

    override fun onEditProfileRequestSuccessful(response: ProfileResponse) {
        response.message?.let {
            context?.toast(
                description = it,
                toastType = ToastType.SUCCESS
            )
        }
        sharedViewModel.updateLoadingScreen(false)
        findNavController().popBackStack()
    }

}