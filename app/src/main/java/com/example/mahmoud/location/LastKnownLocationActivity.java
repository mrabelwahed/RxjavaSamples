package com.example.mahmoud.location;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mahmoud.samples.BuildConfig;
import com.example.mahmoud.samples.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by mahmoud on 28/03/18.
 */

public class LastKnownLocationActivity extends AppCompatActivity {
    @BindView(R.id.lat)
    TextView latText;
    @BindView(R.id.lng)
    TextView lngText;
    @BindView(R.id.main_activity_container)
    LinearLayout parent;

    private FusedLocationProviderClient fusedLocClien;
    private static final int LOCATION_PERMISSION_REQUEST = 0x01;
    private Location mLastLocation;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lastknown_location);
        ButterKnife.bind(this);
        fusedLocClien = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!checkPermission()) {
            requestPermission();
        } else {
            getLastLocation();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void requestPermission() {
        boolean shouldProvideRational = ActivityCompat.
                shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRational) {
            showSnackBar(R.string.permission_request,R.string.ok,v->{
                startLocationPermissionRequest();
            });
        } else {
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
          startLocationPermissionRequest();
        }

    }

    private void startLocationPermissionRequest(){
        ActivityCompat.requestPermissions(this,
                                         new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                         LOCATION_PERMISSION_REQUEST);
    }

    private boolean checkPermission() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        fusedLocClien.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location!= null) {
                        mLastLocation = location;

                        latText.setText(String.valueOf(mLastLocation.getLatitude()));
                        lngText.setText(String.valueOf(mLastLocation.getLongitude()));
                    } else {
                        showSnackbar
                        (getString(R.string.no_location_detected));
                    }
                });
    }
    private void showSnackbar(final String text) {
        if (parent != null) {
            Snackbar.make(parent, text, Snackbar.LENGTH_LONG).show();
        }
    }

    private void showSnackBar(int mainString, int actionStringId, View.OnClickListener listener){
        Snackbar.make(findViewById(android.R.id.content),
                                   getString(mainString),
                                   Snackbar.LENGTH_INDEFINITE).setAction(getString(actionStringId),listener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST && grantResults.length>0){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startLocationPermissionRequest();
            }else{
                showSnackBar(R.string.permission_denied_explanation, R.string.settings,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }
}

