package com.example.koki.reactiontimer

import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.koki.reactiontimer.Util.PrefUtil
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    enum class TimerState {
        Stopped, Paused, Running
    }

    private lateinit var timer: CountDownTimer
    private var timerLengthSeconds = 0L
    private var timerState: TimerState = TimerState.Stopped

    private var secondsRemaining = 0L

    private var mediaPlayer: MediaPlayer? = null
    private var countToTrigger: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text_view_countdown.text = PrefUtil.getSecondsCountdown(this).toString()
        button_start.setOnClickListener { v ->
            startTimer()
            timerState = TimerState.Running
            updateButtons()
        }
        button_pause.setOnClickListener { v ->
            timer.cancel()
            timerState = TimerState.Paused
            updateButtons()
        }
        button_stop.setOnClickListener { v ->
            timer.cancel()
            timerState = TimerState.Stopped
            onTimerFinished()
        }
        button_settings.setOnClickListener { v ->
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    private fun startTimer() {
        timerState = TimerState.Running

        val randomMin = PrefUtil.getSecondsRandomMin(this)
        val randomMax = PrefUtil.getSecondsRandomMax(this)

        timer = object : CountDownTimer(secondsRemaining * 1000, 1000) {
            override fun onFinish() = onTimerFinished()

            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining = millisUntilFinished / 1000
                updateCountdownUI()

                if (mediaPlayer != null)
                    mediaPlayer!!.release()

                ++countToTrigger

                if (countToTrigger in randomMin..(randomMax - 1)) {
                    if ((0..1).random() == 0)
                        triggerRandomReaction()
                } else if (countToTrigger == randomMax) {
                    triggerRandomReaction()
                }

                text_view_random.text = countToTrigger.toString()
            }
        }.start()
    }

    private fun updateCountdownUI() {
        val minutesUntilFinished = secondsRemaining / 60
        val secondsInMinuteUntilFinished = secondsRemaining - minutesUntilFinished * 60
        val secondsStr = secondsInMinuteUntilFinished.toString()
        text_view_countdown.text = "$minutesUntilFinished:${
        if (secondsStr.length == 2) secondsStr
        else "0" + secondsStr}"
    }

    private fun updateButtons() {
        when (timerState) {
            TimerState.Running -> {
                button_start.isEnabled = false
                button_pause.isEnabled = true
                button_stop.isEnabled = true
            }
            TimerState.Paused -> {
                button_start.isEnabled = true
                button_pause.isEnabled = false
                button_stop.isEnabled = true
            }
            TimerState.Stopped -> {
                button_start.isEnabled = true
                button_pause.isEnabled = false
                button_stop.isEnabled = false
            }
        }
    }

    private fun onTimerFinished() {
        timerState = TimerState.Stopped

        setNewTimerLength()

        PrefUtil.setSecondsRemaining(timerLengthSeconds, this)
        secondsRemaining = timerLengthSeconds

        updateButtons()
        updateCountdownUI()
    }

    override fun onResume() {
        super.onResume()

        initTimer()
    }

    override fun onPause() {
        super.onPause()

        if (timerState == TimerState.Running) {
            timer.cancel()
        } else if (timerState == TimerState.Paused) {

        }

        PrefUtil.setPreviousTimerLengthSeconds(timerLengthSeconds, this)
        PrefUtil.setSecondsRemaining(secondsRemaining, this)
        PrefUtil.setTimerState(timerState, this)
    }

    private fun initTimer() {
        timerState = PrefUtil.getTimerState(this)

        if (timerState == TimerState.Stopped)
            setNewTimerLength()
        else
            setPreviousTimerLength()

        secondsRemaining = if (timerState == TimerState.Running || timerState == TimerState.Paused)
            PrefUtil.getSecondsRemaining(this)
        else
            timerLengthSeconds

        if (timerState == TimerState.Running)
            startTimer()

        updateButtons()
        updateCountdownUI()
    }

    private fun setPreviousTimerLength() {
        timerLengthSeconds = PrefUtil.getPreviousTimerLengthSeconds(this)
    }

    private fun setNewTimerLength() {
        val lengthInMinutes = PrefUtil.getTimerLegth(this)
        timerLengthSeconds = (lengthInMinutes * 60L)
    }

    private fun triggerRandomReaction() {
        countToTrigger = 0

        if ((0..1).random() == 0)
            mediaPlayer = MediaPlayer.create(this, R.raw.left)
        else
            mediaPlayer = MediaPlayer.create(this, R.raw.right)

        mediaPlayer!!.start()
    }

    fun IntRange.random() =
            Random().nextInt((endInclusive + 1) - start) + start
}
