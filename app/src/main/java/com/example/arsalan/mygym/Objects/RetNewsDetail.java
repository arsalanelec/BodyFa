package com.example.arsalan.mygym.Objects;

import com.google.gson.annotations.SerializedName;

public class RetNewsDetail {

    @SerializedName("Record")
    News record;
    public RetNewsDetail() {
    }

    public News getRecord() {
        return record;
    }

}
