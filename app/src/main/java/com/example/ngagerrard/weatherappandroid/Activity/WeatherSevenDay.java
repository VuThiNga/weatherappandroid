package com.example.ngagerrard.weatherappandroid.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ngagerrard.weatherappandroid.Controller.WeatherAysnTask;
import com.example.ngagerrard.weatherappandroid.Controller.WeatherAysnTask_daily;
import com.example.ngagerrard.weatherappandroid.Model.WeatherDailyCustomDisplay;
import com.example.ngagerrard.weatherappandroid.R;

import java.util.ArrayList;

/**
 * Created by Nga Gerrard on 07/04/2017.
 */
public class WeatherSevenDay extends AppCompatActivity {

    String thanhPho = "";
    AutoCompleteTextView txtSearchDaily;
    Button btnSearchDaily;
    String[] arrayProvince;
    private SharedPreferences saveLocation;
    private SharedPreferences.Editor editorLocation;
    String location;
    private String coordinates[] = new String[2];

    //Adapter cua AutocompleteTextView
    ArrayAdapter<String> adapterAuCo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weather7_daily);

        saveLocation = getSharedPreferences(MainActivity.LOCATION, MODE_PRIVATE);
        editorLocation = saveLocation.edit();
        location = saveLocation.getString(MainActivity.LOCATION, "21.0066156-105.8474236");
        if(!location.equals("") || location != null){
            coordinates = location.split("-");
        }

        Intent intent = this.getIntent();
        if (intent.hasExtra("thanhpho")) {
            thanhPho = intent.getStringExtra("thanhpho");
        }

        //anh xa
        txtSearchDaily = (AutoCompleteTextView)findViewById(R.id.txtACSearchDaily);
        btnSearchDaily = (Button)findViewById(R.id.btnSearchDaily);
        arrayProvince = getResources().getStringArray(R.array.arrProvince);

        //adapter cho autocompleteTextView
        adapterAuCo = new ArrayAdapter<String>(WeatherSevenDay.this, android.R.layout.simple_list_item_1, arrayProvince);
        txtSearchDaily.setAdapter(adapterAuCo);
        addEvents();

    }


    private void addEvents() {
        txtSearchDaily.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //su kien nhan button
        btnSearchDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = "";
                city = txtSearchDaily.getText().toString();
                if(city.equals("")){
                    final AlertDialog.Builder builder = new AlertDialog.Builder(WeatherSevenDay.this);
                    builder.setTitle("Chú ý!");
                    builder.setMessage("Bạn chưa lựa chọn tỉnh thành");
                    builder.show();
                }else {
                    WeatherAysnTask_daily task = new WeatherAysnTask_daily(WeatherSevenDay.this, city);
                    task.execute();
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWeatherSevenDay();
    }

    private void getWeatherSevenDay() {
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
        if(!thanhPho.equals("") && !thanhPho.equals(null)){
            WeatherAysnTask_daily task=new WeatherAysnTask_daily(this,thanhPho);
            task.execute();
            return;
        }
        else if (lastLocation != null)
        {
            //ta lấy được thì truyền vĩ độ, kinh độ để xem thời tiết
            WeatherAysnTask_daily task=new WeatherAysnTask_daily(this,lastLocation.getLatitude(),lastLocation.getLongitude());
            task.execute();

        }else if(lastLocation == null){
            //ta lấy được thì truyền vĩ độ, kinh độ để xem thời tiết
            WeatherAysnTask_daily task=new WeatherAysnTask_daily(this,Double.parseDouble(coordinates[0]),Double.parseDouble(coordinates[1]));
            task.execute();
        }

//        String province = "Thái Bình";
//        WeatherAysnTask_daily task = new WeatherAysnTask_daily(this, province);
//        task.execute();
    }

}
