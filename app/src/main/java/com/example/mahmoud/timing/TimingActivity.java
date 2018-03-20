package com.example.mahmoud.timing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.mahmoud.samples.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;

/**
 * Created by mahmoud on 20/03/18.
 */

public class TimingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timing);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.timing1)
    public void runSingleTaskAfter2Sec() {
        Log.d("btn_clicked","clicked:"+getCurrentTimestamp());
        Flowable.timer(2, TimeUnit.SECONDS)
                .subscribe(val -> {
                            Log.d("next", "next:" + getCurrentTimestamp());
                        },
                        throwable -> {
                            Log.d("error", "error" + throwable.getMessage());
                        },
                        ()->{
                        Log.d("complete",getCurrentTimestamp());
                        });
    }

    @OnClick(R.id.timing2)
    public void doRepeatedTaskEvery1sec() {
        Log.d("btn_clicked","clicked:"+getCurrentTimestamp());
        Flowable.interval(1, TimeUnit.SECONDS)
                .subscribe(val -> {
                            Log.d("next", "next:" + getCurrentTimestamp());
                        },
                        throwable -> {
                            Log.d("error", "error" + throwable.getMessage());
                        },
                        ()->{
                            Log.d("complete",getCurrentTimestamp());
                        });

    }

    @OnClick(R.id.timing3)
    public void doTiming3() {

    }

    @OnClick(R.id.timing4)
    public void doTiming4() {

    }

    private String getCurrentTimestamp() {
        return new SimpleDateFormat("k:m:s:S a", Locale.getDefault()).format(new Date());
    }
}
