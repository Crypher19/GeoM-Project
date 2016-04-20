package com.geom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import classes.LoadingThread;
import classes.MyFile;
import classes.PublicTransport;
import classes.SharedData;

public class MainActivity extends AppCompatActivity {

    MyFile f;
    Intent i;
    SharedData s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        s = new SharedData();
        f = new MyFile();
        i = new Intent(MainActivity.this, HomeActivity.class);

        LoadingThread lt = new LoadingThread(s);
        lt.start();

        try {
            lt.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //controllo e carico i preferiti
        if (f.getFavouritesList() != null) {
            s.favList = f.getFavouritesList();
        }

        //carico i mezzi di trasporto
        List<PublicTransport> PTList = new ArrayList<>();
        PTList.add(new PublicTransport("Bus", "Include ASF, Urbani e Internurbani", R.mipmap.ic_material_bus_grey));
        PTList.add(new PublicTransport("Treno", "Include Trennord, Trenitalia e Italo", R.mipmap.ic_material_train_grey));
        s.PTList = PTList;

        Log.i("sMESSAGE SHAREDDATAMAIN", s.busList.get(0).getPt_id() + " " + s.busList.get(0).getPt_name());

        //puntatore ai dati condivisi
        i.putExtra("SharedData", s);
        startActivity(i);
        //mainActivity non deve essere raggiungibile dall'utente
        finish();
    }
}
