package tech.hackcity.educarts.uitls

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import tech.hackcity.educarts.R

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
    toolbar.visibility = View.GONE
}

fun showToolBar(toolbar: MaterialToolbar) {
    toolbar.visibility = View.VISIBLE
}


