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

        /*LoadingThread lt = new LoadingThread(s);
        lt.start();

        try {
            lt.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        s.busList.add(new PublicTransport(1, "bus", "c-81", "asf" ,"mariano-cantu", true, 12.5, 12.5));
        s.busList.add(new PublicTransport(2, "bus", "c-80", "asf" ,"mariano-arosio", false, 12.5, 12.5));

        s.trainList.add(new PublicTransport(3, "treno", "ff123", "trenord" ,"milano-asso", false, 12.5, 12.5));
        s.trainList.add(new PublicTransport(4, "treno", "ff456", "trenitalia" ,"milano-modena", true, 12.5, 12.5));

        List<PublicTransport> temp;
        //controllo e carico i preferiti
        if ((temp = f.getFavouritesList()) != null) {
            s.favList = temp;
        }

        //carico i mezzi di trasporto
        List<PublicTransport> PTList = new ArrayList<>();
        PTList.add(new PublicTransport("bus", "Include ASF, Urbani e Internurbani", R.mipmap.ic_material_bus_grey));
        PTList.add(new PublicTransport("treno", "Include Trennord, Trenitalia e Italo", R.mipmap.ic_material_train_grey));
        s.PTList = PTList;

        Log.i("sMESSAGE SHAREDDATAMAIN", s.busList.get(0).getPt_id() + " " + s.busList.get(0).getPt_name());

        //puntatore ai dati condivisi
        i.putExtra("SharedData", s);
        startActivity(i);
        //mainActivity non deve essere raggiungibile dall'utente
        finish();
    }
}
