package com.geom.geomdriver.classes.threads;

import android.location.Location;
import android.widget.TextView;

/**
 * Created by dario on 20/05/2016.
 */
public class ThreadRunnable implements Runnable {

    private Location l;
    private TextView textLoaction;

    public ThreadRunnable(Location location, TextView textView) {
        this.l = location;
        this.textLoaction = textView;
    }

    @Override
    public void run() {
        String lat = Double.toString(l.getLatitude());
        String lng = Double.toString(l.getLongitude());
        textLoaction.setText("Posizione\nLatitudine (X): " + lat + "\nLongitudine (Y): " + lng);
    }
}
