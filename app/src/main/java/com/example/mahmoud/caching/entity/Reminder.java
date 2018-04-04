package com.example.mahmoud.caching.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.mahmoud.caching.Converters;

import java.util.Date;

/**
 * Created by mahmoud on 27/03/18.
 */

@Entity(foreignKeys = @ForeignKey(
                            entity = Task.class,
                            childColumns ="taskId",
                            parentColumns = "id",
                            onDelete = ForeignKey.CASCADE))
@TypeConverters(Converters.class)
public class Reminder {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "reminderId")
    private long id;
    private long taskId;
    private Date date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
