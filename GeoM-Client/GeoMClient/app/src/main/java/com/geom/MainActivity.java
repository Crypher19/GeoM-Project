package com.geom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import classes.PublicTransport;
import classes.SharedData;

public class MainActivity extends AppCompatActivity {
    Intent i;
    SharedData s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        s = new SharedData();
        i = new Intent(MainActivity.this, HomeActivity.class);
        Bundle b = new Bundle();

        List<PublicTransport> temp;
        //controllo e carico i preferiti
        if ((temp = s.getFavsListFromFile()) != null) {
            s.favList = temp;
        }

        //carico i mezzi di trasporto
        List<PublicTransport> PTList = new ArrayList<>();
        PTList.add(new PublicTransport("bus", "Include ASF, Urbani e Internurbani",
                R.drawable.ic_material_bus_grey));
        PTList.add(new PublicTransport("treno", "Include Trennord, Trenitalia e Italo",
                R.drawable.ic_material_train_grey));
        s.PTList = PTList;

        //ottengo il valore della checkBox wifiOnly
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        s.wifiOnly = sharedPreferences.getBoolean("wifiOnly", false);
        //Log.i("GUI_LOG", "wifiOnly: " + Boolean.toString(s.wifiOnly));

        b.putParcelable("SharedData", s); //puntatore ai dati condivisi
        i.putExtra("bundle", b);
        startActivity(i);
        //mainActivity non deve essere raggiungibile dall'utente
        finish();
    }
}
