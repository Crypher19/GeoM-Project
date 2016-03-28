package com.example.mattia.geom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import classes.Bus;
import classes.layout_classes.BusListAdapter;

public class ChooseBusActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_bus);

        ArrayList<Bus> BusList= new ArrayList<>();
        BusList.add(new Bus("C-80", "Mariano Comense"));
        BusList.add(new Bus("C-81", "Cantu"));

        //list of Bus objects in cardview
        RecyclerView recList = (RecyclerView) findViewById(R.id.bus_recycler_view);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        BusListAdapter ba = new BusListAdapter(BusList);
        recList.setAdapter(ba);
    }
}
