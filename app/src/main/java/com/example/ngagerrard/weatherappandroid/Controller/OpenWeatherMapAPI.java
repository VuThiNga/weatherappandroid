package com.example.ngagerrard.weatherappandroid.Controller;

import com.example.ngagerrard.weatherappandroid.Model.OpenWeatherJSon;
import com.example.ngagerrard.weatherappandroid.Model.OpenWeatherJSon_daily;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Nga Gerrard on 28/03/2017.
 */
public class OpenWeatherMapAPI {

    //Truong hop xem thoi tiet bang cach nhap dia diem cu the
    public static OpenWeatherJSon prediction(String q){
        try {
            String location = URLEncoder.encode(q, "UTF-8");
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=88bb5023443497a99fbd58c3299600c6");
            InputStreamReader reader = new InputStreamReader(url.openStream(), "UTF-8");
            OpenWeatherJSon results = new Gson().fromJson(reader, OpenWeatherJSon.class);

            String idIcon = results.getWeather().get(0).getIcon().toString();
            String urlIcon = "http://openweathermap.org/img/w/"+idIcon+".png";
            URL urlImage = new URL(urlIcon);
            return results;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Truong hop xem thoi tiet bang cach nhap kinh do, vi do
    public static OpenWeatherJSon prediction(double lat, double lon){
        try {
//            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&amp;lon=" +lon + "&appid=88bb5023443497a99fbd58c3299600c6");
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" +lon + "&appid=88bb5023443497a99fbd58c3299600c6");
            InputStreamReader reader = new InputStreamReader(url.openStream(), "UTF-8");
            OpenWeatherJSon results = new Gson().fromJson(reader, OpenWeatherJSon.class);

            String idIcon = results.getWeather().get(0).getIcon().toString();
            String urlIcon = "http://openweathermap.org/img/w/"+idIcon+".png";
            URL urlImage = new URL(urlIcon);

            return results;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    //Truong hop xem thoi tiet trong nhieu ngay lien tiep voi dau vao la kinh do vi do (daily)
    public static OpenWeatherJSon_daily predictionDaily(double lat, double lon, int cnt){
        URL url = null;
        try {
            url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?lat="+lat+"&lon="+lon+"&amp;cnt="+cnt + "&appid=88bb5023443497a99fbd58c3299600c6");
            InputStreamReader reader = new InputStreamReader(url.openStream(), "UTF-8");
            OpenWeatherJSon_daily results = new Gson().fromJson(reader, OpenWeatherJSon_daily.class);

            return results;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Truong hop xem thoi tiet trong nhieu ngay lien tiep voi dau vao la dia diem cu the (daily)
    public static OpenWeatherJSon_daily predictionDaily(String q, int cnt){
        try {
            String location= URLEncoder.encode(q, "UTF-8");
            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q="+location+"&cnt="+cnt+"&appid=88bb5023443497a99fbd58c3299600c6");
            InputStreamReader reader = new InputStreamReader(url.openStream(),"UTF-8");
            OpenWeatherJSon_daily results = new Gson().fromJson(reader, OpenWeatherJSon_daily.class);

            return results;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
