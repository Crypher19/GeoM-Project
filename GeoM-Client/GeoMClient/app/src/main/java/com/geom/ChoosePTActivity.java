package com.geom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import classes.SharedData;
import classes.layout_classes.PublicTransportSpecificListAdapter;

/*implementazione metodi onClick di cardview in PublicTransportSpecificListAdapter*/
public class ChoosePTActivity extends AppCompatActivity {
    SharedData s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_pt);

        s = getIntent().getParcelableExtra("SharedData");

        String pt_type = getIntent().getStringExtra("pt_type");//tipo di lista da visualizzare

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Nuovo " + pt_type);//il titolo varia in base alla lista visualizzata
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        RecyclerView recList = (RecyclerView) findViewById(R.id.pt_recycler_view);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        PublicTransportSpecificListAdapter publicTransportSpecificListAdaptera;

        if(pt_type.equals("Bus")){//cardview di Bus
            publicTransportSpecificListAdaptera = new PublicTransportSpecificListAdapter(s.busList);
            recList.setAdapter(publicTransportSpecificListAdaptera);
        } else if(pt_type.equals("Treno")){//cardview di Train
            publicTransportSpecificListAdaptera = new PublicTransportSpecificListAdapter(s.trainList);
            recList.setAdapter(publicTransportSpecificListAdaptera);
        }
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(ChoosePTActivity.this, HomeActivity.class);
        i.putExtra("SharedData", s);
        //i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        setResult(RESULT_OK, i);
        startActivityForResult(i, 8);
    }
}
