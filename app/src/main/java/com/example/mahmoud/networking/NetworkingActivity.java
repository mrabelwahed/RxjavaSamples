package com.example.mahmoud.networking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.mahmoud.networking.adapter.RepoAdapter;
import com.example.mahmoud.networking.endpoint.GithubReposApi;
import com.example.mahmoud.networking.model.Repo;
import com.example.mahmoud.samples.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by mahmoud on 20/03/18.
 */

public class NetworkingActivity extends AppCompatActivity {
    @BindView(R.id.recycler_view)
    RecyclerView rvRepos;
    @BindView(R.id.retryBtn)
    Button retryBtn;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    PublishSubject<Long> retryRequest = PublishSubject.create();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_networking);
        ButterKnife.bind(this);

        GithubReposApi api = ApiClient.getInstance()
                .getGithubReposApi();

        api.getUserRepos("mrabelwahed")
                .doOnError(throwable -> enableRetryBtn())
                .retryWhen(attempt -> attempt.zipWith(retryRequest,(o,o2)->o))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscriber->progressBar.setVisibility(View.VISIBLE))
                .doOnDispose(()->progressBar.setVisibility(View.GONE))
                .subscribe(repos -> {
                            setAdapterData(repos);
                        },
                        throwable -> {
                            Log.d("error", "error" + throwable.getMessage());
                        });

    }

    private void enableRetryBtn(){
        retryBtn.setVisibility(View.VISIBLE);
    }

    void setAdapterData(List<Repo> repos) {
        RepoAdapter adapter = new RepoAdapter(getApplicationContext(), repos);
        rvRepos.setLayoutManager(new LinearLayoutManager(this));
        rvRepos.setItemAnimator(new DefaultItemAnimator());
        rvRepos.setAdapter(adapter);
    }


    @OnClick(R.id.retryBtn)
    public void retryRequest() {
        retryRequest.onNext(System.currentTimeMillis());
    }

}
