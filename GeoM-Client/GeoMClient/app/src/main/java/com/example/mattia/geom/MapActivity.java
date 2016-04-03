package com.example.mattia.geom;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import classes.Favourite;

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
        if (b.containsKey("snackbarContent")){//extra ricevuto da ChooseTrainActivity o ChooseBusActivity
            String snackbarContent = b.getString("snackbarContent");
            Snackbar.make(findViewById(R.id.activity_map), snackbarContent, Snackbar.LENGTH_LONG).show();

        } else{//extra ricevuto da FavouritesActivity
            Favourite f = b.getParcelable("favourite");
            Snackbar.make(findViewById(R.id.activity_map), "ricevuto mezzo: " + f.getPt_name(), Snackbar.LENGTH_LONG).show();
        }
    }

}
