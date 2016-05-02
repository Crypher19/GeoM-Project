package com.geom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

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

        List<PublicTransport> temp;
        //controllo e carico i preferiti
        if ((temp = f.getFavouritesList()) != null) {
            s.favList = temp;
        }

        //carico i mezzi di trasporto
        List<PublicTransport> PTList = new ArrayList<>();
        PTList.add(new PublicTransport("bus", "Include ASF, Urbani e Internurbani",
                R.mipmap.ic_material_bus_grey));
        PTList.add(new PublicTransport("treno", "Include Trennord, Trenitalia e Italo",
                R.mipmap.ic_material_train_grey));
        s.PTList = PTList;

        i.putExtra("SharedData", s); //puntatore ai dati condivisi
        startActivity(i);
        //mainActivity non deve essere raggiungibile dall'utente
        finish();
    }
}
