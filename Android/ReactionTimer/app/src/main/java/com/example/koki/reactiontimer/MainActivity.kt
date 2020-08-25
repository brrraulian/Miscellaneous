package com.example.koki.reactiontimer

import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.koki.reactiontimer.Util.PrefUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_timer.*
import kotlinx.android.synthetic.main.content_settings.*
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

        fab_start.setOnClickListener { v ->
            getSettings()
            updateContents()
            startTimer()
            timerState = TimerState.Running
            updateButtons()
        }
        fab_pause.setOnClickListener { v ->
            timer.cancel()
            timerState = TimerState.Paused
            updateButtons()
        }
        fab_stop.setOnClickListener { v ->
            timer.cancel()
            timerState = TimerState.Stopped
            onTimerFinished()
        }
    }

    private fun getSettings() {
        val hours = wp_hours.currentItemPosition
        val minutes = wp_minutes.currentItemPosition
        val seconds = wp_seconds.currentItemPosition

        var totalSeconds = (((hours * 60) + minutes) * 60) + seconds

        PrefUtil.setSecondsLength(totalSeconds, this)
    }

    private fun updateContents() {
        text_view_countdown.text = "00:00:00"
        if(content_settings.visibility == View.VISIBLE) {
            content_settings.visibility = View.GONE
            content_timer.visibility = View.VISIBLE
        } else {
            content_settings.visibility = View.VISIBLE
            content_timer.visibility = View.GONE
        }
    }

    private fun startTimer() {
        timerState = TimerState.Running

        val randomMin = PrefUtil.getSecondsRandomMin(this)
        val randomMax = PrefUtil.getSecondsRandomMax(this)
        secondsRemaining = PrefUtil.getSecondsLength(this).toLong()

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
            }
        }.start()
    }

    private fun updateCountdownUI() {
        val hoursUntilFinished = secondsRemaining / 60 / 60
        val minutesUntilFinished = (secondsRemaining / 60) - hoursUntilFinished * 60
        val secondsInMinuteUntilFinished = ((secondsRemaining - minutesUntilFinished * 60) - hoursUntilFinished * 60 * 60)
        val hoursStr = hoursUntilFinished.toString()
        val minutesStr = minutesUntilFinished.toString()
        val secondsStr = secondsInMinuteUntilFinished.toString()
        text_view_countdown.text = "${
        if (hoursStr.length == 2) hoursStr
        else "0" + hoursStr}:${
        if (minutesStr.length == 2) minutesStr
        else "0" + minutesStr}:${
        if (secondsStr.length == 2) secondsStr
        else "0" + secondsStr}"
//        progress_countdown.progress = (timerLengthSeconds - secondsRemaining).toInt()
    }

    private fun updateButtons() {
        when (timerState) {
            TimerState.Running -> {
                fab_start.isEnabled = false
                fab_pause.isEnabled = true
                fab_stop.isEnabled = true
            }
            TimerState.Paused -> {
                fab_start.isEnabled = true
                fab_pause.isEnabled = false
                fab_stop.isEnabled = true
            }
            TimerState.Stopped -> {
                fab_start.isEnabled = true
                fab_pause.isEnabled = false
                fab_stop.isEnabled = false
            }
        }
    }

    private fun onTimerFinished() {
        timerState = TimerState.Stopped

        setNewTimerLength()

//        progress_countdown.progress = 0

        PrefUtil.setSecondsRemaining(timerLengthSeconds, this)
        secondsRemaining = timerLengthSeconds

        updateButtons()
        updateCountdownUI()
        updateContents()
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
//        progress_countdown.max = timerLengthSeconds.toInt()
    }

    private fun setNewTimerLength() {
        val lengthInMinutes = PrefUtil.getTimerLegth(this)
        timerLengthSeconds = (lengthInMinutes * 60L)
//        progress_countdown.max = timerLengthSeconds.toInt()
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_timer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
