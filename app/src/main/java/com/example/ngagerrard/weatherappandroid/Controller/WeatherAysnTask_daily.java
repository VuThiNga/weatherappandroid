package com.example.ngagerrard.weatherappandroid.Controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ngagerrard.weatherappandroid.Adapter.AdapterSevenDayWeather;
import com.example.ngagerrard.weatherappandroid.Model.OpenWeatherJSon;
import com.example.ngagerrard.weatherappandroid.Model.OpenWeatherJSon_daily;
import com.example.ngagerrard.weatherappandroid.Model.WeatherDailyCustomDisplay;
import com.example.ngagerrard.weatherappandroid.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Nga Gerrard on 07/04/2017.
 */
public class WeatherAysnTask_daily extends AsyncTask<Void, Void, OpenWeatherJSon_daily> {
    final static int CNT = 7;
    ProgressDialog dialog;
    Activity activity;
    TypePrediction typePrediction;
    String q;
    NumberFormat format = new DecimalFormat("#0.0");
    double latitude, longitude;
    Bitmap myBitmap = null;
    String idIcon[] = new String[CNT];
    int Icon[] = new int[CNT];

    TextView txtCity;
    ListView lvWeatherDaily;

    ArrayList<WeatherDailyCustomDisplay> arrayList = new ArrayList<WeatherDailyCustomDisplay>();
    //Contructor luc thoi tiet thoi dia diem bat ki
    public WeatherAysnTask_daily(Activity activity, String q){
        this.activity = activity;
        this.q = q;
        typePrediction = TypePrediction.ADDRESS_NAME;
        this.dialog = new ProgressDialog(activity);

        this.dialog.setMessage("Please wait....");
        this.dialog.setCancelable(true);
        //dialog.show();
    }
    //Constructor tai toa do bat ki
    public WeatherAysnTask_daily(Activity activity, double latitude, double longitude) {
        this.activity = activity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.typePrediction = TypePrediction.LATITUDE_LONGITUDE;
        this.dialog = new ProgressDialog(activity);

        this.dialog.setMessage("Please wait . . . . .");

        this.dialog.setCancelable(true);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.dialog.show();
    }
    @Override
    protected OpenWeatherJSon_daily doInBackground(Void... params) {
        OpenWeatherJSon_daily openWeatherJSonDaily = null;
        if(typePrediction == TypePrediction.ADDRESS_NAME){
            openWeatherJSonDaily = OpenWeatherMapAPI.predictionDaily(q, CNT);
        }else {
            openWeatherJSonDaily = OpenWeatherMapAPI.predictionDaily(latitude, longitude, CNT);
        }

        try {
            for(int i = 0; i < CNT; i++) {
                idIcon[i] = openWeatherJSonDaily.getList().get(i).getWeather().get(0).getIcon().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return openWeatherJSonDaily;

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(final OpenWeatherJSon_daily openWeatherJSon_daily) {
        super.onPostExecute(openWeatherJSon_daily);

        //Anh xa
        txtCity = (TextView)activity.findViewById(R.id.txtCity);
        lvWeatherDaily = (ListView)activity.findViewById(R.id.lvsevenday);

        //Khai bao cac mang luu tru du lieu
        final double date[] = new double[CNT];
        final String descripWeather[] = new String[CNT];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd/MM/yyyy");
        //chuyen doi sang mang phuc vu display len listview

        final String dateFormat[] = new String[CNT];
        final String tempMax[] = new String[CNT];
        final String tempMin[] = new String[CNT];
        final String rain[] = new String[CNT];
        final String tempDay[] = new String[CNT];
        final String tempNight[] = new String[CNT];
        final String pressure[] = new String[CNT];
        final String huminity[] = new String[CNT];
        final String speedWind[] = new String[CNT];
        //Icon
        for(int i = 0; i  <CNT; i ++) {
            if (idIcon[i].equals("01d")) Icon[i] = R.drawable.d01;
            else if (idIcon[i].equals("01n")) Icon[i] = R.drawable.n01;
            else if (idIcon[i].equals("02d")) Icon[i] =  R.drawable.d02;
            else if (idIcon[i].equals("02n")) Icon[i] = R.drawable.n02;
            else if (idIcon[i].equals("03d") || idIcon[i].equals("03n"))
                Icon[i] = R.drawable.dn03;
            else if (idIcon[i].equals("04d") || idIcon[i].equals("04n"))
                Icon[i] = R.drawable.dn04;
            else if (idIcon[i].equals("09d") || idIcon[i].equals("09n"))
                Icon[i] = R.drawable.dn09;
            else if (idIcon[i].equals("10d")) Icon[i] = R.drawable.d10;
            else if (idIcon[i].equals("10n")) Icon[i] = R.drawable.n10;
            else if (idIcon[i].equals("11d") || idIcon[i].equals("11n"))
                Icon[i] = R.drawable.dn11;
            else if (idIcon[i].equals("13d") || idIcon[i].equals("13n"))
                Icon[i] = R.drawable.dn13;
            else if (idIcon[i].equals("50d") || idIcon[i].equals("50n"))
                Icon[i] = R.drawable.dn50;
        }
        if(typePrediction == TypePrediction.LATITUDE_LONGITUDE) {
            txtCity.setText(openWeatherJSon_daily.getCity().getName() + " - " + openWeatherJSon_daily.getCity().getCountry());
            if(openWeatherJSon_daily.getCity().getName().equals("Ha Dong")){
                txtCity.setText("Hà Nội");
            }
        }else if(typePrediction == TypePrediction.ADDRESS_NAME){
            txtCity.setText(q);
        }

        //lay du lieu tu Gson
        for(int i = 0; i < CNT; i++){
            date[i] = openWeatherJSon_daily.getList().get(i).getDt() * 1000;
            rain[i] = format.format(openWeatherJSon_daily.getList().get(i).getRain() * 10.0) + "mm";
            descripWeather[i] = translate(openWeatherJSon_daily.getList().get(i).getWeather().get(0).getDescription());

            dateFormat[i] = simpleDateFormat.format(date[i]);
            tempMax[i] = format.format(openWeatherJSon_daily.getList().get(i).getTemp().getMax() - 273.15) + "°C";
            tempMin[i] = format.format(openWeatherJSon_daily.getList().get(i).getTemp().getMin() - 273.15) + "°C";

            tempDay[i] = format.format(openWeatherJSon_daily.getList().get(i).getTemp().getDay() - 273.15) + "°C";
            tempNight[i] = format.format(openWeatherJSon_daily.getList().get(i).getTemp().getNight() - 273.15) + "°C";
            pressure[i] = format.format(openWeatherJSon_daily.getList().get(i).getPressure()) + "pHa";
            huminity[i] = format.format(openWeatherJSon_daily.getList().get(i).getHumidity()) + "%";
            speedWind[i] = openWeatherJSon_daily.getList().get(i).getSpeed() + "m/s";

            arrayList.add(new WeatherDailyCustomDisplay(dateFormat[i], descripWeather[i], rain[i], Icon[i], tempMax[i], tempMin[i]));
        }

        AdapterSevenDayWeather adapterSevenDayWeather = new AdapterSevenDayWeather(activity.getApplicationContext(), R.layout.row_weather_daily, arrayList);
        lvWeatherDaily.setAdapter(adapterSevenDayWeather);

        //txtCity.setText(q);
        //Hien thi thông tin chi tiêt
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setIcon(R.drawable.anh2);
        builder.setTitle("Thông tin chi tiết");


        lvWeatherDaily.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String message =    "Thời gian                   : " + dateFormat[position] +"\n"+
                                    "Nhiệt độ trung bình : " +(tempDay[position]) +"\n"+
                                    "Nhiệt độ ban đêm    : " + (tempNight[position]) + "\n" +
                                    "Nhiệt độ cao nhất    : " + (tempMax[position]) +"\n" +
                                    "Nhiệt độ thấp nhất   : " + (tempMin[position])+"\n"+
                                    "Thời tiết                    : " + descripWeather[position]+"\n"+
                                    "Áp suất                     : " + pressure[position]+"\n"+
                                    "Độ ẩm                       : " + huminity[position]+"\n"+
                                    "Tốc độ gió                : " + speedWind[position]+"\n";
                builder.setMessage(message);
                builder.show();
            }
        });
        this.dialog.dismiss();
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
