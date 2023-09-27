package tech.hackcity.educarts.uitls

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import tech.hackcity.educarts.R
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.ClipData
import android.content.ClipboardManager
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.view.animation.LinearInterpolator
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import tech.hackcity.educarts.ui.alerts.CustomToast
import tech.hackcity.educarts.ui.alerts.ToastType
import java.lang.StringBuilder
import android.util.Patterns
import android.widget.CheckBox


/**
 *Created by Victor Loveday on 5/10/23
 */

fun Context.toast(title: String? = null, description: String, toastType: ToastType) {

    val duration = when (toastType) {
        ToastType.INFO -> 3000
        ToastType.ERROR -> 5000
        ToastType.SUCCESS -> 2000
    }

    val formattedDescription = StringBuilder()

    if (isJsonContainMessageAndCode(description)) {
        val errorMessages = extractErrorMessagesFromErrorBody(description)
        if (errorMessages.isNotEmpty()) {
            for ((code, message) in errorMessages) {
                formattedDescription.append(message)
            }

            if (errorMessages.size > 1) {
                formattedDescription.append("\n\n")
            }
        }
    } else {
        formattedDescription.append(description)
    }

    CustomToast.makeText(
        this,
        title,
        formattedDescription.toString(),
        duration,
        toastType
    )
}

fun View.snackbar(message: String, action: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction(action) {
            snackbar.dismiss()
        }
    }.show()
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.INVISIBLE
}

fun showLoadingScreen(view: ViewGroup) {
    view.visibility = View.VISIBLE
}

fun hideLoadingScreen(view: ViewGroup) {
    view.visibility = View.GONE
}

fun startShimmerLoader(shimmerFrameLayout: ShimmerFrameLayout) {
    shimmerFrameLayout.startShimmer()
    shimmerFrameLayout.visibility = View.VISIBLE
}

fun stopShimmerLoader(shimmerFrameLayout: ShimmerFrameLayout) {
    shimmerFrameLayout.stopShimmer()
    shimmerFrameLayout.visibility = View.INVISIBLE
}

fun showButtonLoadingState(button: Button, progressBar: ProgressBar, buttonText: String) {
    progressBar.show()
    button.isEnabled = false
    button.setBackgroundResource(R.drawable.disabled_button)
    button.text = buttonText
}

fun hideButtonLoadingState(button: Button, progressBar: ProgressBar, buttonText: String) {
    progressBar.hide()
    button.isEnabled = true
    button.setBackgroundResource(R.drawable.primary_button)
    button.text = buttonText
}

fun enablePrimaryButtonState(button: Button) {
    button.apply {
        isEnabled = true
        setBackgroundResource(R.drawable.primary_button)
    }
}

fun disablePrimaryButtonState(button: Button) {
    button.apply {
        isEnabled = false
        setBackgroundResource(R.drawable.disabled_button)
    }
}

fun MaterialButton.animateButtonFadeIn() {
    val originalAlpha = alpha
    alpha = 0.3f

    val animator = ObjectAnimator.ofFloat(this, "alpha", originalAlpha)
    animator.duration = 500L

    animator.interpolator = LinearInterpolator()
    animator.start()
}

fun TextView.animateTextFadeIn() {
    val originalAlpha = alpha
    alpha = 0.3f

    val animator = ObjectAnimator.ofFloat(this, "alpha", originalAlpha)
    animator.duration = 500L

    animator.interpolator = LinearInterpolator()
    animator.start()
}

fun spannableTextWithForegroundColour(
    text: String,
    start: Int,
    end: Int,
    fColour: Int,
    textView: TextView
) {
    val spannableString = SpannableString(text)
    val fColor = ForegroundColorSpan(fColour)
    spannableString.setSpan(fColor, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)

    textView.text = spannableString
}

fun shortenString(input: String, length: Int): String {
    if (input.length <= length) {
        return input
    }

    val shortenedString = input.substring(0, length)
    return "$shortenedString..."
}

