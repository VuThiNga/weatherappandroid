package com.example.ngagerrard.weatherappandroid.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ngagerrard.weatherappandroid.Model.WeatherDailyCustomDisplay;
import com.example.ngagerrard.weatherappandroid.R;

import java.util.List;

/**
 * Created by Nga Gerrard on 07/04/2017.
 */
public class AdapterSevenDayWeather extends ArrayAdapter<WeatherDailyCustomDisplay> {
    public AdapterSevenDayWeather(Context context, int resource, List<WeatherDailyCustomDisplay> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view =  inflater.inflate(R.layout.row_weather_daily, null);
        }
        WeatherDailyCustomDisplay p = getItem(position);
        if (p != null) {
            // Anh xa + Gan gia tri
            TextView txtDate = (TextView) view.findViewById(R.id.txtDate);
            txtDate.setText(p.getDate());
            TextView txtDes= (TextView)view.findViewById(R.id.txtDescrip);
            txtDes.setText(p.getWeatherDiscription());
            TextView txtRain = (TextView)view.findViewById(R.id.txtRain);
            txtRain.setText(p.getRain());
            TextView txtMaxTemp = (TextView)view.findViewById(R.id.txtTempMax);
            txtMaxTemp.setText(p.getTempmax());
            TextView txtMinTemp = (TextView)view.findViewById(R.id.txtTempMin);
            txtMinTemp.setText(p.getTempmin());
            ImageView imgWeatherDaily = (ImageView)view.findViewById(R.id.imgIconDaily);
            imgWeatherDaily.setImageResource(p.getIcon());

//            String urlIcon = "http://openweathermap.org/img/w/" + p.getIcon() + ".png";
//            //Tao doi tuong URL
//            Bitmap myBitmap = null;
//            try {
//                URL urlConnection = null;
//
//                urlConnection = new URL(urlIcon);
//                //Mo ket noi
//                HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
//                connection.setDoInput(true);
//                connection.connect();
//                //Doc du lieu
//                InputStream input = connection.getInputStream();
//                //Tien hanh convert ra hinh anh
//                myBitmap = BitmapFactory.decodeStream(input);
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            imgWeatherDaily.setImageBitmap(myBitmap);
        }
        return view;
    }

}
