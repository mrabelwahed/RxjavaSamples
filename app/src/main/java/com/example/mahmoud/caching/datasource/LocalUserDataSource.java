package com.example.mahmoud.caching.datasource;

import com.example.mahmoud.caching.dao.UserDao;
import com.example.mahmoud.caching.entity.User;

import io.reactivex.Flowable;

/**
 * Created by mahmoud on 04/04/18.
 */

public class LocalUserDataSource implements UserDataSource {
    private final UserDao userDao;

    public LocalUserDataSource(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Flowable<User> getUser() {
        return userDao.getUser();
    }

    @Override
    public void insertOrUpdate(User user) {
        userDao.insert(user);
    }

    @Override
    public void deleteAllUsers() {
        userDao.deleteAllUsers();
    }
}