fun removeSpacesFromString(input: String): String {
    val hasSpaces = input.contains(" ")

    return if (hasSpaces) {
        input.replace(" ", "")
    } else {
        input
    }
}

fun clearExtraCharacters(input: String): String {
    return input.replace("\"", "").trim()
}

fun TextView.animateTextFadeOut() {
    val originalAlpha = alpha

    alpha = 1.0f

    val animator = ObjectAnimator.ofFloat(this, "alpha", 0f)
    animator.duration = 500L
    animator.interpolator = LinearInterpolator()
    animator.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            alpha = originalAlpha
        }
    })
    animator.start()
}

fun hideViews(views: List<View>) {
    for (view in views) {
        view.visibility = View.GONE
    }
}

fun showViews(views: List<View>) {
    for (view in views) {
        view.visibility = View.VISIBLE
    }
}

fun changeToolbarColor(toolbar: MaterialToolbar, color: Int) {
    toolbar.setBackgroundColor(color)
}

fun hideToolBar(toolbar: MaterialToolbar) {
    toolbar.visibility = View.INVISIBLE
}

fun showToolBar(toolbar: MaterialToolbar) {
    toolbar.visibility = View.VISIBLE
}

fun showCustomInfoDialog(
    context: Context,
    title: String,
    message: String,
    positiveButtonMessage: String,
    activity: Class<*>? = null,
    destination: String? = null,
    cancelable: Boolean = false
) {
    MaterialAlertDialogBuilder(context, R.style.AlertDialogTheme)
        .setTitle(title)
        .setMessage(message)
        .setCancelable(cancelable)
        .setPositiveButton(positiveButtonMessage) { dialog, which ->
            dialog.dismiss()

            activity?.let {
                val intent = Intent(context, it)
                intent.putExtra("destination", destination)
                context.startActivity(intent)
            }
        }
        .show()
}

//fun setupInputValidation(
//    emailInput: TextInputEditText,
//    firstNameInput: TextInputEditText,
//    lastNameInput: TextInputEditText,
//    countryInput: TextInputEditText,
//    isPhoneValid: Boolean,
//    isPasswordStrong: Boolean,
//    isTermsAgreed: Boolean,
//    submitButton: Button
//) {
//    emailInput.doOnTextChanged { _, _, _, _ ->
//        val shouldEnable = shouldEnableButton(
//            emailInput,
//            firstNameInput,
//            lastNameInput,
//            countryInput,
//            isPhoneValid,
//            isPasswordStrong,
//            isTermsAgreed
//        )
//        submitButton.isEnabled = shouldEnable
//    }
//
//    firstNameInput.doOnTextChanged { _, _, _, _ ->
//        val shouldEnable = shouldEnableButton(
//            emailInput,
//            firstNameInput,
//            lastNameInput,
//            countryInput,
//            isPhoneValid,
//            isPasswordStrong,
//            isTermsAgreed
//        )
//        submitButton.isEnabled = shouldEnable
//    }
//
//    lastNameInput.doOnTextChanged { _, _, _, _ ->
//        val shouldEnable = shouldEnableButton(
//            emailInput,
//            firstNameInput,
//            lastNameInput,
//            countryInput,
//            isPhoneValid,
//            isPasswordStrong,
//            isTermsAgreed
//        )
//        submitButton.isEnabled = shouldEnable
//    }
//
//    countryInput.doOnTextChanged { _, _, _, _ ->
//        val shouldEnable = shouldEnableButton(
//            emailInput,
//            firstNameInput,
//            lastNameInput,
//            countryInput,
//            isPhoneValid,
//            isPasswordStrong,
//            isTermsAgreed
//        )
//        submitButton.isEnabled = shouldEnable
//    }
//}

