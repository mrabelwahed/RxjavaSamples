package com.example.mahmoud.rxdownload;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mahmoud.samples.R;
import com.github.lzyzsd.circleprogress.ArcProgress;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;

/**
 * Created by mahmoud on 15/03/18.
 */

public class RxDownloadActivity extends AppCompatActivity {
    @BindView(R.id.arc_progress)
    ArcProgress arcProgress;
    @BindView(R.id.download)
    Button downloadBtn;
    @BindView(R.id.img)
    ImageView imageView;

    File folder, file;

    private static final String IMAGE_URL = "http://i.dailymail.co.uk/i/pix/2015/11/16/20/2E82087500000578-3321104-image-a-29_1447706769339.jpg";
    // private PublishSubject<Integer> progressValueObservabele = PublishSubject.create();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_downloader);
        ButterKnife.bind(this);
        folder = new File(Environment.getExternalStorageDirectory(), "/Downloads");
    }


    @OnClick(R.id.download)
    public void onDownloadClicked() {
        downloadBtn.setClickable(false);
        downloadBtn.setTextColor(Color.parseColor("#f7f7f7"));

        Request request = new Request.Builder().url(IMAGE_URL).build();
        Observable.defer(new Callable<ObservableSource<?>>() {
            @Override
            public ObservableSource<?> call() throws Exception {
                try {
                    Response response = new OkHttpClient().newCall(request).execute();
                    if (response.isSuccessful())
                        saveFile(response);
                    return Observable.just(file);
                } catch (Exception e) {
                    return Observable.error(e);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(file -> {
                            Log.d("success", file.toString());
                            imageView.setImageURI(Uri.parse(file.getPath()));
                        },
                        throwable -> {
                            Log.d("error", "error" + throwable.getMessage());
                        });
    }

    private void saveFile(Response response) {
        file = new File(folder.getPath(), "/downloadedImage.png");
        try {
            BufferedSink sink = Okio.buffer(Okio.sink(file));
            sink.writeAll(response.body().source());
            sink.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void resetDownloadButton() {
        downloadBtn.setClickable(true);
        arcProgress.setProgress(0);
    }

}
