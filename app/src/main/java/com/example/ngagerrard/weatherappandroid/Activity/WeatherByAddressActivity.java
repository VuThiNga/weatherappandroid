package com.example.ngagerrard.weatherappandroid.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.example.ngagerrard.weatherappandroid.Controller.WeatherAysnTask;
import com.example.ngagerrard.weatherappandroid.R;

/**
 * Created by Nga Gerrard on 28/03/2017.
 */
public class WeatherByAddressActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weather_current_location);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getWeatherByAddress();
    }

    private void getWeatherByAddress() {
        Intent intent = getIntent();
        //Lay dia chi tu WeatherChooseAddress gui qua
        String province = intent.getStringExtra("ADDRESS");
        WeatherAysnTask task = new WeatherAysnTask(this, province);
        task.execute();
    }

}
