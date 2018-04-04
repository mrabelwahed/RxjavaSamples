package com.example.mahmoud.caching.datasource;

import com.example.mahmoud.caching.entity.User;

import io.reactivex.Flowable;

/**
 * Created by mahmoud on 04/04/18.
 */

public interface UserDataSource {
    Flowable<User> getUser();

    void insertOrUpdate(User user);

    void deleteAllUsers();
}
