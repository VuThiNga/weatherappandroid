package com.example.ngagerrard.weatherappandroid.Model;

import java.util.List;

/**
 * Created by Nga Gerrard on 07/04/2017.
 */
public class OpenWeatherJSon_daily {
    private City city;
    private List<ListItem> list;

    public List<ListItem> getList() {
        return list;
    }

    public void setList(List<ListItem> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
