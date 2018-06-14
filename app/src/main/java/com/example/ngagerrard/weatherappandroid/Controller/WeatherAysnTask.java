package com.example.ngagerrard.weatherappandroid.Controller;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ngagerrard.weatherappandroid.Activity.MainActivity;
import com.example.ngagerrard.weatherappandroid.Activity.WeatherCurrentLocationActivity;
import com.example.ngagerrard.weatherappandroid.Adapter.MyWindowInfoAdapter;
import com.example.ngagerrard.weatherappandroid.Model.OpenWeatherJSon;
import com.example.ngagerrard.weatherappandroid.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Nga Gerrard on 28/03/2017.
 */
public class WeatherAysnTask extends AsyncTask<Void, Void, OpenWeatherJSon>{

    ProgressDialog dialog;
    Activity activity;
    TypePrediction typePrediction;
    String q;
    double longitude;
    double latitude;
    int a= 0;
    NumberFormat format = new DecimalFormat("#0.0");

    //Bitmap myBitmap = null;
    String idIcon;

    //double latitude, longitude;

    //Contructor luc thoi tiet thoi dia diem bat ki
    public WeatherAysnTask(Activity activity, String q){
        this.activity = activity;
        this.q = q;
        typePrediction = TypePrediction.ADDRESS_NAME;
        this.dialog = new ProgressDialog(activity);

        this.dialog.setMessage("Please wait....");
        this.dialog.setCancelable(true);
//        dialog.show();
    }

    public WeatherAysnTask(Activity activity, String q, int a){
        this.activity = activity;
        this.a = a;
        this.q = q;
        typePrediction = TypePrediction.ADDRESS_NAME;
        this.dialog = new ProgressDialog(activity);

        this.dialog.setMessage("Please wait....");
        this.dialog.setCancelable(true);
//        dialog.show();
    }
    //Constructor lay thoi tiet theo toa do bat ki
    public WeatherAysnTask(Activity activity, double latitude, double longitude){
        this.activity = activity;
        typePrediction = TypePrediction.LATITUDE_LONGITUDE;
        this.longitude = longitude;
        this.latitude = latitude;

        this.dialog = new ProgressDialog(activity);
        this.dialog.setMessage("Please wait....");
        this.dialog.setCancelable(true);
//        dialog.show();
    }

