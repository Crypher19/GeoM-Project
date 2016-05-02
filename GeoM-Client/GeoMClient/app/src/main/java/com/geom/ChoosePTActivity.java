package com.geom;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.List;

import classes.LoadingThread;
import classes.PublicTransport;
import classes.SharedData;
import classes.layout_classes.PublicTransportSpecificListAdapter;

/*implementazione metodi onClick di cardview in PublicTransportSpecificListAdapter*/
public class ChoosePTActivity extends AppCompatActivity {
    SharedData s;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recList;
    String pt_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_pt);

        s = getIntent().getParcelableExtra("SharedData");
        pt_type = s.pt_type;

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //il titolo varia in base alla lista visualizzata
        getSupportActionBar().setTitle("Nuovo " + pt_type);
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
        PublicTransportSpecificListAdapter publicTransportSpecificListAdapter
                = new PublicTransportSpecificListAdapter(s.getListType(pt_type));
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

        //pulsante preferiti
        FloatingActionButton favourites_fab = (FloatingActionButton) findViewById(R.id.favourites_fab);
        favourites_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!s.favList.isEmpty()) {
                    Intent i = new Intent(ChoosePTActivity.this, FavouritesActivity.class);
                    i.putExtra("SharedData", s);
                    i.putExtra("PreviousActivity", "ChoosePTActivity");
                    startActivityForResult(i, 2);
                }//se non ci sono preferiti
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChoosePTActivity.this, R.style.AppCompatAlertDialogStyleLight);
                    builder.setTitle("Nessun preferito trovato");
                    builder.setPositiveButton("OK", null);
                    builder.show();
                }
            }
        });
    }

    public boolean refresh(){//aggiorno la lista
        s.getListType(pt_type).clear(); // svuoto completamente la lista
        Log.i("BUSLIST", Integer.toString(s.busList.get(0).getPt_id()));

        // parte il thread per ottenere la nuova lista dal server
        LoadingThread lt = new LoadingThread(s);
        lt.start();

        try {
            lt.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // costruisco la nuova lista
        List<PublicTransport> temp = s.getListType(pt_type);

        /*aggiornamento lista*/

        recList.setAdapter(new PublicTransportSpecificListAdapter(temp));
        recList.invalidate();

        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent i) {
        super.onActivityResult(requestCode, resultCode, i);
        if(resultCode == RESULT_OK){
            s = i.getParcelableExtra("SharedData");
        }
    }
}
