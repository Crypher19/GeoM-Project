package com.geomclient;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		DatiCondivisi dc = new DatiCondivisi();
		ThreadSocket ts = new ThreadSocket(dc);
		ts.start();
		
		
		
	}
}
