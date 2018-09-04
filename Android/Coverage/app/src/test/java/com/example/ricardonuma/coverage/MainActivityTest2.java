package com.example.ricardonuma.coverage;

import android.view.View;
import android.widget.Button;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class MainActivityTest2 {

    @Test
    public void shouldHideButtonAfterClick() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);

        Button button = (Button) activity.findViewById(R.id.hide);
        button.performClick();

        assertThat(button.getVisibility(), is(View.GONE));
    }
}