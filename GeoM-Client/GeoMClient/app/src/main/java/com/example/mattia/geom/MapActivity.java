package com.example.mattia.geom;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Bundle b = getIntent().getExtras();
        String snackbarContent = b.getString("snackbarContent");

        Snackbar.make(findViewById(R.id.activity_map), snackbarContent, Snackbar.LENGTH_LONG).show();
    }

    //apro HomeActivity (i flag sono cancellati)
    @Override
    public void onBackPressed() {
        Intent mainIntent = new Intent(MapActivity.this, HomeActivity.class);
        //evito di ritornare alla MapActivity
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
    }
}
