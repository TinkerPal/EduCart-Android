package tech.hackcity.educarts.uitls

import android.os.CountDownTimer
import java.util.concurrent.TimeUnit

/**
 *Created by Victor Loveday on 8/3/23
 */

class CountdownTimer(private var totalDuration: Long, private val interval: Long, private val listener: CountdownListener) {
    private var countDownTimer: CountDownTimer? = null
    private var isRunning = false

    interface CountdownListener {
        fun onTick(timeLeft: String)
        fun onFinish()
    }

    fun start() {
        if (isRunning) return

        countDownTimer = object : CountDownTimer(totalDuration, interval) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(minutes)
                val timeLeft = String.format("%02d:%02d", minutes, seconds)
                listener.onTick(timeLeft)
            }

            override fun onFinish() {
                isRunning = false
                listener.onFinish()
            }
        }

        countDownTimer?.start()
        isRunning = true
    }

    fun stop() {
        countDownTimer?.cancel()
        countDownTimer = null
        isRunning = false
    }

    fun isRunning(): Boolean {
        return isRunning
    }

    fun setTotalDuration(duration: Long) {
        totalDuration = duration
    }
}
