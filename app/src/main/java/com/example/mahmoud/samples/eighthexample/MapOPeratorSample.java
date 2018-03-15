package com.example.mahmoud.samples.eighthexample;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.example.mahmoud.samples.AppInfo;
import com.example.mahmoud.samples.AppRichInfo;
import com.example.mahmoud.samples.AppsAdapter;
import com.example.mahmoud.samples.R;
import com.example.mahmoud.samples.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mahmoud on 15/03/18.
 */

public class MapOPeratorSample extends AppCompatActivity {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private File mFilesDir;
    private AppsAdapter adapter;
    private List<AppInfo> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        getMyApps();

        swipeRefreshLayout.setOnRefreshListener(() -> getMyApps());

    }

    private void getMyApps() {
        getFileDir().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(file -> {
                    mFilesDir = file;
                    refreshList();
                });
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AppsAdapter(data, R.layout.item);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        // Progress
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(true);
        recyclerView.setVisibility(View.GONE);
    }

    private void refreshList() {
        Observable.fromIterable(getInstalledApps())
                .map(item ->{
                    String lowercase = item.getName().toLowerCase();
                    item.setName(lowercase);
                    return item;
                })
                .subscribe(installedApp -> {
                            Log.d("xx", "xxxxx" + installedApp.getName());
                            //set adapter data
                            recyclerView.setVisibility(View.VISIBLE);
                            //save data
                            adapter.addApplication(data.size() - 1, installedApp);
                            swipeRefreshLayout.setRefreshing(false);
                        },
                        throwable -> {
                            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);
                        });
    }


    private Observable<File> getFileDir() {
        return Observable.create(subscriber -> {
            subscriber.onNext(getApplication().getFilesDir());
            subscriber.onComplete();
        });
    }

    private List<AppInfo> getInstalledApps() {

        List<AppRichInfo> apps = new ArrayList<>();
        List<AppInfo> installedApps = new ArrayList<>();


        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> infos = getApplication().getPackageManager().queryIntentActivities(mainIntent, 0);
        for (ResolveInfo info : infos) {
            apps.add(new AppRichInfo(getApplication(), info));
        }

        for (AppRichInfo appInfo : apps) {
            Bitmap icon = Utils.drawableToBitmap(appInfo.getIcon());
            String name = appInfo.getName();
            String iconPath = mFilesDir + "/" + name;
            Utils.storeBitmap(getApplication(), icon, name);

            installedApps.add(new AppInfo(name, iconPath, appInfo.getLastUpdateTime()));
        }
        return installedApps;
    }

}
