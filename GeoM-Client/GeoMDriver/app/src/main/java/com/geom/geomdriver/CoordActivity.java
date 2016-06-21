package com.geom.geomdriver;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.geom.geomdriver.classes.PublicTransport;
import com.geom.geomdriver.classes.SharedData;
import com.geom.geomdriver.classes.threads.ThreadLocation;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class CoordActivity extends AppCompatActivity {
    private SharedData s;
    private GoogleApiClient client;

    TextView myLocationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coord);

        s = getIntent().getBundleExtra("bundle").getParcelable("SharedData");
        PublicTransport pt = s.pt;

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //il titolo varia in base alla lista visualizzata
        if(toolbar != null && pt != null)
            toolbar.setTitle("Login effettuato");
        setSupportActionBar(toolbar);

        //pulsante logout
        Button logout_btn = (Button) findViewById(R.id.btn_logout);
        if(logout_btn != null) {
            logout_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(),
                            R.style.AppCompatAlertDialogStyleLight);
                    builder.setTitle(Html.fromHtml("<b>"+ getString(R.string.warning_title) +"</b>"));
                    builder.setMessage(getString(R.string.warning_message));
                    builder.setPositiveButton(getString(R.string.ok_string), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            s.sendCoord = false; // termino l'invio di coordinate
                            Log.i("sMESSAGE CAMBIATO", "s.sendCoord = " + s.sendCoord);
                            Intent i = new Intent(CoordActivity.this, HomeActivity.class);
                            startActivity(i);
                            finish();
                        }
                    });
                    builder.setNegativeButton(getString(R.string.undo_string), null);
                    builder.show();
                }
            });
        }

        //nome utente
        TextView usernameTextView = (TextView) findViewById(R.id.usernameTextView);
        usernameTextView.setText("Sei autenticato come " + s.username);

        //mezzo scelto
        TextView ptTextView = (TextView) findViewById(R.id.ptTextView);
        ptTextView.setText("Mezzo: " + pt.getPt_name());

        String svcName = Context.LOCATION_SERVICE;
        LocationManager locationManager = (LocationManager) getSystemService(svcName);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        final String provider = locationManager.getBestProvider(criteria, true);

        myLocationText = (TextView) findViewById(R.id.textViewCoord);

        ThreadLocation t = new ThreadLocation(s, this, locationManager, myLocationText, provider);
        t.start();

        //locationManager.requestLocationUpdates(provider, 2000, 10, locationListener); //metodo per aggiornare la posizione periodicamente

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Second Page",
                Uri.parse("http://host/path"),
                Uri.parse("android-app://com.geom.geomdriver/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Second Page",
                Uri.parse("http://host/path"),
                Uri.parse("android-app://com.geom.geomdriver/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CoordActivity.this,
                R.style.AppCompatAlertDialogStyleLight);
        builder.setTitle(Html.fromHtml("<b>"+ getString(R.string.warning_title) +"</b>"));
        builder.setMessage(getString(R.string.warning_message));
        builder.setPositiveButton(getString(R.string.ok_string), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                s.sendCoord = false; // termino l'invio di coordinate
                Log.i("sMESSAGE CAMBIATO", "s.sendCoord = " + s.sendCoord);
                //continuo con onBackPressed normalmente
                CoordActivity.super.onBackPressed();
            }
        });
        builder.setNegativeButton(getString(R.string.undo_string), null);
        builder.show();
    }

    @Override
    public void onDestroy() {
        s.sendCoord = false;
        Log.i("sMESSAGE DESTROY", "s.sendCoord = " + s.sendCoord);
        super.onDestroy();
    }

}