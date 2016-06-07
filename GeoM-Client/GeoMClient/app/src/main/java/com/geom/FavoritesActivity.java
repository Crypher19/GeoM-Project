package com.geom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import classes.PublicTransport;
import classes.SharedData;
import classes.layout_classes.FavoritesListAdapter;
import classes.layout_classes.ListViewDivider;
import classes.layout_classes.RecyclerItemClickListener;

public class FavoritesActivity extends AppCompatActivity {
    private SharedData s;

    FavoritesListAdapter favouritesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        s = getIntent().getBundleExtra("bundle").getParcelable("SharedData");

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        //lista di mezzi di trasporto
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.fav_recycler_view);
        //divider
        recyclerView.addItemDecoration(new ListViewDivider(this, ListViewDivider.VERTICAL_LIST));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        favouritesListAdapter = new FavoritesListAdapter(s);
        recyclerView.setAdapter(favouritesListAdapter);

        recyclerView.addOnItemTouchListener( new RecyclerItemClickListener(this, recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent i = new Intent(FavoritesActivity.this, MapsActivity.class);
                        Bundle b = new Bundle();
                        //invio la posizione del preferito selezionato
                        PublicTransport fav = s.favList.get(position);

                        s.goToFavouritesActivity = true;//devo tornare a FavoritesActivity

                        b.putParcelable("SharedData", s);
                        b.putParcelable("PublicTransport", fav);
                        i.putExtra("bundle", b);
                        startActivityForResult(i, 4);
                    }

                    @Override
                    public void onLongItemClick(View view, final int position) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(FavoritesActivity.this,
                                R.style.AppCompatAlertDialogStyleLight);
                        builder.setTitle("Eliminare questo preferito?");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                String snackbarContent;
                                PublicTransport pt = s.favList.get(position);

                                if(removeListItem(position)){
                                    snackbarContent = "Preferito eliminato";
                                } else snackbarContent = "ERRORE, preferito non eliminato";

                                if(s.favList.size() == 0){//ultimo preferito rimasto
                                    Intent i = new Intent(FavoritesActivity.this, HomeActivity.class);
                                    Bundle b = new Bundle();
                                    b.putString("snackbarContent", snackbarContent);
                                    b.putParcelable("SharedData", s);
                                    i.putExtra("bundle", b);
                                    startActivity(i);
                                } else{//non è l'ultimo preferito
                                    Snackbar.make(findViewById(R.id.activity_favourites), snackbarContent, Snackbar.LENGTH_LONG).show();
                                }
                            }
                        });
                        builder.setNegativeButton("ANNULLA", null);
                        builder.show();
                    }
                })
        );

        //aggiorno tradcinando verso il basso
        final SwipeRefreshLayout swipeRefreshLayout
                = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String message;
                if(refresh()){
                    message = "Preferiti aggiornati";
                } else message = "ERRORE, preferiti non aggiornati";

                Snackbar.make((findViewById(R.id.activity_favourites)), message,
                        Snackbar.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);//termino l'animazione
            }
        });
    }

    public boolean removeListItem(int position){
        PublicTransport pt = s.favList.get(position);

        if(pt != null) {
            if (s.removeFav(pt)) {
                //se va tutto bene aggiorno la lista da visualizzare
                favouritesListAdapter.removeItem(position);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.favorites_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {//aggiorno i preferiti
            String message;
            if(refresh()){
                message = "Preferiti aggiornati";
            } else message = "ERRORE, preferiti non aggiornati";

            Snackbar.make((findViewById(R.id.activity_favourites)), message,
                    Snackbar.LENGTH_LONG).show();
            return true;
        } else if(id == R.id.action_delete_all){//elimina tutti i preferiti

            AlertDialog.Builder builder = new AlertDialog.Builder(FavoritesActivity.this,
                    R.style.AppCompatAlertDialogStyleLight);
            builder.setTitle("Eliminare tutti i preferiti?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int id) {
                    String snackbarContent;
                    Intent i;
                    Bundle b = new Bundle();

                    if(deleteAll()){
                        snackbarContent = "Preferiti eliminati";
                    } else snackbarContent = "ERRORE, preferiti non eliminati";

                    i = new Intent(FavoritesActivity.this, HomeActivity.class);

                    b.putString("snackbarContent", snackbarContent);
                    b.putParcelable("SharedData", s);
                    i.putExtra("bundle", b);
                    startActivityForResult(i, 3);
                }
            });

            builder.setNegativeButton("ANNULLA", null);
            builder.show();
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean refresh(){
        List<PublicTransport> favList;

        if((favList = s.getFavsListFromFile()) != null){
            s.favList = favList;
            favouritesListAdapter.notifyDataSetChanged();
            return true;
        }

        return false;
    }

    public boolean deleteAll(){

        if (s.removeAllFavs()) {
            s.favList = new ArrayList<>();//lista vuota
            //refreshListContent(favList);
            return true;
        }
        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent i) {
        super.onActivityResult(requestCode, resultCode, i);
        if(resultCode == RESULT_OK){
            s = getIntent().getBundleExtra("bundle").getParcelable("SharedData");
        }
    }

    public void goBack(){
        Intent i;
        Bundle b = new Bundle();

        if(s.goToHomeActivity && !s.goToChoosePTActivity){//da FavoritesActivity a HomeActivity
            i = new Intent(FavoritesActivity.this, HomeActivity.class);
            s.goToHomeActivity = false;
        } else{//da FavoritesActivity a ChoosePTActivity
            i = new Intent(FavoritesActivity.this, ChoosePTActivity.class);
            s.goToChoosePTActivity = false;
        }

        b.putParcelable("SharedData", s);
        i.putExtra("bundle", b);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        setResult(RESULT_OK);
        startActivity(i);
    }

    @Override
    public void onBackPressed(){
        goBack();
    }
}