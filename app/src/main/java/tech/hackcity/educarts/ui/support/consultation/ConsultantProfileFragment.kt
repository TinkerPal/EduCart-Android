package tech.hackcity.educarts.ui.support.consultation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.support.SupportRepository
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.data.storage.UserInfoManager
import tech.hackcity.educarts.databinding.FragmentConsulantProfileBinding
import tech.hackcity.educarts.domain.model.support.Consultant
import tech.hackcity.educarts.domain.model.support.ConsultantProfileResponse
import tech.hackcity.educarts.ui.alerts.ToastType
import tech.hackcity.educarts.ui.browser.BrowserActivity
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.toast

/**
 *Created by Victor Loveday on 9/11/23
 */
class ConsultantProfileFragment: Fragment(R.layout.fragment_consulant_profile), ConsultantListener {

    private lateinit var binding: FragmentConsulantProfileBinding

    private val args: ConsultantProfileFragmentArgs by navArgs()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentConsulantProfileBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val api = RetrofitInstance(requireContext())
        val sharePreferencesManager = SharePreferencesManager(requireContext())
        val repository = SupportRepository(api, sharePreferencesManager)
        val factory = ConsultationViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[ConsultationViewModel::class.java]
        viewModel.consultantListener = this

        setupProfile(args.consultant)

        lifecycleScope.launchWhenCreated {
            viewModel.fetchConsultantProfile(args.consultant.id)
        }

        binding.scheduleMeetingBtn.setOnClickListener {
            val intent = Intent(requireContext(), BrowserActivity::class.java)
            intent.putExtra("destination", "Consultation")
            startActivity(intent)
        }

        val userInfoManager = UserInfoManager(requireContext())
        userInfoManager.fetchUserInfo().asLiveData().observe(viewLifecycleOwner) {
            if (it.free_consultation) {
                binding.scheduleMeetingBtn.text = resources.getString(R.string.schedule_free_appointment)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupProfile(consultant: Consultant) {
        with(binding) {
            if (consultant.profile_picture.isNullOrEmpty()) {
                profileImageView.setImageResource(R.drawable.default_profile)
            } else {
                Glide.with(requireActivity()).load(consultant.profile_picture).into(profileImageView)
            }

            priceTV.text = "$${consultant.price_per_hour?.takeIf { true } ?: resources.getString(R.string.nil)}/hour"

            fullNameTV.text = consultant.name?.takeIf { it.isNotEmpty() } ?: resources.getString(R.string.nil)

            specializationTV.text = consultant.specialization?.takeIf { it.isNotEmpty() } ?: resources.getString(R.string.nil)

            experienceTV.text = "${consultant.years_of_experience?.takeIf { true } ?: resources.getString(R.string.nil)} years"

            qualificationTV.text = consultant.qualification?.takeIf { it.isNotEmpty() } ?: resources.getString(R.string.nil)

            locationTV.text = consultant.state?.takeIf { it.isNotEmpty() } ?:
            resources.getString(R.string.nil)

            countryTV.text = consultant.country?.takeIf { it.isNotEmpty() } ?:
            resources.getString(R.string.nil)

            availabilityTV.text = consultant.availability?.takeIf { it.isNotEmpty() } ?:
            resources.getString(R.string.nil)

            dailyAvailabilityTV.text = consultant.daily_availability?.takeIf { it.isNotEmpty() } ?:
            resources.getString(R.string.nil)

            bioTV.text = consultant.bio?.takeIf { it.isNotEmpty() } ?:
            resources.getString(R.string.nil)

            when (consultant.rate) {
                1 -> star1.setImageResource(R.drawable.rating_star_filled)
                2 -> star2.setImageResource(R.drawable.rating_star_filled)
                3 -> star3.setImageResource(R.drawable.rating_star_filled)
                4 -> star4.setImageResource(R.drawable.rating_star_filled)
                5 -> star5.setImageResource(R.drawable.rating_star_filled)
                else -> {}
            }

        }

    }

    override fun onFetchConsultantRequestStarted() {
        sharedViewModel.updateLoadingScreen(true)
    }

    override fun onRequestFailed(message: String) {
        context?.toast(description = message, toastType = ToastType.ERROR)
        sharedViewModel.updateLoadingScreen(false)
    }

    override fun onFetchConsultantRequestSuccessful(response: ConsultantProfileResponse) {
        sharedViewModel.updateLoadingScreen(false)
        setupProfile(response.data)
    }
}