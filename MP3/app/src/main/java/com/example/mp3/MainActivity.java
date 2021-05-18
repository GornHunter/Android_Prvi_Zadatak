package com.example.mp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), SecondActivity.class);
                SingleRow sr = (SingleRow) ca.getItem(position);

                intent.putExtra(EXTRA_MESSAGE, sr.getImage() + "!" + sr.getSong() + "!" + sr.getMP3());
                startActivity(intent);
            }
        });
    }
}