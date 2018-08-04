package com.github.johntinashe.popularmovies.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Cast {

    @SerializedName("cast_id")
    private int cast_id;
    @SerializedName("character")
    private String character;
    @SerializedName("credit_id")
    private String credit_id;
    @SerializedName("gender")
    private int gender;
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("order")
    private int order;
    @SerializedName("profile_path")
    @Nullable private String profile_path;

    @SuppressWarnings("unused")
    public Cast() {
    }

    @SuppressWarnings("unused")
    public Cast(int cast_id, String character, String credit_id, int gender, int id, String name, int order, @Nullable String profile_path) {
        this.cast_id = cast_id;
        this.character = character;
        this.credit_id = credit_id;
        this.gender = gender;
        this.id = id;
        this.name = name;
        this.order = order;
        this.profile_path = profile_path;
    }

    @Nullable
    public String getProfile_path() {
        return profile_path;
    }

    @SuppressWarnings("unused")
    public void setProfile_path(@Nullable String profile_path) {
        this.profile_path = profile_path;
    }

    @SuppressWarnings("unused")
    public int getCast_id() {
        return cast_id;
    }

    @SuppressWarnings("unused")
    public void setCast_id(int cast_id) {
        this.cast_id = cast_id;
    }

    @SuppressWarnings("unused")
    public String getCharacter() {
        return character;
    }

    @SuppressWarnings("unused")
    public void setCharacter(String character) {
        this.character = character;
    }

    @SuppressWarnings("unused")
    public String getCredit_id() {
        return credit_id;
    }

    @SuppressWarnings("unused")
    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }

    @SuppressWarnings("unused")
    public int getGender() {
        return gender;
    }

    @SuppressWarnings("unused")
    public void setGender(int gender) {
        this.gender = gender;
    }

    @SuppressWarnings("unused")
    public int getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(int id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public void setName(String name) {
        this.name = name;
    }

    @SuppressWarnings("unused")
    public int getOrder() {
        return order;
    }

    @SuppressWarnings("unused")
    public void setOrder(int order) {
        this.order = order;
    }

}
