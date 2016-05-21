package com.example.ricevitore_gps;

import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Geocoder gc;
    private TextView myLocationText;
    private double lat, lon;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        gc = new Geocoder(this, Locale.getDefault());

        myLocationText = (TextView) findViewById(R.id.textView);

        new Thread() {
            public void run() {
                int i = 0;
                while (i++ < 100) {
                    double latitudine = (i/10)+44;
                    double longitudine = (i/10)+8;
                    ThreadRunnable t = new ThreadRunnable(getApplicationContext(),mMap,latitudine,longitudine,i);
                    try {
                        runOnUiThread(t);
                        Thread.sleep(1000);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Maps Page",
                Uri.parse("http://host/path"),
                Uri.parse("android-app://com.example.ricevitore_gps/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Maps Page",
                Uri.parse("http://host/path"),
                Uri.parse("android-app://com.example.ricevitore_gps/http/host/path")
        );

        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public void updateMap(double latitudine,double longitudine) {
        lat = latitudine;
        lon = longitudine;
        LatLng pos = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(pos).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));

        updateAddress(); //aggiorno la textView che contiene l'indirizzo
    }

    private void updateAddress() {
        String addressString = "No address found";

        if (lat != 0 && lon != 0) {
            // Update the position overlay.
            double latitude = lat;
            double longitude = lon;


            if (!Geocoder.isPresent())
                addressString = "No geocoder available";
            else {
                try {
                    List<Address> addresses = gc.getFromLocation(latitude, longitude, 1);
                    StringBuilder sb = new StringBuilder();
                    if (addresses.size() > 0) {
                        Address address = addresses.get(0);

                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
                            sb.append(address.getAddressLine(i)).append("\n");

                        sb.append(address.getLocality()).append("\n");
                        sb.append(address.getPostalCode()).append("\n");
                        sb.append(address.getCountryName());
                    }
                    addressString = sb.toString();
                } catch (IOException e) {
                    Log.d("WHEREAMI", "IO Exception", e);
                }
            }
        }

        myLocationText.setText("Indirizzo:\n" + "\n" + addressString);
    }

}