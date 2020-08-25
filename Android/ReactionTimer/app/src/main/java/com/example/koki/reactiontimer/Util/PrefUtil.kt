package com.example.koki.reactiontimer.Util

import android.content.Context
import android.preference.PreferenceManager
import com.example.koki.reactiontimer.MainActivity

class PrefUtil {
    companion object {
        fun getTimerLegth(context: Context): Int {
            return getSecondsLength(context)
        }

        private const val PREVIOUS_TIMER_LENGTH_SECONDS_ID = "com.example.koki.reactiontimer.previous_timer_length"

        fun getPreviousTimerLengthSeconds(context: Context): Long {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, 0)
        }

        fun setPreviousTimerLengthSeconds(seconds: Long, context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, seconds)
            editor.apply()
        }

        private const val TIMER_STATE_ID = "com.example.koki.reactiontimer.timer_state"

        fun getTimerState(context: Context): MainActivity.TimerState {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val ordinal = preferences.getInt(TIMER_STATE_ID, 0)
            return MainActivity.TimerState.values()[ordinal]
        }

        fun setTimerState(timerState: MainActivity.TimerState, context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            val ordinal = timerState.ordinal
            editor.putInt(TIMER_STATE_ID, ordinal)
            editor.apply()
        }

        private const val SECONDS_REMAINING_ID = "com.example.koki.reactiontimer.seconds_remaining"

        fun getSecondsRemaining(context: Context): Long {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(SECONDS_REMAINING_ID, 0)
        }

        fun setSecondsRemaining(seconds: Long, context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(SECONDS_REMAINING_ID, seconds)
            editor.apply()
        }


        private const val SECONDS_LENGTH_ID = "com.example.koki.reactiontimer.seconds_length"

        fun getSecondsLength(context: Context): Int {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getInt(SECONDS_LENGTH_ID, 60)
        }

        fun setSecondsLength(seconds: Int, context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putInt(SECONDS_LENGTH_ID, seconds)
            editor.apply()
        }


        private const val SECONDS_RANDOM_MIN_ID = "com.example.koki.reactiontimer.seconds_random_min"

        fun getSecondsRandomMin(context: Context): Int {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getInt(SECONDS_RANDOM_MIN_ID, 1)
        }

        fun setSecondsRandomMin(seconds: Int, context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putInt(SECONDS_RANDOM_MIN_ID, seconds)
            editor.apply()
        }


        private const val SECONDS_RANDOM_MAX_ID = "com.example.koki.reactiontimer.seconds_random_max"

        fun getSecondsRandomMax(context: Context): Int {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getInt(SECONDS_RANDOM_MAX_ID, 5)
        }

        fun setSecondsRandomMax(seconds: Int, context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putInt(SECONDS_RANDOM_MAX_ID, seconds)
            editor.apply()
        }
    }
}