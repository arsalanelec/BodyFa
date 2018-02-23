package com.example.arsalan.mygym.Objects;

/**
 * Created by Arsalan on 23-02-2018.
 */

public class TutorialGroup {
    int id;
    String name;
    String thumb;
    int thumbRes;

    public TutorialGroup(String name, int thumbRes) {
        this.name = name;
        this.thumbRes = thumbRes;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getThumb() {
        return thumb;
    }

    public int getThumbRes() {
        return thumbRes;
    }
}
