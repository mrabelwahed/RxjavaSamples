package com.example.mahmoud.rxdownload;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.mahmoud.samples.R;
import com.github.lzyzsd.circleprogress.ArcProgress;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by mahmoud on 15/03/18.
 */

public class RxDownloadActivity extends AppCompatActivity {
    @BindView(R.id.arc_progress)
    ArcProgress arcProgress;
    @BindView(R.id.download)
    Button downloadBtn;

    private PublishSubject<Integer> progressValueObservabele = PublishSubject.create();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_downloader);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.download)
    public void onDownloadClicked() {
        downloadBtn.setClickable(false);
        downloadBtn.setTextColor(Color.parseColor("#f7f7f7"));

        progressValueObservabele
                .distinct()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(progressVal -> {
                            arcProgress.setProgress(progressVal);
                        },
                        throwable -> {
                            Log.d("error", "error" + throwable.getMessage());
                        });

        String destination = "/sdcard/downloadedfile.avi";
        downloadTask("http://archive.blender.org/fileadmin/movies/softboy.avi", destination)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> {
                    resetDownloadButton();
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                    File file = new File(destination);
                    intent.setDataAndType(Uri.fromFile(file), "image");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }, error -> {
                    Toast.makeText(getApplicationContext(), "Something went south", Toast.LENGTH_SHORT).show();
                    resetDownloadButton();
                });
    }


    private Observable<Boolean> downloadTask(String source, String destination) {
        return Observable.create(subscriber -> {
            try {
                boolean result = downloadFile(source, destination);
                if (result) {
                    subscriber.onNext(true);
                    subscriber.onComplete();
                } else {
                    subscriber.onError(new Throwable("Download failed."));
                }
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }

    private boolean downloadFile(String source, String destination) {
        int count;
        try {
            URL url = new URL(source);
            URLConnection conection = url.openConnection();
            conection.connect();
            // this will be useful so that you can show a tipical 0-100% progress bar
            int lenghtOfFile = conection.getContentLength();

            // download the file
            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            // Output stream
            OutputStream output = new FileOutputStream(destination);

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called
                progressValueObservabele.onNext((int)((total*100)/lenghtOfFile));

                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();
            return true;

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

        return false;
    }


    private void resetDownloadButton() {
        downloadBtn.setClickable(true);
        arcProgress.setProgress(0);
    }

}
