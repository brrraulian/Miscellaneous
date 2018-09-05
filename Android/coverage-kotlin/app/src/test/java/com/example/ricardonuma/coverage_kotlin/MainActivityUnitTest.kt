package com.example.ricardonuma.coverage_kotlin

import android.view.View
import android.widget.Button
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainActivityUnitTest {

    @Test
    fun shouldHideButtonAfterClick() {
        val activity = Robolectric.setupActivity(MainActivity::class.java)

        val button = activity.findViewById<Button>(R.id.hide)
        button.performClick()

        MatcherAssert.assertThat(button.visibility, Matchers.`is`(View.GONE))
    }
}