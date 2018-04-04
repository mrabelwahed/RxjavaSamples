package com.example.mahmoud.caching;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.mahmoud.caching.datasource.UserDataSource;

/**
 * Created by mahmoud on 04/04/18.
 */

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final UserDataSource mDataSource;

    public ViewModelFactory(UserDataSource dataSource){
        mDataSource = dataSource;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class)){
            return (T) new UserViewModel(mDataSource);
        }
        throw new IllegalArgumentException("unknown view model");
    }
}
