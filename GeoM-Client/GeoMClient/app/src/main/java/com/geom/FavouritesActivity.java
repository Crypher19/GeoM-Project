package com.geom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import classes.MyFile;
import classes.PublicTransport;
import classes.SharedData;
import classes.layout_classes.FavouritesListAdapter;

public class FavouritesActivity extends AppCompatActivity {
    SharedData s;
    ListView lv;
    FavouritesListAdapter fla;
    List<PublicTransport> favList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        s = (getIntent().getParcelableExtra("SharedData"));

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

        //costruisco e visualizzo la lista nell'activity
        lv = (ListView) findViewById(R.id.favListView);
        fla = new FavouritesListAdapter(
                FavouritesActivity.this, R.layout.pt_specific_item_list_layout,
                new ArrayList<>(s.favList));
        lv.setAdapter(fla);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(FavouritesActivity.this, MapActivity.class);
                //invio la posizione del preferito selezionato
                PublicTransport fav = s.favList.get(position);
                i.putExtra("SharedData", s);
                i.putExtra("favourite", (Parcelable) fav);
                i.putExtra("PreviousActivity", "FavouritesActivity");
                startActivityForResult(i, 4);
            }
        });

        //pressione lunga sul preferito (per eliminare)
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FavouritesActivity.this, R.style.AppCompatAlertDialogStyleLight);
                builder.setTitle("Eliminare questo preferito?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        String snackbarContent;

                        //ottengo il preferito
                        PublicTransport fav = (PublicTransport) parent.getItemAtPosition(position);
                        if(removeListItem(fav)){
                            snackbarContent = "Preferito eliminato";
                        } else snackbarContent = "ERRORE, preferito non eliminato";

                        if(s.favList.size() == 0){//ultimo preferito rimasto
                            Intent i = new Intent(FavouritesActivity.this, HomeActivity.class);
                            i.putExtra("snackbarContent", snackbarContent);
                            i.putExtra("SharedData", s);
                            //elimino la lista delle activity
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        } else Snackbar.make(view, snackbarContent, Snackbar.LENGTH_SHORT).show();//non è l'ultimo preferito
                    }
                });
                builder.setNegativeButton("ANNULLA", null);
                builder.show();

                return true;
            }
        });

        //aggiorno tradcinando verso il basso
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String message;
                if(refresh()){
                    message = "Preferiti aggiornati";
                } else message = "ERRORE, preferiti non aggiornati";

                Snackbar.make((findViewById(R.id.activity_favourites)), message, Snackbar.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);//termino l'animazione
            }
        });
    }

    public void refreshListContent(List<PublicTransport> favList){
        fla.clear();
        fla.addAll(favList);
        fla.notifyDataSetChanged();
    }

    public boolean removeListItem(PublicTransport fav){
        MyFile f = new MyFile();
        //rimuovo il preferito dalla lista e dal file xml
        favList = s.favList;

        if (favList.remove(fav) && f.removeFavourite(fav) == 0) {
            s.favList = favList;
            //se va tutto bene aggiorno la lista da visualizzare
            refreshListContent(s.favList);
            return true;
        } else
            return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fav_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {//aggiorno i preferiti
            String message;
            if(refresh()){
                message = "Preferiti aggiornati";
            } else message = "ERRORE, preferiti non aggiornati";

            Snackbar.make((findViewById(R.id.activity_favourites)), message, Snackbar.LENGTH_SHORT).show();
            return true;
        } else if(id == R.id.action_delete_all){//elimina tutti i preferiti

            AlertDialog.Builder builder = new AlertDialog.Builder(FavouritesActivity.this, R.style.AppCompatAlertDialogStyleLight);
            builder.setTitle("Eliminare tutti i preferiti?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int id) {
                    String snackbarContent;
                    Intent i;

                    if(deleteAll()){
                        snackbarContent = "Preferiti eliminati";
                    } else snackbarContent = "ERRORE, preferiti non eliminati";

                    i = new Intent(FavouritesActivity.this, HomeActivity.class);
                    i.putExtra("snackbarContent", snackbarContent);
                    i.putExtra("SharedData", s);
                    //elimino la lista delle activity
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(i, 3);
                }
            });

            builder.setNegativeButton("ANNULLA", null);
            builder.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(FavouritesActivity.this, HomeActivity.class);
        i.putExtra("SharedData", s);
        setResult(RESULT_OK, i);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent i) {
        super.onActivityResult(requestCode, resultCode, i);
        if(resultCode == RESULT_OK){
            s = i.getParcelableExtra("SharedData");
        }
    }

    public boolean refresh(){
        List<PublicTransport> favList;
        MyFile f = new MyFile();

        if((favList = f.getFavouritesList()) != null){
            s.favList = favList;
            refreshListContent(favList);
            return true;
        } else return false;
    }

    public boolean deleteAll(){
        List<PublicTransport> favList;
        MyFile f = new MyFile();

        if (f.removeAllFavourites() > -1) {
            favList = new ArrayList<>();//lista vuota
            s.favList = favList;
            refreshListContent(favList);
            return true;
        } else return false;
    }
}