package com.github.johntinashe.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class TrailersResponse {

    @SerializedName("results")
    private ArrayList<Trailer> trailers;

    public TrailersResponse() {
    }

    public TrailersResponse(ArrayList<Trailer> trailers) {
        this.trailers = trailers;
    }

    public ArrayList<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(ArrayList<Trailer> trailers) {
        this.trailers = trailers;
    }
}
