package tech.hackcity.educarts.ui.auth.verifyOTP

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.goodiebag.pinview.Pinview
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.auth.AuthRepository
import tech.hackcity.educarts.data.storage.SessionManager
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.data.storage.UserInfoManager
import tech.hackcity.educarts.databinding.FragmentOtpBinding
import tech.hackcity.educarts.domain.model.auth.VerifyOTPResponse
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel
import tech.hackcity.educarts.uitls.CountdownTimer
import tech.hackcity.educarts.uitls.enableButtonState
import tech.hackcity.educarts.uitls.toast

/**
 *Created by Victor Loveday on 2/20/23
 */
class OTPFragment : Fragment(R.layout.fragment_otp), VerifyOTPListener {

    private lateinit var binding: FragmentOtpBinding

    private val args: OTPFragmentArgs by navArgs()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var countdownTimer: CountdownTimer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentOtpBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.title.text = args.pageTitle
        binding.message.text = args.information

        val api = RetrofitInstance(requireContext())
        val sessionManager = SessionManager(requireContext())
        val sharePreferencesManager = SharePreferencesManager(requireContext())
        val userInfoManager = UserInfoManager(requireContext())
        val repository = AuthRepository(api, sessionManager, sharePreferencesManager, userInfoManager)
        val factory = VerifyOTPViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[VerifyOTPViewModel::class.java]
        viewModel.verifyOTPListener = this

        binding.pinView.setPinViewEventListener(object : Pinview.PinViewEventListener {
            override fun onDataEntered(pinview: Pinview?, fromUser: Boolean) {
                viewModel.id = sharePreferencesManager.fetchUserId().toString()
                viewModel.otp = pinview?.value

                viewModel.verifyPin(requireContext())
            }
        })

        setupResendOTPCountdownTimer()
    }

    private fun navigateToDestination() {
        when (args.destination) {
            "login" -> {
                findNavController().navigate(R.id.action_OTPFragment_to_loginFragment)
            }

            "reset password" -> {
                findNavController().navigate(R.id.action_OTPFragment_to_createNewPasswordFragment)
            }

            "create pin" -> {
                findNavController().navigate(R.id.action_OTPFragment_to_createNewPinFragment)
            }
        }
    }

    private fun setupResendOTPCountdownTimer() {
        val totalDurationMillis = 1 * 60 * 1000L
        val intervalMillis = 1000L

        countdownTimer = CountdownTimer(
            totalDurationMillis,
            intervalMillis,
            object : CountdownTimer.CountdownListener {
                override fun onTick(timeLeft: String) {
                    binding.timerTextView.text =
                        resources.getString(R.string.resend_code_in, timeLeft)
                }

                override fun onFinish() {
                    enableButtonState(binding.resendCodeBtn)
                }
            })

        countdownTimer.start()
    }

    override fun onRequestStarted() {
        val infiniteZoomAnim =
            AnimationUtils.loadAnimation(requireContext(), R.anim.infinite_zoom_in)
        binding.astericksImageView.startAnimation(infiniteZoomAnim)
    }

    override fun onRequestFailed(message: String) {
        binding.astericksImageView.clearAnimation()
        context?.toast(message)
    }

    override fun onRequestSuccessful(response: VerifyOTPResponse) {
        binding.astericksImageView.clearAnimation()
        context?.toast(response.message)
        navigateToDestination()
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.setToolBarColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.background_001
            )
        )
        sharedViewModel.setToolbarVisibility(true)
        sharedViewModel.updateHorizontalStepViewPosition(args.step)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (countdownTimer.isRunning()) {
            countdownTimer.stop()
        }
    }

    override fun onPause() {
        super.onPause()
        if (countdownTimer.isRunning()) {
            countdownTimer.stop()
        }
    }

    override fun onDetach() {
        super.onDetach()
        if (countdownTimer.isRunning()) {
            countdownTimer.stop()
        }
    }

}