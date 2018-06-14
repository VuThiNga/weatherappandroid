package com.example.ngagerrard.weatherappandroid.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.ngagerrard.weatherappandroid.Controller.WeatherAysnTask;
import com.example.ngagerrard.weatherappandroid.R;

/**
 * Created by Nga Gerrard on 28/03/2017.
 */
public class WeatherCurrentLocationActivity extends AppCompatActivity{

    private SharedPreferences saveLocation;
    private SharedPreferences.Editor editorLocation;
    String location;
    private String coordinates[] = new String[2];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_current_location);

        saveLocation = getSharedPreferences(MainActivity.LOCATION, MODE_PRIVATE);
        editorLocation = saveLocation.edit();
        location = saveLocation.getString(MainActivity.LOCATION, "21.0066156-105.8474236");
        if(!location.equals("") || location != null){
            coordinates = location.split("-");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getCurrentLocation();
    }

    private void getCurrentLocation() {

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location lastLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));
        //Toast.makeText(WeatherCurrentLocationActivity.this, "aaa", Toast.LENGTH_LONG).show();
        if (lastLocation != null)
        {
            //ta lấy được thì truyền vĩ độ, kinh độ để xem thời tiết
            WeatherAysnTask task=new WeatherAysnTask(this,lastLocation.getLatitude(),lastLocation.getLongitude());
            task.execute();
        }else{
            //ta lấy được thì truyền vĩ độ, kinh độ để xem thời tiết
            WeatherAysnTask task=new WeatherAysnTask(this,Double.parseDouble(coordinates[0]),Double.parseDouble(coordinates[1]));
            task.execute();
        }
    }

}
