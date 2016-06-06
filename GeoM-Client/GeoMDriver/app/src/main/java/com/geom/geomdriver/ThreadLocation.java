package com.geom.geomdriver;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

/**
 * Created by dario on 5/22/2016.
 */
public class ThreadLocation extends Thread {

    private Context c;
    private LocationManager loc;
    private TextView t1, t2;
    private String prov;
    private int count;
    private Activity a;

    public ThreadLocation(Activity activity, LocationManager locationManager, TextView locationText, TextView refreshText, String provider) {
        a=activity;
        loc = locationManager;
        t1 = locationText;
        t2 = refreshText;
        prov = provider;
        count = 0;
    }

    public void run() {
        if (ActivityCompat.checkSelfPermission(a.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(a.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        HandlerThread handlerThread = new HandlerThread("MyHandlerThread");
        handlerThread.start();
        // Now get the Looper from the HandlerThread
        // NOTE: This call will block until the HandlerThread gets control and initializes its Looper
        Looper looper = handlerThread.getLooper();
        loc.requestLocationUpdates(prov, 2000, 10, locationListener,looper);
    }

    private LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            updateWithNewLocation(location);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status,
                                    Bundle extras) {
        }
    };

    public void updateWithNewLocation(Location location){
        ThreadRunnable t = new ThreadRunnable(location,t1,t2,count);
        a.runOnUiThread(t);
        count++;
    }
}
