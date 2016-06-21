package com.geom.geomdriver.classes.threads;

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
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

import com.geom.geomdriver.classes.SharedData;
import com.geom.geomdriver.classes.StaticHandler;

/**
 * Created by dario on 5/22/2016.
 */
public class ThreadLocation extends Thread {

    private SharedData sd;
    private Context c;
    private LocationManager loc;
    private TextView t2;
    private String prov;
    private Activity a;

    public ThreadLocation(SharedData sd, Activity activity, LocationManager locationManager, TextView refreshText, String provider) {
        this.sd = sd;
        this.a = activity;
        this.loc = locationManager;
        this.t2 = refreshText;
        this.prov = provider;
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

    public void updateWithNewLocation(Location location) {
        sd.coordX = location.getLatitude(); // X
        sd.coordY = location.getLongitude(); // Y
        sd.refreshOnly = true; // imposto solo il refresh normale di SharedData senza notify()

        Message msg = new Message();
        msg.obj = sd;
        StaticHandler.getHandler().sendMessage(msg); // invio messaggio per aggiornare SharedData

        ThreadRunnable t = new ThreadRunnable(location, t2);
        a.runOnUiThread(t);
    }
}
