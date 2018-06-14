package com.example.ngagerrard.weatherappandroid.Activity;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.ngagerrard.weatherappandroid.Controller.WeatherAysnTask;
import com.example.ngagerrard.weatherappandroid.R;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences saveLocation;
    private SharedPreferences.Editor editorLocation;
    String location = "21.0066156-105.8474236";
    private String coordinates[] = new String[2];
    public static final String LOCATION = "location";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        saveLocation = getSharedPreferences(LOCATION, MODE_PRIVATE);
        editorLocation = saveLocation.edit();
        location = saveLocation.getString(LOCATION, "21.0066156-105.8474236");
        if (!location.equals("") || location != null) {
            coordinates = location.split("-");
        }
        getWeatherCurrentLocation();
        //show notification
    }

    @Override
    protected void onResume() {
        super.onResume();
        //getWeatherCurrentLocation();
    }

    private void getWeatherCurrentLocation() {
        if (!isNetworkConnected()) {
            Toast.makeText(MainActivity.this, "Bạn chưa kết nối Wifi hoặc 3G", Toast.LENGTH_LONG).show();
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Log.d("abc", "1");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Log.d("abc", "2");
        Location lastLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));
        //Toast.makeText(WeatherCurrentLocationActivity.this, "aaa", Toast.LENGTH_LONG).show();
        if (lastLocation != null)
        {
            Log.d("abc", "not null : "+lastLocation.getLatitude()+" : "+coordinates[0]+":"+lastLocation.getLongitude()+" : "+coordinates[1]);
            location = lastLocation.getLatitude()+"-"+lastLocation.getLongitude();
            editorLocation.clear();
            editorLocation.putString(LOCATION, location);
            editorLocation.commit();


            //ta lấy được thì truyền vĩ độ, kinh độ để xem thời tiết
            WeatherAysnTask task=new WeatherAysnTask(this,lastLocation.getLatitude(),lastLocation.getLongitude(), 1);
            task.execute();
        }else{
            Log.d("abc", "null " + coordinates);
            //ta lấy được thì truyền vĩ độ, kinh độ để xem thời tiết
            WeatherAysnTask task=new WeatherAysnTask(this,Double.parseDouble(coordinates[0]),Double.parseDouble(coordinates[1]), 1);
            task.execute();
         }
    }

    //Su kien nhan item menu thời tiết các tỉnh thành khác
    public void openChooseAddressAct(View view){
        if(!isNetworkConnected())
        {
            Toast.makeText(MainActivity.this,"Bạn chưa kết nối Wifi hoặc 3G",Toast.LENGTH_LONG).show();
            return;
        }
        Intent i = new Intent(MainActivity.this, WeatherChooseAddress.class);
        startActivity(i);
    }

    //Su kien nhan item menu thoi tiet 7 ngay tiep theo
    public void openSevenDayDailyWeather(View view){
        if(!isNetworkConnected())
        {
            Toast.makeText(MainActivity.this,"Bạn chưa kết nối Wifi hoặc 3G",Toast.LENGTH_LONG).show();
            return;
        }
        Intent i = new Intent(MainActivity.this, WeatherSevenDay.class);
        startActivity(i);
    }

    //Su kien nhan item xem thoi tiet chi tiet
    public void openWeatherDetail(View view){
        if(!isNetworkConnected())
        {
            Toast.makeText(MainActivity.this,"Bạn chưa kết nối Wifi hoặc 3G",Toast.LENGTH_LONG).show();
            return;
        }
        Intent i = new Intent(MainActivity.this, WeatherCurrentLocationActivity.class);
        startActivity(i);
    }

    //Su kien nhan item xem thong tin ung dung
    public void openInformation(View view){
        Intent i = new Intent(MainActivity.this, InformationAppActivity.class);
        startActivity(i);
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }

    //show Notification
    public void showNotification(View view){
        if(!isNetworkConnected())
        {
            Toast.makeText(MainActivity.this,"Bạn chưa kết nối Wifi hoặc 3G",Toast.LENGTH_LONG).show();
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Log.d("abc", "1");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Log.d("abc", "2");
        Location lastLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));
        //Toast.makeText(WeatherCurrentLocationActivity.this, "aaa", Toast.LENGTH_LONG).show();
        if (lastLocation != null)
        {
            Log.d("abc", "not null : "+lastLocation.getLatitude()+" : "+coordinates[0]+":"+lastLocation.getLongitude()+" : "+coordinates[1]);
            location = lastLocation.getLatitude()+"-"+lastLocation.getLongitude();
            editorLocation.clear();
            editorLocation.putString(LOCATION, location);
            editorLocation.commit();


            //ta lấy được thì truyền vĩ độ, kinh độ để xem thời tiết
            WeatherAysnTask task=new WeatherAysnTask(this,lastLocation.getLatitude(),lastLocation.getLongitude(), 2);
            task.execute();
        }else{
            Log.d("abc", "null " + coordinates);
            //ta lấy được thì truyền vĩ độ, kinh độ để xem thời tiết
            WeatherAysnTask task=new WeatherAysnTask(this,Double.parseDouble(coordinates[0]),Double.parseDouble(coordinates[1]), 2);
            task.execute();
        }
//        WeatherAysnTask task = new WeatherAysnTask(this, "Hà Nội", 2);
//        task.execute();
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//        builder.setSmallIcon(R.drawable.anh2);
//        builder.setContentTitle("Thời tiết Hà Nội");
//        builder.setContentText("T");
//        Intent intent = new Intent(this, WeatherCurrentLocationActivity.class);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(WeatherCurrentLocationActivity.class);
//        stackBuilder.addNextIntent(intent);
//        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);
//        NotificationManager NM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        NM.notify(0, builder.build());

    }
}
