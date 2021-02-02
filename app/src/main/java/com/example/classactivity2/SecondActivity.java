package com.example.classactivity2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class SecondActivity extends AppCompatActivity {

    private TextView name;
    private TextView information;
    private TextView max;
    private TextView min;
    private TextView feels;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        name = findViewById(R.id.nameCity);
        information = findViewById(R.id.description);
        max = findViewById(R.id.maxTemp);
        min = findViewById(R.id.minTemp);
        feels = findViewById(R.id.feelsLike);

        Intent intent = getIntent();

        if(intent.getStringExtra("city").equals("city not found")){
            name.setText(intent.getStringExtra("city"));
        }
        else{
            name.setText(intent.getStringExtra("city") + ", " + intent.getStringExtra("country"));
            information.setText(intent.getStringExtra("description"));
            max.setText("High       " + intent.getStringExtra("max_temp") + "℉");
            min.setText("Low        " + intent.getStringExtra("min_temp") + "℉");
            feels.setText("Feels Like       " + intent.getStringExtra("feels_temp") + "℉");
        }



    }
}
