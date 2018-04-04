package com.example.mahmoud.caching;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.mahmoud.caching.dao.UserDao;
import com.example.mahmoud.caching.entity.User;

/**
 * Created by mahmoud on 27/03/18.
 */

@Database(entities = {User.class}, version = 1)
public abstract class AppDB extends RoomDatabase {

    private static final String DATABASE_NAME = "sample.db";
    private static volatile AppDB INSTANCE;

    public static AppDB getInstance(Context context){
        if (INSTANCE == null){
            synchronized (AppDB.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDB.class, DATABASE_NAME)
                            .build();
                }
            }

        }
        return INSTANCE;
    }

    public abstract UserDao userDao();

}
