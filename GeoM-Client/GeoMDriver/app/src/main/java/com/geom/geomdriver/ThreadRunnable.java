package com.geom.geomdriver;

import android.location.Location;
import android.widget.TextView;

/**
 * Created by dario on 20/05/2016.
 */
public class ThreadRunnable implements Runnable {

    private Location l;
    private TextView t1, t2;
    private int count;

    public ThreadRunnable(Location location, TextView textView1, TextView textView2, int nRefresh) {
        l = location;
        t1 = textView1;
        t2 = textView2;
        count = nRefresh;
    }

    @Override
    public void run() {
        String temp = Integer.toString(count);
        t1.setText("numero di refresh: " + temp);
        String lat = Double.toString(l.getLatitude());
        String lng = Double.toString(l.getLongitude());
        t2.setText("location: " + lat + ";" + lng);
    }
}
