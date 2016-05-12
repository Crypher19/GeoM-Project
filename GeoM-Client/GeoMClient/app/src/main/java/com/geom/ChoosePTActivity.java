package com.geom;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import classes.PublicTransport;
import classes.SharedData;
import classes.layout_classes.OnLoadMoreListener;
import classes.layout_classes.PublicTransportCardViewAdapter;

/*implementazione metodi onClick di cardview in PublicTransportCardViewAdapter*/
public class ChoosePTActivity extends AppCompatActivity {
    SharedData s;
    SwipeRefreshLayout swipeRefreshLayout;
    PublicTransportCardViewAdapter publicTransportCardViewAdapter;
    RecyclerView recList;
    String pt_type;
    Handler handler = new Handler();
    String message = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_pt);

        s = getIntent().getBundleExtra("bundle").getParcelable("SharedData");
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
                goBack();
            }
        });

        //CardView
        recList = (RecyclerView) findViewById(R.id.pt_recycler_view);
        recList.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        publicTransportCardViewAdapter
                = new PublicTransportCardViewAdapter(s.getListType(pt_type), recList);
        recList.setAdapter(publicTransportCardViewAdapter);

        //ProgressBar (scorro in basso per caricare più elementi)
        publicTransportCardViewAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                s.getListType(pt_type).add(null);
                publicTransportCardViewAdapter.notifyItemInserted(s.getListType(pt_type).size() - 1);

                //Carico più elementi in RecyclerView
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //Tolgo la Progressbar dalla CardView
                        s.getListType(pt_type).remove(s.getListType(pt_type).size() - 1);
                        publicTransportCardViewAdapter.notifyItemRemoved(s.getListType(pt_type).size());

                        //carico più elementi
                        loadMore();

                        //notifico cambiamenti
                        publicTransportCardViewAdapter.notifyDataSetChanged();
                        publicTransportCardViewAdapter.setLoaded();
                    }
                }, 2000);
            }
        });

        //SwipeRefreshLayout (aggiorno trascinando verso il basso)
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.post(refreshing);
            }
        });

        if(message != null){//risultato dell'aggiornamento
            Snackbar.make((findViewById(R.id.activity_choose_pt)), message, Snackbar.LENGTH_LONG).show();
        }

        //FloatingActionButton per i preferiti
        FloatingActionButton favourites_fab = (FloatingActionButton) findViewById(R.id.favourites_fab);
        favourites_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!s.favList.isEmpty()) {
                    Intent i = new Intent(ChoosePTActivity.this, FavouritesActivity.class);
                    Bundle b = new Bundle();

                    s.goToChoosePTActivity = true;

                    b.putParcelable("SharedData", s);
                    i.putExtra("bundle", b);
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

    //Aggiorno quando trascino verso il basso
    private final Runnable refreshing = new Runnable() {
        public void run() {
            try {
            swipeRefreshLayout.setRefreshing(true);
            /*s.clearList(pt_type); // svuoto completamente la lista

            // parte il thread per ottenere la nuova lista dal server
            LoadingThread lt = new LoadingThread(s);
            lt.start();
            lt.join();*/

            // costruisco la nuova lista
            //List<PublicTransport> temp = s.getListType(pt_type);

            /* aggiornamento lista */
            /*recList.setAdapter(new PublicTransportCardViewAdapter(temp));
            recList.invalidate();
            */
            //termino l'animazione
            swipeRefreshLayout.setRefreshing(false);

            message = "Lista aggiornata";
            } catch (Exception e) {
            e.printStackTrace();
            message = "ERRORE, lista non aggiornata";
            }
        }
    };

    //Carico più elementi quando arrivo alla fine della lista
    public void loadMore(){
        //DA SOSTITUIRE CON QUERY SERVER
        if (pt_type.equals(PublicTransport.pt_type_bus)) {

            s.busList.add(new PublicTransport(21, "TESTBUS", "TEST", "TEST", "TEST", false, 12.5, 12.5));

        } else if(pt_type.equals(PublicTransport.pt_type_train)){
            s.trainList.add(new PublicTransport(21, "TESTTRAIN", "TEST", "TEST", "TEST", false, 12.5, 12.5));
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent i) {
        super.onActivityResult(requestCode, resultCode, i);
        if(resultCode == RESULT_OK){
            s = getIntent().getBundleExtra("bundle").getParcelable("SharedData");
        }
    }

    public void goBack(){//da ChoosePTActivity a HomeActivity

        //termino la ProgressBar
        publicTransportCardViewAdapter.setOnLoadMoreListener(null);

        Intent i = new Intent(ChoosePTActivity.this, HomeActivity.class);
        Bundle b = new Bundle();

        s.goToHomeActivity = false;

        b.putParcelable("SharedData", s);
        i.putExtra("bundle", b);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(i);
    }

    @Override
    public void onBackPressed(){
        goBack();
    }
}