//fun shouldEnableButton(
//    emailInput: TextInputEditText,
//    firstNameInput: TextInputEditText,
//    lastNameInput: TextInputEditText,
//    countryInput: TextInputEditText,
//    isPhoneValid: Boolean,
//    isPasswordStrong: Boolean,
//    isTermsAgreed: Boolean
//): Boolean {
//    val emailText = emailInput.text
//    val firstNameText = firstNameInput.text
//    val lastNameText = lastNameInput.text
//    val countryText = countryInput.text
//
//    // Check all conditions and return true if the button should be enabled, false otherwise
//    return !emailText.isNullOrBlank() &&
//            !firstNameText.isNullOrBlank() &&
//            !lastNameText.isNullOrBlank() &&
//            !countryText.isNullOrBlank() &&
//            isPhoneValid &&
//            isPasswordStrong &&
//            isTermsAgreed
//}


fun checkEmailFormat(textInputEditText: TextInputEditText): Boolean {
    val email = textInputEditText.text.toString().trim()

    val disallowedDomains = listOf("yopmail.com", "example.com")

    return Patterns.EMAIL_ADDRESS.matcher(email)
        .matches() && !disallowedDomains.any { email.endsWith("@$it") }
}

fun checkPasswordStrength(
    context: Context,
    textInput: TextInputEditText,
    lowerCasePar: TextView,
    upperCasePar: TextView,
    numberPar: TextView,
    specialCharPar: TextView,
    eightCharPar: TextView
): Boolean {
    var hasLowerCase = false
    var hasUpperCase = false
    var hasNumber = false
    var hasSpecialChar = false
    var hasEightChar = false

    textInput.doOnTextChanged { text, _, _, _ ->
        if (text != null) {

            if (text.any { it.isLowerCase() }) {
                hasLowerCase = true
                lowerCasePar.setTextColor(context.getColor(R.color.success_green))
            } else {
                lowerCasePar.setTextColor(context.getColor(R.color.error_600))
            }

            if (text.any { it.isUpperCase() }) {
                hasUpperCase = true
                upperCasePar.setTextColor(context.getColor(R.color.success_green))
            } else {
                upperCasePar.setTextColor(context.getColor(R.color.error_600))
            }

            if (text.any { it.isDigit() }) {
                hasNumber = true
                numberPar.setTextColor(context.getColor(R.color.success_green))
            } else {
                numberPar.setTextColor(context.getColor(R.color.error_600))
            }

            if (text.any { !it.isLetterOrDigit() }) {
                hasSpecialChar = true
                specialCharPar.setTextColor(context.getColor(R.color.success_green))
            } else {
                specialCharPar.setTextColor(context.getColor(R.color.error_600))
            }

            if (text.length >= 8) {
                hasEightChar = true
                eightCharPar.setTextColor(context.getColor(R.color.success_green))
            } else {
                eightCharPar.setTextColor(context.getColor(R.color.error_600))
            }

        }

    }

    return hasLowerCase && hasUpperCase && hasNumber && hasSpecialChar && hasEightChar
}


