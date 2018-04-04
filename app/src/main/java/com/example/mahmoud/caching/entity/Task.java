package com.example.mahmoud.caching.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by mahmoud on 27/03/18.
 */

//delete child objects of parent is deleted
@Entity(foreignKeys  = @ForeignKey(entity = User.class,
                                   childColumns = "userId",
                                   parentColumns = "id",
                                   onDelete = ForeignKey.CASCADE))
public class Task {

    @PrimaryKey(autoGenerate =  true)
    private long id;
    private long userId;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
