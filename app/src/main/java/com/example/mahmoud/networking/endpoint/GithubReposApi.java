package com.example.mahmoud.networking.endpoint;

import com.example.mahmoud.networking.model.Repo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Mahmoud Ramadan on 12/23/17.
 */

public interface GithubReposApi {
    @GET("users/{user}/repos")
    Observable<List<Repo>> getUserRepos(@Path("user") String username);
}
