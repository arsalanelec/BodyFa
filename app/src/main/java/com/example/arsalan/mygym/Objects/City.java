package com.example.arsalan.mygym.Objects;


/**
 * Created by Arsalan on 03-10-2017.
 */

public class City {

    int _id;

    String name;

    String provinceId;

    public City() {
    }

    @Override
    public String toString() {
        return name;
    }

    public int get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getProvinceId() {
        return provinceId;
    }

}
