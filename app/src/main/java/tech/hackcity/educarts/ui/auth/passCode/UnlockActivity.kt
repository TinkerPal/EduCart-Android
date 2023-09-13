package tech.hackcity.educarts.ui.auth.passCode

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.biometric.BiometricPrompt
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.ActivityUnlockBinding
import tech.hackcity.educarts.ui.auth.AuthActivity
import tech.hackcity.educarts.ui.main.MainActivity
import tech.hackcity.educarts.uitls.BiometricAuthListener
import tech.hackcity.educarts.uitls.Biometrics
import tech.hackcity.educarts.uitls.toast

class UnlockActivity : AppCompatActivity(), BiometricAuthListener {

    private lateinit var binding: ActivityUnlockBinding
    var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUnlockBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_EduCarts)
        if (retrieveLoginStatus()) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
        setContentView(binding.root)

        //keep app on light mode only
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.signInBtn.setOnClickListener {
            loginUser()
        }

        binding.fingerprintIV.setOnClickListener {
            loginUserWithFingerprint()
        }
    }

    private fun loginUser() {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }

    private fun loginUserWithFingerprint() {
        if (Biometrics.isBiometricReady(this)) {
            Biometrics.showBiometricPrompt(
                activity = this,
                listener = this,
                cryptoObject = null,
            )
        } else {
            toast(resources.getString(R.string.no_biometric_feature_perform_on_this_device))
        }
    }

    override fun onBiometricAuthenticateError(error: Int, errMsg: String) {
        when (error) {
            BiometricPrompt.ERROR_USER_CANCELED -> finish()
            BiometricPrompt.ERROR_NEGATIVE_BUTTON -> {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    override fun onBiometricAuthenticateSuccess(result: BiometricPrompt.AuthenticationResult) {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }

    private fun retrieveLoginStatus(): Boolean {
        sharedPreferences = this.getSharedPreferences("loginPref", Context.MODE_PRIVATE)
        return sharedPreferences!!.getBoolean("isLogin", false)
    }
}