package com.example.mahmoud.caching;

import android.content.Context;

import com.example.mahmoud.caching.datasource.LocalUserDataSource;
import com.example.mahmoud.caching.datasource.UserDataSource;

/**
 * Created by mahmoud on 04/04/18.
 */

public class Injection {
    public static UserDataSource provideUserDataSource(Context context){
        AppDB appDB = AppDB.getInstance(context);
        return new LocalUserDataSource(appDB.userDao());
    }

    public static ViewModelFactory provideViewModelFactory(Context context){
      UserDataSource userDataSource = provideUserDataSource(context);
      return new ViewModelFactory(userDataSource);
    }
}
