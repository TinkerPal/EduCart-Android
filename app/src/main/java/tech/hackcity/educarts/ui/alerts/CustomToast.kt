package tech.hackcity.educarts.ui.alerts

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.Toast
import androidx.core.content.ContextCompat
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.CustomToastLayoutBinding

/**
 *Created by Victor Loveday on 9/12/23
 */

object CustomToast {

    fun makeText(
        context: Context,
        title: String?,
        description: String,
        duration: Int,
        toastType: ToastType
    ) {
        val binding = CustomToastLayoutBinding.inflate(LayoutInflater.from(context))
        val customToastView = binding.root

        if (!title.isNullOrEmpty()) {
            binding.titleTextView.text = title
        } else {
            binding.titleTextView.visibility = View.GONE
        }

        binding.descriptionTextView.text = description

        val titleColor: Int
        val descriptionColor: Int
        when (toastType) {
            ToastType.SUCCESS -> {
                titleColor = ContextCompat.getColor(context, R.color.success_title_color)
                descriptionColor = ContextCompat.getColor(context, R.color.success_description_color)
            }

            ToastType.ERROR -> {
                titleColor = ContextCompat.getColor(context, R.color.error_title_color)
                descriptionColor = ContextCompat.getColor(context, R.color.error_description_color)
            }

            ToastType.INFO -> {
                titleColor = ContextCompat.getColor(context, R.color.error_title_color)
                descriptionColor = ContextCompat.getColor(context, R.color.error_description_color)
            }
        }
        binding.titleTextView.setTextColor(titleColor)
        binding.descriptionTextView.setTextColor(descriptionColor)

        val bgDrawable: Drawable? = when (toastType) {
            ToastType.SUCCESS -> ContextCompat.getDrawable(context, R.drawable.success_toast_bg)
            ToastType.ERROR -> ContextCompat.getDrawable(context, R.drawable.error_toast_bg)
            ToastType.INFO -> ContextCompat.getDrawable(context, R.drawable.info_toast_bg)
        }
        customToastView.background = bgDrawable

        val customToast = Toast(context)
        customToast.duration = Toast.LENGTH_SHORT
        customToast.view = customToastView
        customToast.setGravity(
            Gravity.TOP or Gravity.CENTER_HORIZONTAL,
            0,
            context.resources.getDimensionPixelSize(R.dimen.custom_toast_margin_top)
        )

        // Calculate the height of the custom toast view
        customToastView.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        val toastHeight = customToastView.measuredHeight.toFloat()

        // Animate entrance and exit
        val entranceAnimator = createEntranceAnimator(customToastView, toastHeight)
        val exitAnimator = createExitAnimator(customToastView, toastHeight)

        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(entranceAnimator, exitAnimator)

        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                customToast.cancel()
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })

        customToast.show()
        animatorSet.start()
    }

    private fun createEntranceAnimator(view: View, toastHeight: Float): ValueAnimator {
        val animator = ValueAnimator.ofFloat(-toastHeight, 0f)
        animator.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Float
            view.translationY = value
        }
        animator.duration = 500
        animator.interpolator = AccelerateInterpolator()
        return animator
    }

    private fun createExitAnimator(view: View, toastHeight: Float): ValueAnimator {
        val animator = ValueAnimator.ofFloat(0f, -toastHeight)
        animator.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Float
            view.translationY = value
        }
        animator.duration = 500
        animator.startDelay = 2000 // Adjust the delay as needed
        animator.interpolator = AccelerateInterpolator()
        return animator
    }
}

enum class ToastType {
    SUCCESS,
    ERROR,
    INFO,
}
