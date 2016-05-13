package com.geom;

import android.app.Activity;
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

        s = getIntent().getBundleExtra("bundle").getParcelable("SharedData");

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        //extra ricevuto da ChoosePTActivity o FavouritesActivity
        PublicTransport pt = getIntent().getBundleExtra("bundle").getParcelable("PublicTransport");

    }

    public void goBack(){
        Intent i;
        Bundle b = new Bundle();

        if(s.goToChoosePTActivity && !s.goToFavouritesActivity){//devo tornare a ChoosePTActivity
            i = new Intent();
            b.putParcelable("SharedData", s);
            i.putExtra("bundle", b);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            setResult(Activity.RESULT_OK, i);
            finish();

            s.goToChoosePTActivity = false;
        } else{//devo tornare a FavouritesActivity
            i = new Intent(MapActivity.this, FavouritesActivity.class);
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
