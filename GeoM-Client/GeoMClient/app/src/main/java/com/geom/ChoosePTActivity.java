package com.geom;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import classes.PublicTransport;
import classes.SharedData;
import classes.layout_classes.PublicTransportSpecificListAdapter;

/*implementazione metodi onClick di cardview in PublicTransportSpecificListAdapter*/
public class ChoosePTActivity extends AppCompatActivity {
    SharedData s;
    String pt_type;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_pt);

        s = getIntent().getParcelableExtra("SharedData");

        pt_type = getIntent().getStringExtra("pt_type");//tipo di lista da visualizzare

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

        recList = (RecyclerView) findViewById(R.id.pt_recycler_view);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        //CardView
        //in base al mezzo selezionato visualizzo la lista corrispondente
        PublicTransportSpecificListAdapter publicTransportSpecificListAdapter = new PublicTransportSpecificListAdapter(s.getListType(pt_type));
        recList.setAdapter(publicTransportSpecificListAdapter);

        //aggiorno tradcinando verso il basso
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String message;
                if(refresh()){
                    message = "Preferiti aggiornati";
                } else message = "ERRORE, preferiti non aggiornati";

                Snackbar.make((findViewById(R.id.activity_choose_pt)), message, Snackbar.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);//termino l'animazione
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(ChoosePTActivity.this, HomeActivity.class);
        i.putExtra("SharedData", s);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        setResult(RESULT_OK, i);
        startActivityForResult(i, 8);
    }

    public boolean refresh(){//aggiorno la lista
        List<PublicTransport> temp = s.getListType(pt_type);

        /*aggiornamento lista*/

        recList.setAdapter(new PublicTransportSpecificListAdapter(temp));
        recList.invalidate();

        return true;
    }
}
