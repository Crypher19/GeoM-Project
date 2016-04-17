package com.example.mattia.geom;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import classes.PublicTransport;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //ricevo extra
        Bundle b = getIntent().getExtras();

        //extra ricevuto da ChooseBusActivity o ChooseTrainActivity o FavouritesActivity
        if (b.containsKey("PublicTransport")){
            PublicTransport pt = b.getParcelable("PublicTransport");
        }

        //messaggio preferiti
        if(b.containsKey("snackbarContent")){
            Snackbar.make(findViewById(R.id.activity_map), b.getString("snackbarContent"), Snackbar.LENGTH_SHORT).show();
        }
    }

}
