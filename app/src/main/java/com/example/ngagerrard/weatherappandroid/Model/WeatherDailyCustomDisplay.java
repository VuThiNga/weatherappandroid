package com.example.ngagerrard.weatherappandroid.Model;

/**
 * Created by Nga Gerrard on 07/04/2017.
 */
public class WeatherDailyCustomDisplay {
    String date;
    String weatherDiscription;
    String rain;
    int icon;
    String tempmax;
    String tempmin;

    public WeatherDailyCustomDisplay(String date, String weatherDiscription, String rain, int icon, String tempmax, String tempmin) {
        this.date = date;
        this.weatherDiscription = weatherDiscription;
        this.rain = rain;
        this.icon = icon;
        this.tempmax = tempmax;
        this.tempmin = tempmin;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeatherDiscription() {
        return weatherDiscription;
    }

    public void setWeatherDiscription(String weatherDiscription) {
        this.weatherDiscription = weatherDiscription;
    }

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTempmax() {
        return tempmax;
    }

    public void setTempmax(String tempmax) {
        this.tempmax = tempmax;
    }

    public String getTempmin() {
        return tempmin;
    }

    public void setTempmin(String tempmin) {
        this.tempmin = tempmin;
    }
}
