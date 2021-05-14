package com.example.mp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //Intent intent = getIntent();
        //String m = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        //TextView textView = (TextView) findViewById(R.id.textView);
        //textView.setText(m);
    }
}