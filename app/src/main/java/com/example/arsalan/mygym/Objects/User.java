package com.example.arsalan.mygym.Objects;

import com.google.gson.annotations.SerializedName;

public class User {
    /*"UserId": 1,
        "Name": "Name",
        "Family": "Family",
        "Phone": "Phone",
        "Email": null,
        "Mobile": null,
        "Age": 34,
        "Address": "Address",
        "PictureUrl": "/Content/images/1.jpg",
        "RegisterDate": "2018-04-04T18:58:13.78",
        "BirthDate": "1984-09-21T00:00:00",
        "Gender": true,
        "Weight": 68,
        "CityId": 1,
        "Intro": "Intro",
        "RegisterDateFa": "1397/01/15",
        "RegisterTime": "18:58:13",
        "BirthDateFa": "1363/06/30",
        "BirthTime": "BirthTime"*/

    @SerializedName("UserId")
    long id;
    String password;
    String username;
    String name;
    String phone;
    String age;
    String address;
    String weight;
    int cityId;
    public User() {
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getWeight() {
        return weight;
    }

    public int getCityId() {
        return cityId;
    }
}
