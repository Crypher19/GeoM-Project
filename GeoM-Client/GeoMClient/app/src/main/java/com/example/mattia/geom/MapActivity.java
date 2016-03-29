package com.example.mattia.geom;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import classes.Favourite;

public class MapActivity extends AppCompatActivity {
    boolean allowBack;//posso tornare indietro

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

            allowBack = false;
        } else{//extra ricevuto da FavouritesActivity
            Favourite f = b.getParcelable("favourite");
            Snackbar.make(findViewById(R.id.activity_map), "ricevuto mezzo: " + f.getPt_name(), Snackbar.LENGTH_LONG).show();

            allowBack = true;
        }
    }

    @Override
    public void onBackPressed() {
        if(!allowBack){//apro HomeActivity (cancello i flag della coda)
            //evito di ritornare alla MapActivity
            Intent i = new Intent(MapActivity.this, HomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else super.onBackPressed(); //comportamento di default
    }

}
