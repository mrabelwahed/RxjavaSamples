package com.example.mahmoud.networking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mahmoud.networking.adapter.RepoAdapter;
import com.example.mahmoud.networking.endpoint.GithubReposApi;
import com.example.mahmoud.networking.model.Repo;
import com.example.mahmoud.networking.state.ApiRequest;
import com.example.mahmoud.networking.state.RequestState;
import com.example.mahmoud.samples.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by mahmoud on 21/03/18.
 */

public class NetworkingRequestState extends AppCompatActivity {
    @BindView(R.id.recycler_view)
    RecyclerView rvRepos;
    @BindView(R.id.retryBtn)
    Button retryBtn;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    PublishSubject<Long> retryRequest = PublishSubject.create();
    BehaviorSubject<RequestState> state = BehaviorSubject.createDefault(RequestState.IDLE);//receive the recent emission

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_networking);
        ButterKnife.bind(this);

        GithubReposApi api = ApiClient.getInstance()
                .getGithubReposApi();

        ApiRequest apiRequest = new ApiRequest(api.getUserRepos("mrabelwahed"));
        apiRequest.state.subscribe(requestState->updateLoadingView(requestState));
        apiRequest.errors.subscribe(throwable ->showError(throwable.getMessage()));
        apiRequest.repos.subscribe(repos -> setAdapterData(repos));
        apiRequest.execute();

    }

    private void showError(String msg){
        Toast.makeText(this, "eror"+msg , Toast.LENGTH_SHORT).show();
    }

    private void updateLoadingView(RequestState requestState){
        switch (requestState){
           case  IDLE:
            break;
           case  LOADING:
            progressBar.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.GONE);
            break;
            case  COMPLETED:
            progressBar.setVisibility(View.GONE);
            break;
           case  ERROR:
            progressBar.setVisibility(View.GONE);
            retryBtn.setVisibility(View.VISIBLE);
            break;
        }
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
