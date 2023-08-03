package tech.hackcity.educarts.ui.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.animation.ValueAnimator
import androidx.core.content.ContextCompat
import tech.hackcity.educarts.R

/**
 *Created by Victor Loveday on 8/1/23
 */

class CustomLineView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val inactiveColor = ContextCompat.getColor(context, R.color.gray_002)
    private val activeColor = ContextCompat.getColor(context, R.color.gray_006)

    private var lineCount = 3
    private var lineHeight = 20f
    private var lineWidth = 1000f
    private var lineSpacing = 100f
    private var cornerRadius = 10f

    private var position = 1
    private var animProgress = 0f

    private val paint = Paint()
    private val rectF = RectF()

    private val animatorDuration = 500L
    private var animator: ValueAnimator? = null

    init {
        context.obtainStyledAttributes(attrs, R.styleable.CustomLineView).apply {
            lineCount = getInt(R.styleable.CustomLineView_lineCount, 3)
            lineSpacing = getDimension(R.styleable.CustomLineView_lineSpacing, 100f)
            // Retrieve any other custom attributes you define in attrs.xml here...
            recycle()
        }

        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in 1..lineCount) {
            val lineX =
                ((lineWidth + lineSpacing) / (lineCount + 1)) * i - lineWidth / (lineCount + 1)
            val lineY = (height - lineHeight) / 2

            paint.color = inactiveColor
            rectF.set(lineX, lineY, lineX + lineWidth / (lineCount + 1), lineY + lineHeight)
            canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)

            if (i == position) {
                paint.color = activeColor
                val animatedWidth = lineWidth / (lineCount + 1) * animProgress
                rectF.set(lineX, lineY, lineX + animatedWidth, lineY + lineHeight)
                canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)
            }
        }
    }

    fun setPosition(position: Int) {
        if (position in 1..lineCount && this.position != position) {
            this.position = position
            animator?.cancel()

            animator = ValueAnimator.ofFloat(0f, 1f).apply {
                duration = animatorDuration
                interpolator = LinearInterpolator()
                addUpdateListener { animation ->
                    animProgress = animation.animatedValue as Float
                    invalidate()
                }
            }
            animator?.start()
        }
    }
}
