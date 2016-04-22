package com.geom;

import android.content.Intent;
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

        //extra ricevuto da ChoosePTActivity o FavouritesActivity
        if (b.containsKey("PublicTransport")){
            PublicTransport pt = b.getParcelable("PublicTransport");
        }
    }

    @Override
    public void onBackPressed(){
        String previousActivity = getIntent().getStringExtra("PreviousActivity");

        Intent i;
        switch (previousActivity) {
            case "ChoosePTActivity": //l'activity precedente è ChoosePTActivity
                i = new Intent(MapActivity.this, HomeActivity.class);
                break;
            case "FavouritesActivity": //l'activity precedente è FavouritesActivity
                i = new Intent(MapActivity.this, FavouritesActivity.class);
                break;
            default: //default
                i = new Intent(MapActivity.this, HomeActivity.class);
                break;
        }

        i.putExtra("SharedData", s);
        setResult(RESULT_OK, i);
        //pulisco la lista delle activity
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
