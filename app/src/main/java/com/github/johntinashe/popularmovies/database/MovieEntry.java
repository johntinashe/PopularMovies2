package com.github.johntinashe.popularmovies.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@SuppressWarnings("unused")
@Entity(tableName = "movies")
public class MovieEntry {

    @PrimaryKey()
    private int id;
    private String title;
    private float rating;
    private String date;
    private String plot;
    private String language;
    private String poster;
    private String backdrop;

    @Ignore
    public MovieEntry() {
    }

    public MovieEntry(int id, String title, float rating, String date, String plot, String language, String poster, String backdrop) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.date = date;
        this.plot = plot;
        this.language = language;
        this.poster = poster;
        this.backdrop = backdrop;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
