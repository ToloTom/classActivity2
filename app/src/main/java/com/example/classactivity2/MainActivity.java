package com.example.classactivity2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText user_input;
    private Button button_go;
    private RequestParams params = new RequestParams();

    private String api_url = "http://api.openweathermap.org/data/2.5/weather?";
    private static AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_input = findViewById(R.id.cityName);
        button_go = findViewById(R.id.go_button);

        button_go.setOnClickListener(v ->
                launchNextActivity(v));
    }

    public void launchNextActivity(View view){

        String city = user_input.getText().toString();
        //api_url+=city+"&appid=746a94822b8753f1ce5ac64ad873b8f9";

        params.put("q", city);
        params.put("appid", "746a94822b8753f1ce5ac64ad873b8f9");
        params.put("units", "imperial");

        client.addHeader("Accept", "application/json");
        client.get(api_url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {

                    JSONObject json = new JSONObject(new String(responseBody));

                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);

                    intent.putExtra("city", json.getString("name"));
                    intent.putExtra("country", json.getJSONObject("sys").getString("country"));
                    intent.putExtra("description", json.getJSONArray("weather").getJSONObject(0).getString("description"));
                    intent.putExtra("feels_temp", json.getJSONObject("main").getString("feels_like"));
                    intent.putExtra("min_temp", json.getJSONObject("main").getString("temp_min"));
                    intent.putExtra("max_temp", json.getJSONObject("main").getString("temp_max"));

                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                try {
                    JSONObject json = new JSONObject(new String(responseBody));

                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra("city", json.getString("message"));
                    intent.putExtra("country", "");
                    intent.putExtra("description", "");
                    intent.putExtra("feels_temp", "");
                    intent.putExtra("min_temp", "");
                    intent.putExtra("max_temp", "");

                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }
}