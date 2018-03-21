package com.example.mahmoud.networking;

import com.example.mahmoud.networking.endpoint.GithubReposApi;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mahmoud on 20/03/18.
 */

public class ApiClient {
    private  static ApiClient instance;
    private static final String BASE_URL="https://api.github.com/";
    private  Retrofit retrofit;

    private ApiClient (){
        buildRetrofit();
    }

    public synchronized static ApiClient getInstance(){
        if (instance == null)
            instance = new ApiClient();
        return instance;
    }

    private Retrofit buildRetrofit(){
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        //log url body
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpBuilder.addInterceptor(loggingInterceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpBuilder.build())
                .build();

        return  retrofit;
    }

    public GithubReposApi getGithubReposApi(){
        return  retrofit.create(GithubReposApi.class);
    }
}
