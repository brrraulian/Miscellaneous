package com.example.ricardonuma.coverage;

import android.view.View;
import android.widget.Button;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class MainActivityUnitTest {

    @Test
    public void shouldHideButtonAfterClick() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);

        Button button = activity.findViewById(R.id.hide);
        button.performClick();

        assertThat(button.getVisibility(), Is.is(View.GONE));
    }
}