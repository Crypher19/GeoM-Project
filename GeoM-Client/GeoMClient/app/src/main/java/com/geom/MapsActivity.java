package com.geom;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import classes.PublicTransport;
import classes.SharedData;
import classes.UiGpsThread;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SharedData s;

    private GoogleApiClient client;

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

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        new Thread() {
            public void run() {
                int i = 0;
                while (s.ricezioneCoord) {
                    Double latitudine = s.listCoord.get(0);
                    Double longitudine = s.listCoord.get(1);

                    UiGpsThread t = new UiGpsThread(getApplicationContext(), mMap, latitudine, longitudine, i);
                    try {
                        runOnUiThread(t);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, "Maps Page", Uri.parse("http://host/path"),
                Uri.parse("android-app://com.geom/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, "Maps Page", Uri.parse("http://host/path"),
                Uri.parse("android-app://com.geom/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
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