    //Contructor duoc su dung cho activity dau tien khi vao ung dung
    public WeatherAysnTask(Activity activity, double latitude, double longitude, int a){
        this.a = a;
        this.activity = activity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.typePrediction = TypePrediction.LATITUDE_LONGITUDE;
        this.dialog=new ProgressDialog(activity);

        this.dialog.setMessage("please wait . . . . ");

        this.dialog.setCancelable(true);
    }
    //Constructor lay thong tin thoi tiet theo toa do bat ki tren ban do
//    public WeatherAysnTask(Marker marker, GoogleMap map, Activity activity, double latitude, double longitude ){
//        this(activity, latitude, longitude);
//        this.marker=marker;
//        this.map=map;
//    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.dialog.show();
    }

    @Override
    protected OpenWeatherJSon doInBackground(Void... params) {
        OpenWeatherJSon openWeatherJSon = null;
        if(typePrediction == TypePrediction.ADDRESS_NAME){
            openWeatherJSon = OpenWeatherMapAPI.prediction(q);
        }else {
            openWeatherJSon = OpenWeatherMapAPI.prediction(latitude, longitude);
        }


        try {
            idIcon = openWeatherJSon.getWeather().get(0).getIcon().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return openWeatherJSon;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(OpenWeatherJSon openWeatherJSon) {
        super.onPostExecute(openWeatherJSon);
//        if (map != null) {
//            map.setInfoWindowAdapter(new MyWindowInfoAdapter(openWeatherJSon, myBitmap, marker, this.activity, latitude, longitude));
//            marker.showInfoWindow();
//            this.dialog.dismiss();
//            return;
//        }
        if(a == 2){
            String location = "";
            if(typePrediction == TypePrediction.LATITUDE_LONGITUDE) {

                location = openWeatherJSon.getName()+ " - " + openWeatherJSon.getSys().getCountry();
            }else if(typePrediction == TypePrediction.ADDRESS_NAME){
                location = q;
            }
            String temp = format.format(openWeatherJSon.getMain().getTemp() - 273.15) + "°C";
            String msg = translate(openWeatherJSon.getWeather().get(0).getDescription());
            Intent intent = new Intent();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(activity);
            if (idIcon.equals("01d")) {
                builder.setSmallIcon(R.drawable.d01);
            }
            else if (idIcon.equals("01n")) {
                builder.setSmallIcon(R.drawable.n01);
            }
            else if (idIcon.equals("02d")) {
                builder.setSmallIcon(R.drawable.d02);
            }
            else if (idIcon.equals("02n")) {
                builder.setSmallIcon(R.drawable.n02);
            }
            else if (idIcon.equals("03d") || idIcon.equals("03n")) {
                builder.setSmallIcon(R.drawable.dn03);
            }
            else if (idIcon.equals("04d") || idIcon.equals("04n")) {
                builder.setSmallIcon(R.drawable.dn04);
            }
            else if (idIcon.equals("09d") || idIcon.equals("09n")) {
                builder.setSmallIcon(R.drawable.dn09);
            }
            else if (idIcon.equals("10d")) {
                builder.setSmallIcon(R.drawable.d10);
            }
            else if (idIcon.equals("10n")) {
                builder.setSmallIcon(R.drawable.n10);
            }
            else if (idIcon.equals("11d") || idIcon.equals("11n"))
                builder.setSmallIcon(R.drawable.dn11);
            else if (idIcon.equals("13d") || idIcon.equals("13n"))
                builder.setSmallIcon(R.drawable.dn13);
            else if (idIcon.equals("50d") || idIcon.equals("50n")) {
                builder.setSmallIcon(R.drawable.dn50);
            }

            builder.setContentTitle("Thời tiết " + location);
            builder.setContentText("Nhiệt độ: " + temp + "   " + msg);
            Intent intent2 = new Intent(activity, WeatherCurrentLocationActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(activity);
            stackBuilder.addParentStack(WeatherCurrentLocationActivity.class);
            stackBuilder.addNextIntent(intent2);
            PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            NotificationManager NM = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
            NM.notify(0, builder.build());
            this.dialog.dismiss();

        }
        if(a != 1 && a!= 2){
            //anh xa
            TextView txtAddressCurrent = (TextView)activity.findViewById(R.id.txtLocal);
            TextView txtTemp = (TextView)activity.findViewById(R.id.txtTemp);
            ImageView imageView = (ImageView) activity.findViewById(R.id.imgIcon);
            TextView txtMsg = (TextView)activity.findViewById(R.id.txtMsg);

            final TextView txtMaxTemp = (TextView)activity.findViewById(R.id.txtMaxTemp);
            final TextView txtMinTemp = (TextView)activity.findViewById(R.id.txtMinTemp);
            TextView txtPresure = (TextView)activity.findViewById(R.id.txtPressure);
            TextView txtHumidity = (TextView)activity.findViewById(R.id.txtHumidity);
            TextView txtWind = (TextView)activity.findViewById(R.id.txtWind);
            //TextView txtVisibility = (TextView)activity.findViewById(R.id.txtVisibility);
            final TextView txtSunrise = (TextView)activity.findViewById(R.id.txtSunrise);
            final TextView txtSunset = (TextView)activity.findViewById(R.id.txtSunset);
            TextView txtDay = (TextView)activity.findViewById(R.id.txtDay);



            String temp = format.format(openWeatherJSon.getMain().getTemp() - 273.15) + "°C";
            String msg = translate(openWeatherJSon.getWeather().get(0).getDescription());
            String tempMax = format.format(openWeatherJSon.getMain().getTemp_max() - 273.15) + "°C";
            String tempMin = format.format(openWeatherJSon.getMain().getTemp_min() - 273.15) + "°C";
            String pressure = openWeatherJSon.getMain().getPressure() + "hPa";
            String humidity = openWeatherJSon.getMain().getHumidity() + "%";
            String wind = format.format(openWeatherJSon.getWind().getSpeed()) + "m/s";


            Date timeSunmax = new Date(openWeatherJSon.getDt() * 1000);
            String sunmax = timeSunmax.getHours() + ":" + timeSunmax.getMinutes();

            //Binh minh, hoang hon
            Date timeSunrise = new Date(openWeatherJSon.getSys().getSunrise() * 1000);
            String sunrise = timeSunrise.getHours() + ":" + timeSunrise.getMinutes() + " AM";
            Date timeSunSet = new Date(openWeatherJSon.getSys().getSunset() * 1000);
            String sunset = (timeSunSet.getHours() - 12) + ":" + timeSunSet.getMinutes() + " PM";

            //add vao widget

            //Background
            LinearLayout linearLayout = (LinearLayout)activity.findViewById(R.id.linearLayoutDetail);
            //imageView.setImageBitmap(myBitmap);
            if (idIcon.equals("01d")) {
                imageView.setImageResource(R.drawable.d01);
                linearLayout.setBackgroundResource(R.color.colorClear);
            }
            else if (idIcon.equals("01n")) {
                imageView.setImageResource(R.drawable.n01);
                linearLayout.setBackgroundResource(R.color.colorClearNight);
            }
            else if (idIcon.equals("02d")) {
                imageView.setImageResource(R.drawable.d02);
                linearLayout.setBackgroundResource(R.color.colorClear);
            }
            else if (idIcon.equals("02n")) {
                imageView.setImageResource(R.drawable.n02);
                linearLayout.setBackgroundResource(R.color.colorClearNight);
            }
            else if (idIcon.equals("03d") || idIcon.equals("03n")) {
                imageView.setImageResource(R.drawable.dn03);
                linearLayout.setBackgroundResource(R.color.colorCloud);
            }
            else if (idIcon.equals("04d") || idIcon.equals("04n")) {
                imageView.setImageResource(R.drawable.dn04);
                linearLayout.setBackgroundResource(R.color.colorCloud);
            }
            else if (idIcon.equals("09d") || idIcon.equals("09n")) {
                imageView.setImageResource(R.drawable.dn09);
                linearLayout.setBackgroundResource(R.color.colorRain);
            }
            else if (idIcon.equals("10d")) {
                imageView.setImageResource(R.drawable.d10);
                linearLayout.setBackgroundResource(R.color.colorRain);
            }
            else if (idIcon.equals("10n")) {
                imageView.setImageResource(R.drawable.n10);
                linearLayout.setBackgroundResource(R.color.colorRain);
            }
            else if (idIcon.equals("11d") || idIcon.equals("11n"))
                imageView.setImageResource(R.drawable.dn11);
            else if (idIcon.equals("13d") || idIcon.equals("13n"))
                imageView.setImageResource(R.drawable.dn13);
            else if (idIcon.equals("50d") || idIcon.equals("50n")) {
                imageView.setImageResource(R.drawable.dn50);
                linearLayout.setBackgroundResource(R.color.colorCloud);
            }

            //Animation
            Animation animation1 = AnimationUtils.loadAnimation(activity, R.anim.scale);
            imageView.setAnimation(animation1);
            Animation animation2 = AnimationUtils.loadAnimation(activity, R.anim.scale);
            txtMsg.setAnimation(animation2);
            txtTemp.setText(temp);
            txtMsg.setText(msg);
            txtMaxTemp.setText(tempMax);
            txtMinTemp.setText(tempMin);
            txtPresure.setText(pressure);
            txtHumidity.setText(humidity);
            txtWind.setText(wind);
            //txtVisibility.setText(sunmax);
            txtSunrise.setText(sunrise);
            txtSunset.setText(sunset);
            //txtAddressCurrent.setText(q);

            //Ngay thang
            Calendar now = Calendar.getInstance();
            String date = "";
            date = "Ngày " + now.get(Calendar.DATE) + " tháng " + (now.get(Calendar.MONTH) + 1) + ", "
                    + sunmax;
            txtDay.setText(date);

            txtSunrise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(activity, "Bình minh: " + txtSunrise.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });

            txtSunset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(activity, "Hoàng hôn: " + txtSunset.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });

//            if(typePrediction == TypePrediction.LATITUDE_LONGITUDE) {
//                txtAddressCurrent.setText(openWeatherJSon.getName()+ " - " + openWeatherJSon.getSys().getCountry());
//            }else if(typePrediction == TypePrediction.ADDRESS_NAME){
//                txtAddressCurrent.setText(q);
//            }
            try {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this.activity, Locale.getDefault());
                if (typePrediction == TypePrediction.LATITUDE_LONGITUDE)
                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                else {
                    addresses = geocoder.getFromLocationName(q, 1);
                }
                Address address = null;
                if (addresses.size() > 0)
                    address = addresses.get(0);
                if (address != null) {
                    if (typePrediction == TypePrediction.LATITUDE_LONGITUDE) {
                        txtAddressCurrent.setText(address.getAddressLine(0));
                    } else
                        txtAddressCurrent.setText(q);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            this.dialog.dismiss();
        }

        if(a == 1){
            TextView txtDecriptionMain = (TextView)activity.findViewById(R.id.txtDecriptionMain);
            TextView txtAddressMain = (TextView)activity.findViewById(R.id.txtCurrentAddressNameMain);
            TextView txtTempMain = (TextView)activity.findViewById(R.id.txtTemperatureMain);
            TextView timeMain = (TextView)activity.findViewById(R.id.txtTimeMain);
            ImageView imgMain = (ImageView)activity.findViewById(R.id.imgWeatherMain);

            //String temp = format.format(openWeatherJSon.getMain().getTemp() - 273.15);
            String temperature = format.format(openWeatherJSon.getMain().getTemp() - 273.15) + "°C";
            String mesg = translate(openWeatherJSon.getWeather().get(0).getDescription());


            txtDecriptionMain.setText(mesg);
            txtTempMain.setText(temperature);
            //Background
            LinearLayout linearLayout2 = (LinearLayout)activity.findViewById(R.id.linearLayoutMain);

           // imgMain.setImageBitmap(myBitmap);
            if (idIcon.equals("01d")) {
                imgMain.setImageResource(R.drawable.d01);
                linearLayout2.setBackgroundResource(R.color.colorClear);
            }
            else if (idIcon.equals("01n")) {
                imgMain.setImageResource(R.drawable.n01);
                linearLayout2.setBackgroundResource(R.color.colorClearNight);
            }
            else if (idIcon.equals("02d")) {
                imgMain.setImageResource(R.drawable.d02);
                linearLayout2.setBackgroundResource(R.color.colorClear);
            }
            else if (idIcon.equals("02n")) {
                imgMain.setImageResource(R.drawable.n02);
                linearLayout2.setBackgroundResource(R.color.colorClearNight);
            }
            else if (idIcon.equals("03d") || idIcon.equals("03n")){
                imgMain.setImageResource(R.drawable.dn03);
                linearLayout2.setBackgroundResource(R.color.colorCloud);
            }
            else if (idIcon.equals("04d") || idIcon.equals("04n")) {
                imgMain.setImageResource(R.drawable.dn04);
                linearLayout2.setBackgroundResource(R.color.colorCloud);
            }
            else if (idIcon.equals("09d") || idIcon.equals("09n")) {
                imgMain.setImageResource(R.drawable.dn09);
                linearLayout2.setBackgroundResource(R.color.colorRain);
            }
            else if (idIcon.equals("10d")) {
                imgMain.setImageResource(R.drawable.d10);
                linearLayout2.setBackgroundResource(R.color.colorRain);
            }
            else if (idIcon.equals("10n")) {
                imgMain.setImageResource(R.drawable.n10);
                linearLayout2.setBackgroundResource(R.color.colorRain);
            }
            else if (idIcon.equals("11d") || idIcon.equals("11n"))
                imgMain.setImageResource(R.drawable.dn11);
            else if (idIcon.equals("13d") || idIcon.equals("13n"))
                imgMain.setImageResource(R.drawable.dn13);
            else if (idIcon.equals("50d") || idIcon.equals("50n")) {
                imgMain.setImageResource(R.drawable.dn50);
                linearLayout2.setBackgroundResource(R.color.colorCloud);
            }

            //Animation
            Animation animation = AnimationUtils.loadAnimation(activity, R.anim.scale);
            imgMain.setAnimation(animation);

            Date ngayHienTai = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            //SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd-MM-yyyy-hh");
            String ngayGio = simpleDateFormat.format(ngayHienTai);
            timeMain.setText(ngayGio);

//            if(typePrediction == TypePrediction.LATITUDE_LONGITUDE) {
//                txtAddressMain.setText(openWeatherJSon.getName()+ " - " + openWeatherJSon.getSys().getCountry());
//            }else if(typePrediction == TypePrediction.ADDRESS_NAME){
//                txtAddressMain.setText(q);
//            }

            try {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this.activity, Locale.getDefault());
                if (typePrediction == TypePrediction.LATITUDE_LONGITUDE)
                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                else {
                    addresses = geocoder.getFromLocationName(q, 1);
                }
                Address address = null;
                if (addresses.size() > 0)
                    address = addresses.get(0);
                if (address != null) {
                    if (typePrediction == TypePrediction.LATITUDE_LONGITUDE) {
                        txtAddressMain.setText(address.getAddressLine(0));
                    } else
                        txtAddressMain.setText(q);
                /*String city = address.getLocality();
                String state = address.getAdminArea();
                String country = address.getCountryName();
                String postalCode = address.getPostalCode();
                String knownName = address.getFeatureName();*/
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            this.dialog.dismiss();
        }

    }
    public String translate(String textEn){
        String textVN = textEn;
        if(textEn.toLowerCase().equals("sky is clear") || textEn.toLowerCase().equals("clear sky")) textVN = "Quang đãng";
        else if(textEn.toLowerCase().equals("scattered clouds")) textVN = "Mây rải rác";
        else if(textEn.toLowerCase().equals("few clouds")) textVN = "Ít mây";
        else if(textEn.toLowerCase().equals("broken clouds")) textVN = "Nhiều mây";
        else if(textEn.toLowerCase().equals("shower rain")) textVN = "Mưa phùn";
        else if(textEn.toLowerCase().equals("rain")) textVN = "Mưa";
        else if(textEn.toLowerCase().equals("thunderstorm")) textVN = "Mưa dông";
        else if(textEn.toLowerCase().equals("snow")) textVN = "Tuyết";
        else if(textEn.toLowerCase().equals("mist")) textVN = "Sương mù";
        else if(textEn.toLowerCase().equals("light rain")) textVN = "Mưa nhỏ";
        else if(textEn.toLowerCase().equals("moderate rain")) textVN = "Mưa vừa";
        else if(textEn.toLowerCase().equals("heavy intensity rain")) textVN = "Mưa nặng hạt";
        else if(textEn.toLowerCase().equals("very heavy rain")) textVN = "Mưa lớn";
        else if(textEn.toLowerCase().equals("extreme rain")) textVN = "Mưa cực lớn";
        else if(textEn.toLowerCase().equals("freezing rain")) textVN = "Mưa lạnh";
        else if(textEn.toLowerCase().equals("ragged shower rain")) textVN = "Mưa phùn không đều";

        else if(textEn.toLowerCase().equals("light snow")) textVN = "Tuyết nhẹ";
        else if(textEn.toLowerCase().equals("snow")) textVN = "Tuyết";
        else if(textEn.toLowerCase().equals("heavy snow")) textVN = "Tuyết dày";
        else if(textEn.toLowerCase().equals("sleet")) textVN = "Mưa tuyết";
        else if(textEn.toLowerCase().equals("shower sleet")) textVN = "Mưa đá";
        else if(textEn.toLowerCase().equals("rain and snow")) textVN = "Mưa tuyết";
        else if(textEn.toLowerCase().equals("overcast clouds")) textVN = "Mây u ám";
        else if(textEn.toLowerCase().equals("cold")) textVN = "Lạnh lẽo";
        else if(textEn.toLowerCase().equals("hurricane")) textVN = "Bão tố";
        else if(textEn.toLowerCase().equals("hot")) textVN = "Nóng bức";
        else if(textEn.toLowerCase().equals("windy")) textVN = "Gió";
        else if(textEn.toLowerCase().equals("hail")) textVN = "Mưa đá";


        return textVN;
    }
}
