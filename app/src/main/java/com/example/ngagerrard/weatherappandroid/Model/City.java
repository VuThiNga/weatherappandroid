package com.example.ngagerrard.weatherappandroid.Model;

/**
 * Created by Nga Gerrard on 07/04/2017.
 */
public class City {
    private long id;
    private String name;
    private Coord coord;
    private String country;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
