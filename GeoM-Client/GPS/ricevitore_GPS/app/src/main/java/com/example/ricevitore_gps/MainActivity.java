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
		/*Connessione c = new Connessione(d);
		c.start();

		TextView t1 = (TextView)findViewById(R.id.textView1);
		t1.setText("caricamento in corso...");

		try
		{
			c.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        double lat = 10;
        double lon = 10;
        intent.putExtra("latitudine", lat);
        intent.putExtra("longitudine", lon);
        startActivity(intent);
    }
}
