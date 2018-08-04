package com.github.johntinashe.popularmovies.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Genre {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;

    public Genre() {
    }

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
