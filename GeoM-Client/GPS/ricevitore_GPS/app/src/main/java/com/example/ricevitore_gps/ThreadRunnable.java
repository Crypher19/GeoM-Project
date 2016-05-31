package com.example.ricevitore_gps;

import android.content.Context;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by dario on 20/05/2016.
 */
public class ThreadRunnable implements Runnable {

    private Context c;
    private GoogleMap gMap;
    private double lat, lng;
    private int count;

    public ThreadRunnable(Context context, GoogleMap googleMap, double latitudine, double longitudine, int count) {
        c = context;
        gMap = googleMap;
        lat = latitudine;
        lng = longitudine;
        this.count = count;
    }

    @Override
    public void run() {
        String temp = Integer.toString(count);
        LatLng pos = new LatLng(lat, lng);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 16.0f));
        gMap.clear();
        gMap.addMarker(new MarkerOptions().position(pos).title("Marker"+temp));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
    }
}
