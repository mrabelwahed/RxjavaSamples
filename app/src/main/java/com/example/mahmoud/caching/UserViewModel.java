package com.example.mahmoud.caching;

import android.arch.lifecycle.ViewModel;

import com.example.mahmoud.caching.datasource.UserDataSource;
import com.example.mahmoud.caching.entity.User;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by mahmoud on 04/04/18.
 */

public class UserViewModel extends ViewModel {

    private UserDataSource mUserDataSource;
    private User mUser;

    public  UserViewModel(UserDataSource mUserDataSource ){
        this.mUserDataSource = mUserDataSource;
    }

    public Flowable<User> getUser(){
       return mUserDataSource.getUser()
                             .map(user ->{
                                 mUser=user;
                                 return mUser;
                             });
    }

    public Completable updateUserName(String username,String bio){
        return Completable.fromAction(()->{
            mUser = (mUser==null)? new User(username):new User(mUser.getId(),username,bio);
            mUserDataSource.insertOrUpdate(mUser);
        });
    }



}
