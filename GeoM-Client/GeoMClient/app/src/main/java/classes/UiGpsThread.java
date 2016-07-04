package classes;

import android.content.Context;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UiGpsThread implements Runnable {
    private GoogleMap gMap;
    private double lat, lng;
    private String checkMyPT;
    private String ptName;

    public UiGpsThread(GoogleMap googleMap, double latitudine, double longitudine, String checkMyPT, String ptName) {
        this.gMap = googleMap;
        this.lat = latitudine;
        this.lng = longitudine;
        this.checkMyPT = checkMyPT;
        this.ptName = ptName;
    }

    @Override
    public void run() {
        LatLng pos = new LatLng(lat, lng);
        //gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 16.0f));
        gMap.clear();
        gMap.addMarker(new MarkerOptions().position(pos).title(ptName));

        if ("true".equals(checkMyPT)) {
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 16.0f));
        }
    }
}
