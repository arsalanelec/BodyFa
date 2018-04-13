package com.example.arsalan.mygym.Objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Arsalan on 10-02-2018.
 */

public class Trainer {

    @SerializedName("UserId")
    long id;

    /*            "UserId": 2,
            "CityId": 1,
            "GymId": 0,
            "Name": "Name",
            "Family": "Family",
            "Rate": 1,
            "Point": 1,
            "ChampionshipCount": 1,
            "PictureUrl": "/Content/images/1.jpg",
            "Honors": null
        },*/
    String name;
    @SerializedName("Honors")
    String title;

    long rate;
    int point;

    int cityId;
    int gymId;
    @SerializedName("PictureUrl")
    String thumbUrl;

    public Trainer() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public long getRate() {
        return rate;
    }

    public int getPoint() {
        return point;
    }

    public int getCityId() {
        return cityId;
    }

    public int getGymId() {
        return gymId;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }
}
