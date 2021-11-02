package com.example.samplelocation;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        textView = findViewById(R.id.textView);
        findViewById(R.id.button).setOnClickListener(v -> {
            startLocationService();
        });
        AutoPermissions.Companion.loadAllPermissions(this, 101);
    }

    private void startLocationService() {
        LocationManager manager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        try {
            Location loc = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc != null) {
                double latitude = loc.getLatitude();
                double longitude = loc.getLongitude();
                String msg = "최근 위치\nLatitude: " + latitude + "\n"
                        + "Longitude: " + longitude;
                textView.setText(msg);
            }
            GPSListener gps = new GPSListener();
            long minTime = 10000;
            float minDistance = 0;
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gps);
            Toast.makeText(this, "내 위치 요청함", Toast.LENGTH_SHORT).show();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }

    @Override
    public void onDenied(int i, String[] strings) {

    }

    @Override
    public void onGranted(int i, String[] strings) {

    }

    class GPSListener implements LocationListener {
        @Override
        public void onLocationChanged(@NonNull Location loc) {
            Double latitude = loc.getLatitude();
            Double longitude = loc.getLongitude();
            String msg = "최근 위치\nLatitude: " + latitude + "\n"
                    + "Longitude: " + longitude;
            textView.setText(msg);
        }
    }
}