fun signupFormValidation(
    context: Context,
    emailInput: TextInputEditText,
    emailTextInputLayout: TextInputLayout,
    firstNameInput: TextInputEditText,
    lastNameInput: TextInputEditText,
    countryOfResidenceInput: TextInputEditText,
    phoneNumberInput: TextInputEditText,
    passwordInput: TextInputEditText,
    checkBox: CheckBox,
    button: Button
) {
    var isTermsAndConditionAgreed = false

    fun validateFields() {
        val email = emailInput.text.toString().trim()
        val firstName = firstNameInput.text.toString().trim()
        val lastName = lastNameInput.text.toString().trim()
        val countryOfResidence = countryOfResidenceInput.text.toString().trim()
        val phoneNumber = phoneNumberInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()

        val isEmailValid = checkEmailFormat(emailInput)
        val isNotEmpty = email.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty() && countryOfResidence.isNotEmpty() && phoneNumber.isNotEmpty() && password.isNotEmpty() && isTermsAndConditionAgreed

        if (!isEmailValid) {
            emailTextInputLayout.error = context.resources.getString(R.string.wrongly_formatted_email)
        } else {
            emailTextInputLayout.error = null
        }

        if (isNotEmpty) {
            enablePrimaryButtonState(button)
        } else {
            disablePrimaryButtonState(button)
        }
    }

    emailInput.doOnTextChanged { text, start, before, count ->
        validateFields()
    }

    firstNameInput.doOnTextChanged { text, start, before, count ->
        validateFields()
    }

    lastNameInput.doOnTextChanged { text, start, before, count ->
        validateFields()
    }

    countryOfResidenceInput.doOnTextChanged { text, start, before, count ->
        validateFields()
    }

    phoneNumberInput.doOnTextChanged { text, start, before, count ->
        validateFields()
    }

    passwordInput.doOnTextChanged { text, start, before, count ->
        validateFields()
    }

    checkBox.setOnClickListener {
        isTermsAndConditionAgreed = !isTermsAndConditionAgreed
        validateFields()
    }
}


fun compareTwoPasswordFields(
    context: Context, editText1: TextInputEditText, editText2: TextInputEditText,
    editText1Layout: TextInputLayout, editText2Layout: TextInputLayout, actionButton: MaterialButton
) {
    editText1.doOnTextChanged { text, start, before, count ->
        val newPassword = editText1.text.toString().trim()
        val confirmPassword = editText2.text.toString().trim()

        if (newPassword != confirmPassword) {
            editText2Layout.error = context.resources.getString(R.string.password_does_not_match)
            disablePrimaryButtonState(actionButton)

            if (newPassword.length < 8 || confirmPassword.length < 8) {
                disablePrimaryButtonState(actionButton)
            }

        } else {

            if (newPassword.length < 8 || newPassword.length > 20) {
                disablePrimaryButtonState(actionButton)

            } else {
                enablePrimaryButtonState(actionButton)
            }

            editText2Layout.error = null
        }
    }

    editText2.doOnTextChanged { text, start, before, count ->
        val newPassword = editText1.text.toString().trim()
        val confirmPassword = editText2.text.toString().trim()

        if (newPassword != confirmPassword) {
            editText2Layout.error = context.resources.getString(R.string.password_does_not_match)
            disablePrimaryButtonState(actionButton)

            if (newPassword.length < 8 || confirmPassword.length < 8) {
                disablePrimaryButtonState(actionButton)
            }

        } else {

            if (newPassword.length < 8 || newPassword.length > 20) {
                disablePrimaryButtonState(actionButton)

            } else {
                enablePrimaryButtonState(actionButton)
            }

            editText2Layout.error = null
        }
    }
}


fun clickableLink(
    context: Context,
    text: String,
    url: String,
    spannableTextView: TextView,
    startSpan: Int,
    endSpan: Int
) {
    try {
        val spanned = SpannableString(text)
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(p0: View) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = ContextCompat.getColor(context, R.color.dark_gray)
                ds.isUnderlineText = false
            }
        }

        spanned.setSpan(clickableSpan, startSpan, endSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableTextView.text = spanned
        spannableTextView.movementMethod = LinkMovementMethod.getInstance()
    } catch (e: Exception) {
        context.toast(description = "$e", toastType = ToastType.ERROR)
    }
}

fun copyToClipboard(context: Context, label: String, textToCopy: String?) {
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    if (textToCopy != null) {
        val clipData = ClipData.newPlainText(label, textToCopy)
        clipboardManager.setPrimaryClip(clipData)
        context.toast(
            description = context.resources.getString(R.string.copied_to_clip_board),
            toastType = ToastType.INFO
        )
    } else {
        context.toast(
            description = context.resources.getString(R.string.no_text_to_copy),
            toastType = ToastType.INFO
        )
    }

}



