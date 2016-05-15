package com.example.trasmettitore_gps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

/**
 * Created by dario on 15/05/2016.
 */
public class ThreadLocation extends java.lang.Thread {

    private LocationManager l;
    private String provider;
    private Context c;
    private TextView text;

    public ThreadLocation(LocationManager l, String provider, Context c, TextView text) {
        this.l = l;
        this.provider = provider;
        this.c = c;
        this.text = text;
    }

    public void run() {
        if (ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location loc = l.getLastKnownLocation(provider);
        text.setText("your location is: "+loc.getLatitude()+";"+loc.getLongitude());
    }
}
