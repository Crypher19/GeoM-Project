package com.example.ricevitore_gps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                openMap();
            }
        });
    }

    protected void openMap()
    {
        double lat= 14,lon = 16;
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
