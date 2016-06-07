package com.geom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import classes.CoordThread;
import classes.PublicTransport;
import classes.SharedData;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SharedData s;
    private CoordThread ct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        s = getIntent().getBundleExtra("bundle").getParcelable("SharedData");
        //extra ricevuto da ChoosePTActivity o FavoritesActivity
        PublicTransport pt = getIntent().getBundleExtra("bundle").getParcelable("PublicTransport");

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Mappa di " + pt.getPt_name());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        Log.i("sMESSAGE ooooooooooo", pt.getPt_name());

        ct = new CoordThread(s, pt);
        ct.start();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void goBack(){
        Intent i;
        Bundle b = new Bundle();

        s.ricezioneCoord = false; // interrompo la ricezione delle coordinate

        if(s.goToChoosePTActivity && !s.goToFavouritesActivity){//devo tornare a ChoosePTActivity
            i = new Intent(MapsActivity.this, ChoosePTActivity.class);
            b.putParcelable("SharedData", s);
            i.putExtra("bundle", b);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            setResult(Activity.RESULT_OK, i);
            startActivity(i);

            s.goToChoosePTActivity = false;
        } else{//devo tornare a FavoritesActivity
            i = new Intent(MapsActivity.this, FavoritesActivity.class);
            s.goToFavouritesActivity = false;

            b.putParcelable("SharedData", s);
            i.putExtra("bundle", b);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            setResult(RESULT_OK);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed(){
        goBack();
    }
}
