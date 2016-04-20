package com.geom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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

        //carico la lista dei bus (DA SOSTITUIRE CON RICHIESTA SERVER)
        List<PublicTransport> busList= new ArrayList<>();
        busList.add(new PublicTransport(1, "Bus", "C-80", 11, "Cantu-Mariano", true, 12.3, 11.5));
        busList.add(new PublicTransport(2, "Bus", "C-81", 11, "Como-Mariano", false, 12.3, 12.5));
        s.busList = busList;

        //carico la lista dei treni (DA SOSTITUIRE CON RICHIESTA SERVER)
        List<PublicTransport> trainList= new ArrayList<>();
        trainList.add(new PublicTransport(3, "Treno", "T01", 12, "Milano-Asso", true, 14.5, 14.6));
        trainList.add(new PublicTransport(4, "Treno", "T02", 13, "Milano-Venezia", true, 15.1, 17.1));
        s.trainList = trainList;

        //puntatore ai dati condivisi
        i.putExtra("SharedData", s);
        startActivity(i);
        //mainActivity non deve essere raggiungibile dall'utente
        finish();
    }
}
