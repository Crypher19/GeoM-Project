package com.geom;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;;

import classes.PublicTransport;
import classes.SharedData;
import classes.layout_classes.ListViewDivider;
import classes.layout_classes.PublicTransportGenericListAdapter;
import classes.layout_classes.RecyclerItemClickListener;

public class HomeActivity extends AppCompatActivity {

    private SharedData s;
    private RecyclerView recyclerView;
    private PublicTransportGenericListAdapter publicTransportGenericListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        s = getIntent().getBundleExtra("bundle").getParcelable("SharedData");

        //reset di tutte le variabili per il goBack()
        s.goToHomeActivity = false;
        s.goToChoosePTActivity = false;
        s.goToFavouritesActivity = false;

        //quando passo da FavoritesActivity a HomeActivity avendo eliminato tutti i preferiti
        if(getIntent().getBundleExtra("bundle").containsKey("snackbarContent")){
            Snackbar.make(findViewById(R.id.activity_home),
                getIntent().getBundleExtra("bundle").getString("snackbarContent"),
                Snackbar.LENGTH_LONG)
                .show();
        }

        //lista di mezzi di trasporto
        recyclerView = (RecyclerView) findViewById(R.id.generic_pt_recycler_view);
        //divider
        recyclerView.addItemDecoration(new ListViewDivider(this, ListViewDivider.VERTICAL_LIST));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        publicTransportGenericListAdapter = new PublicTransportGenericListAdapter(s.PTList);
        recyclerView.setAdapter(publicTransportGenericListAdapter);

        recyclerView.addOnItemTouchListener( new RecyclerItemClickListener(this,recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //ottengo il tipo di mezzo di trasporto
                        s.pt_type = (s.PTList.get(position).getPt_type());//tipo di lista da visualizzare
                        initNewActivity();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {}
                })
        );

        //pulsante preferiti
        FloatingActionButton favourites_fab = (FloatingActionButton) findViewById(R.id.favourites_fab);
        favourites_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!s.favList.isEmpty()) {
                    s.goToHomeActivity = true;//activity alla quale devo ritornare

                    Intent i = new Intent(HomeActivity.this, FavoritesActivity.class);
                    Bundle b = new Bundle();

                    b.putParcelable("SharedData", s);
                    i.putExtra("bundle", b);
                    startActivityForResult(i, 2);
                }//se non ci sono preferiti
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this,
                            R.style.AppCompatAlertDialogStyleLight);
                    builder.setTitle("Nessun preferito trovato");
                    builder.setPositiveButton("OK", null);
                    builder.show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //costruisco il menù
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();//opzione selezionata

        if (id == R.id.action_settings) {//scelgo impostazioni
            Intent i = new Intent(HomeActivity.this, SettingsActivity.class);
            Bundle b = new Bundle();

            s.goToHomeActivity = true;//torno alla HomeActivity
            b.putParcelable("SharedData", s);
            i.putExtra("bundle", b);
            startActivityForResult(i, 3);
        }

        return super.onOptionsItemSelected(item);
    }

    public void initNewActivity(){
        Intent i = new Intent(HomeActivity.this, ChoosePTActivity.class);
        Bundle b = new Bundle();

        s.offset = 0;

        /*LoadingThread lt = new LoadingThread(s);
        lt.start();
        try {
            lt.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        s.trainList.add(new PublicTransport(1, "treno", "R21753", "Trenord", "Milano-Asso", true, 12.11, 14.02));
        s.trainList.add(new PublicTransport(2, "treno", "R20899", "Trenord", "Treviso-Venezia", true, 12.11, 14.02));
        s.trainList.add(new PublicTransport(3, "treno", "ETR500", "Trenitalia", "Napoli-Venezia", false, 12.11, 14.02));
        s.trainList.add(new PublicTransport(4, "treno", "444444", "Trenord", "Milano-Asso", true, 12.11, 14.02));
        s.trainList.add(new PublicTransport(5, "treno", "555555", "Trenord", "Treviso-Venezia", true, 12.11, 14.02));
        s.trainList.add(new PublicTransport(6, "treno", "666666", "Trenitalia", "Napoli-Venezia", false, 12.11, 14.02));
        s.trainList.add(new PublicTransport(7, "treno", "777777", "Trenord", "Milano-Asso", true, 12.11, 14.02));
        s.trainList.add(new PublicTransport(8, "treno", "888888", "Trenord", "Treviso-Venezia", true, 12.11, 14.02));
        s.trainList.add(new PublicTransport(9, "treno", "999999", "Trenitalia", "Napoli-Venezia", false, 12.11, 14.02));
        s.trainList.add(new PublicTransport(10, "treno", "101010", "Trenord", "Milano-Asso", true, 12.11, 14.02));
        s.trainList.add(new PublicTransport(11, "treno", "111111", "Trenord", "Treviso-Venezia", true, 12.11, 14.02));
        s.trainList.add(new PublicTransport(12,"treno", "121212", "Trenitalia", "Napoli-Venezia", false, 12.11, 14.02));

        s.goToHomeActivity = true;//torno alla HomeActivity

        b.putParcelable("SharedData", s);
        i.putExtra("bundle", b);
        startActivityForResult(i, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent i) {
        super.onActivityResult(requestCode, resultCode, i);
        if(resultCode == RESULT_OK){
            s = getIntent().getBundleExtra("bundle").getParcelable("SharedData");
        }
    }
}