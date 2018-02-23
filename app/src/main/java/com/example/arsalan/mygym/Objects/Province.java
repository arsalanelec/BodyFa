package com.example.arsalan.mygym.Objects;


import java.util.List;

/**
 * Created by Arsalan on 03-10-2017.
 */

public class Province {

    int id;

    String name;

    List<City> cityList;

    public Province() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<City> getCityList() {
        return cityList;
    }

    @Override
    public String toString() {
        return name;
    }
}
