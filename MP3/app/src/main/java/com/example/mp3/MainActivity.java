package com.example.mp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "igor";

    public final static int CONFIRMATION_REQUEST = 1;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        CustomAdapter ca = new CustomAdapter(this);

        listView.setAdapter(ca);

        //Intent intent = new Intent(this, SecondActivity.class);
        //intent.putExtra(EXTRA_MESSAGE, "asd");
        //startActivityForResult(intent, CONFIRMATION_REQUEST);
    }
}