package tech.hackcity.educarts.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.ActivityUnlockBinding
import tech.hackcity.educarts.uitls.BiometricAuthListener
import tech.hackcity.educarts.uitls.BiometricUtils

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
        if (BiometricUtils.isBiometricReady(this)) {
            BiometricUtils.showBiometricPrompt(
                activity = this,
                listener = this,
                cryptoObject = null,
            )
        }else {
            Toast.makeText(this, "No biometric feature perform on this device", Toast.LENGTH_SHORT).show()
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