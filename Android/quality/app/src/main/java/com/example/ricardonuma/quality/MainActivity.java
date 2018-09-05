package com.example.ricardonuma.quality;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * A Container is an object that contains other objects.
 * @author Ricardo Numa
 * @version 0.1
 * @since 0.1
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.hide).setOnClickListener(this);
        mTextView = (TextView) findViewById(R.id.text);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            mTextView.setText("Hello World!");
        } else {
            v.setVisibility(View.GONE);
        }
    }
}
