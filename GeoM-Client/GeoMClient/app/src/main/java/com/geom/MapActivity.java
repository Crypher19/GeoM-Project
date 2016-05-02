package com.geom;

import android.os.Bundle;
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

        s = getIntent().getBundleExtra("bundle").getParcelable("SharedData");

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

        //extra ricevuto da ChoosePTActivity o FavouritesActivity
        if (getIntent().getBundleExtra("bundle").containsKey("PublicTransport")){
            PublicTransport pt = getIntent().getBundleExtra("bundle").getParcelable("PublicTransport");
        }
    }
}
