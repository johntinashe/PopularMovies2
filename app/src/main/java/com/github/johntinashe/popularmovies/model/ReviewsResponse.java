package com.github.johntinashe.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class ReviewsResponse {

    @SerializedName("results")
    private ArrayList<Review> reviews;

    @SerializedName("total_results")
    private int total_results;

    public ReviewsResponse() {
    }

    public ReviewsResponse(ArrayList<Review> reviews, int total_results) {
        this.reviews = reviews;
        this.total_results = total_results;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }
}
