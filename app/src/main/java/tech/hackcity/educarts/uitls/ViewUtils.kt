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
import android.widget.Toast
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
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import com.google.android.material.button.MaterialButton

/**
 *Created by Victor Loveday on 5/10/23
 */


fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
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

fun showShimmerLoader(shimmerFrameLayout: ShimmerFrameLayout) {
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

fun enableButtonState(button: Button) {
    button.apply {
        isEnabled = true
        setBackgroundResource(R.drawable.primary_button)
    }
}

fun disableButtonState(button: Button) {
    button.apply {
        isEnabled = true
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
        view.visibility = View.INVISIBLE
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

fun compareTwoPasswordFields(
    context: Context, editText1: TextInputEditText, editText2: TextInputEditText,
    editText1Layout: TextInputLayout, editText2Layout: TextInputLayout, actionButton: Button
) {
    editText1.doOnTextChanged { text, start, before, count ->
        val newPassword = editText1.text.toString().trim()
        val confirmPassword = editText2.text.toString().trim()

        if (newPassword != confirmPassword) {
            editText2Layout.error = context.resources.getString(R.string.password_does_not_match)
            disableButtonState(actionButton)

            if (newPassword.length < 8 || confirmPassword.length < 8) {
                disableButtonState(actionButton)
            }

        } else {

            if (newPassword.length < 8 || newPassword.length > 20) {
                disableButtonState(actionButton)

            } else {
                enableButtonState(actionButton)
            }

            editText2Layout.error = null
        }
    }

    editText2.doOnTextChanged { text, start, before, count ->
        val newPassword = editText1.text.toString().trim()
        val confirmPassword = editText2.text.toString().trim()

        if (newPassword != confirmPassword) {
            editText2Layout.error = context.resources.getString(R.string.password_does_not_match)
            disableButtonState(actionButton)

            if (newPassword.length < 8 || confirmPassword.length < 8) {
                disableButtonState(actionButton)
            }

        } else {

            if (newPassword.length < 8 || newPassword.length > 20) {
                disableButtonState(actionButton)

            } else {
                enableButtonState(actionButton)
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
        context.toast("$e")
    }
}


