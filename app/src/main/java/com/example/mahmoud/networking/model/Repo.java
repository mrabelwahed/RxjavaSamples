package com.example.mahmoud.networking.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mahmoud on 20/03/18.
 */


public class Repo{
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

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
}
