package com.example.mahmoud.caching.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.mahmoud.caching.entity.User;

import io.reactivex.Flowable;

/**
 * Created by mahmoud on 27/03/18.
 */

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Query("DELETE FROM Users")
    void deleteAllUsers();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(User user);

    @Query("SELECT * FROM Users LIMIT 1")
    Flowable<User> getUser();



}
