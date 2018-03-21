package com.example.mahmoud.timing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.mahmoud.samples.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by mahmoud on 21/03/18.
 */

public class SplashScreen extends AppCompatActivity {
    CompositeDisposable compositeDisposable;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        compositeDisposable = new CompositeDisposable();
        Disposable disposable =
                Flowable.timer(2, TimeUnit.SECONDS)
                .subscribe(val -> {
                            startActivity(new Intent(this,TimingActivity.class));
                            finish();
                        },
                        throwable -> {
                            Log.d("error", "error" + throwable.getMessage());
                        });

        compositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
