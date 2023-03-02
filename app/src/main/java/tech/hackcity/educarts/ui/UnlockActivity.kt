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
import tech.hackcity.educarts.databinding.ActivityUnlockBinding
import tech.hackcity.educarts.uitls.BiometricAuthListener
import tech.hackcity.educarts.uitls.BiometricUtils

class UnlockActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUnlockBinding
    var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUnlockBinding.inflate(layoutInflater)
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

//        startActivity(Intent(this, AuthActivity::class.java))
//        finish()
    }

    /*
   * Check whether the Device is Capable of the Biometric
   */
    private fun hasBiometricCapability(context: Context): Int {
        return BiometricManager.from(context).canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
    }

    fun isBiometricReady(context: Context) =
        hasBiometricCapability(context) == BiometricManager.BIOMETRIC_SUCCESS

    //setting up a biometric
    private fun setBiometricPromptInfo(
        title: String,
        subtitle: String,
        description: String,
    ): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subtitle)
            .setDescription(description)
            .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
            .build()

    }

    /*
    * Initiate the Biometric Prompt
    */
    private fun initBiometricPrompt(
        activity: AppCompatActivity,
        listener: BiometricAuthListener
    ): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(activity)
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                listener.onBiometricAuthenticateError(errorCode, errString.toString())
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                listener.onBiometricAuthenticateSuccess(result)
            }
        }
        return BiometricPrompt(activity, executor, callback)
    }

    /*
     * Display the Biometric Prompt
     */
    fun showBiometricPrompt(
        title: String = "Biometric Authentication",
        subtitle: String = "Enter biometric credentials to proceed.",
        description: String = "Input your Fingerprint or FaceID to ensure it's you!",
        activity: AppCompatActivity,
        listener: BiometricAuthListener,
        cryptoObject: BiometricPrompt.CryptoObject? = null,
    ) {
        val promptInfo = setBiometricPromptInfo(
            title,
            subtitle,
            description,
        )

        val biometricPrompt = initBiometricPrompt(activity, listener)
        biometricPrompt.apply {
            if (cryptoObject == null) authenticate(promptInfo)
            else authenticate(promptInfo, cryptoObject)
        }
    }

    private fun retrieveLoginStatus(): Boolean {
        sharedPreferences = this.getSharedPreferences("loginPref", Context.MODE_PRIVATE)
        return sharedPreferences!!.getBoolean("isLogin", false)
    }
}