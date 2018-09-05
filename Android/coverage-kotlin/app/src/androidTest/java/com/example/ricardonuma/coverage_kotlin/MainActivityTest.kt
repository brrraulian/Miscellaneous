package com.example.ricardonuma.coverage_kotlin

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun shouldUpdateTextAfterButtonClick() {
        Espresso.onView(ViewMatchers.withId(R.id.button)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.text)).check(ViewAssertions.matches(ViewMatchers.withText("Hello World!")))
    }
}