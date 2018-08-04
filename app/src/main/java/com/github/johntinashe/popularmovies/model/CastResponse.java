package com.github.johntinashe.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class CastResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("cast")
    private ArrayList<Cast> casts;

    @SuppressWarnings("unused")
    public CastResponse() {
    }

    @SuppressWarnings("unused")
    public CastResponse(String id, ArrayList<Cast> casts) {
        this.id = id;
        this.casts = casts;
    }

    @SuppressWarnings("unused")
    public String getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Cast> getCasts() {
        return casts;
    }

    @SuppressWarnings("unused")
    public void setCasts(ArrayList<Cast> casts) {
        this.casts = casts;
    }
}
