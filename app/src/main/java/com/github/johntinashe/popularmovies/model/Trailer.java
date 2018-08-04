package com.github.johntinashe.popularmovies.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Trailer {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("key")
    private String key;
    @SerializedName("site")
    private String site;
    @SerializedName("type")
    private String type;

    public Trailer() {
    }

    public Trailer(String id, String name, String key, String site, String type) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.site = site;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
