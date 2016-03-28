package com.example.mattia.geom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import classes.Train;
import classes.layout_classes.TrainListAdapter;

public class ChooseTrainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_train);

        List<Train> TrainList= new ArrayList<>();
        TrainList.add(new Train("T01", "Milano-Asso"));
        TrainList.add(new Train("T02", "Milano-Venezia"));

        //list of Train objects in cardview
        RecyclerView recList = (RecyclerView) findViewById(R.id.train_recycler_view);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        TrainListAdapter ta = new TrainListAdapter(TrainList);
        recList.setAdapter(ta);
    }
}
