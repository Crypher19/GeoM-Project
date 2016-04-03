package com.example.mattia.geom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import classes.Train;
import classes.layout_classes.TrainListAdapter;

/*Il metodo onClick sugli elementi della cardview è definito in TrainListAdapter*/
public class ChooseTrainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_train);
        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //ottengo dal HomeActivity la lista dei treni
        List<Train> trainList = getIntent().getParcelableArrayListExtra("trainList");

        //cardview di oggetti Treno
        RecyclerView recList = (RecyclerView) findViewById(R.id.train_recycler_view);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        TrainListAdapter ta = new TrainListAdapter(trainList);
        recList.setAdapter(ta);
    }

}
