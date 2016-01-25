package com.geomclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private Button btnTransport;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnTransport = (Button) findViewById(R.id.button1);
		
		btnTransport.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent transportIntent = new Intent(MainActivity.this, TransportActivity.class);
				startActivity(transportIntent);
			}
		});
	}
}
