package com.ponthaitay.listmovie.kotlin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SimpleActivity extends AppCompatActivity {

    private TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        tvText = (TextView) findViewById(R.id.tv_text);
        tvText.setText("20Scoops CNX");
    }
}


