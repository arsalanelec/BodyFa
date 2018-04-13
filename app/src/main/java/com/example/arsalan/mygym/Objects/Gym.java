package com.example.arsalan.mygym.Objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Arsalan on 22-02-2018.
 */

public class Gym {
    /*"GymFeatures": null,
            "GymId": 1,
            "Title": "1",
            "CityId": 1,
            "CityName": null,
            "Address": "1",
            "Phone1": "1",
            "Phone2": "1",
            "ThumbUrl": "1",
            "PictureUrl": "/Content/images/1.jpg",
            "Long": "1",
            "Lat": "1",
            "Intro": "1",
            "UserId": 1,
            "Name": null,
            "Family": null,
            "rate": 1,
            "point": 1,
            "Status": "active"*/

    @SerializedName("GymId")
    int id;
    @SerializedName("Title")
    String name;

    String address;

    @SerializedName("rate")
    double rate;
    int point;
    String status;
    int cityId;
    double lat;

    @SerializedName("ThumbUrl")
    String thumbUrl;
    @SerializedName("PictureUrl")
    String pictureUrl;
    @SerializedName("Long")
    double lon;
    @SerializedName("Phone1")
    String phone;


    public Gym() {

    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getRate() {
        return rate;
    }

    public int getPoint() {
        return point;
    }

    public String getStatus() {
        return status;
    }

    public int getCityId() {
        return cityId;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getPhone() {
        return phone;
    }
}
