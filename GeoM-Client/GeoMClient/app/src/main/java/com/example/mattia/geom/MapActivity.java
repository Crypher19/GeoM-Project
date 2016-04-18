package com.example.mattia.geom;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import classes.PublicTransport;
import classes.SharedData;

public class MapActivity extends AppCompatActivity {
    SharedData s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        s = getIntent().getExtras().getParcelable("SharedData");

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

    @Override
    public void onBackPressed(){
        Intent i = new Intent(MapActivity.this, HomeActivity.class);
        i.putExtra("SharedData", s);
        //pulisco la lista delle activity
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        setResult(RESULT_OK, i);
        startActivity(i);
    }
}
