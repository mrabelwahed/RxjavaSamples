package com.example.mahmoud.caching.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

/**
 * Created by mahmoud on 27/03/18.
 */

@Entity(tableName = "users")
public class User {


    @NonNull
    @ColumnInfo(name = "user_id")
    @PrimaryKey
    private String id;

    @ColumnInfo(name = "user_name")
    private String username;

    @ColumnInfo(name = "bio")
    private String bio;

    @Ignore
    public User(String username) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public User(String id, String username , String bio) {
        this.id = id;
        this.username = username;

        this.bio = bio;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
