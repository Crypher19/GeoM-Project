package com.example.mattia.geom;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import classes.Bus;
import classes.Favourite;
import classes.Train;

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

        if (b.containsKey("bus")){//extra ricevuto da ChooseBusActivity
            Bus bus = b.getParcelable("bus");
            System.out.println("Ricevuto bus " + bus.getPTName());
        } else if(b.containsKey("train")){//extra ricevuto da ChooseTrainActivity
            Train train = b.getParcelable("train");
            System.out.println("Ricevuto train " + train.getPTName());
        } else if(b.containsKey("favourite")){//extra ricevuto da FavouritesActivity
            Favourite favourite = b.getParcelable("favourite");
            System.out.println("Ricevuto favourite " + favourite.getPt_name());
        }

        //messaggio preferiti
        if(b.containsKey("snackbarContent")){
            Snackbar.make(findViewById(R.id.activity_map), b.getString("snackbarContent"), Snackbar.LENGTH_LONG).show();
        }
    }

}
