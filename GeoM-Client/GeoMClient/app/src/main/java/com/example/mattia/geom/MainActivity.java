package com.example.mattia.geom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import classes.Bus;
import classes.Favourite;
import classes.MyFile;
import classes.PublicTransport;
import classes.SharedData;
import classes.Train;

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

        List<Favourite> favList;

        //controllo e carico i preferiti
        if ((favList = f.getFavouritesList()).size() > 0) {
            s.setFavList(favList);
        }

        //carico i mezzi di trasporto
        List<PublicTransport> PTList = new ArrayList<>();
        PTList.add(new PublicTransport("Bus", "Include ASF, Urbani e Internurbani", R.mipmap.ic_material_bus_grey));
        PTList.add(new PublicTransport("Treno", "Include Trennord, Trenitalia e Italo", R.mipmap.ic_material_train_grey));
        s.setPTList(PTList);

        //carico la lista dei bus (DA SOSTITUIRE CON RICHIESTA SERVER)
        ArrayList<Bus> busList= new ArrayList<>();
        busList.add(new Bus("C-80", "Mariano Comense"));
        busList.add(new Bus("C-81", "Cantu"));
        s.setBusList(busList);

        //carico la lista dei treni (DA SOSTITUIRE CON RICHIESTA SERVER)
        List<Train> trainList= new ArrayList<>();
        trainList.add(new Train("T01", "Milano-Asso"));
        trainList.add(new Train("T02", "Milano-Venezia"));
        s.setTrainList(trainList);

        //puntatore ai dati condivisi
        i.putExtra("SharedData", s);
        startActivity(i);
        //mainActivity non deve essere raggiungibile dall'utente
        finish();
    }
}
