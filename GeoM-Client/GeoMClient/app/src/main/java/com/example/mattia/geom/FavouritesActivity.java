package com.example.mattia.geom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

import classes.Favourite;
import classes.MyFile;
import classes.SharedData;
import classes.layout_classes.FavouritesListAdapter;

public class FavouritesActivity extends AppCompatActivity {
    SharedData s;
    ListView lv;
    FavouritesListAdapter fla;
    List<Favourite> favList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

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
                FavouritesActivity.this, R.layout.favourite_item_list_layout,
                new ArrayList<>(s.getFavList()));
        lv.setAdapter(fla);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(FavouritesActivity.this, MapActivity.class);
                //invio la posizione del preferito selezionato
                Favourite fav = s.getFavouriteInPos(position);
                i.putExtra("favourite", fav);
                startActivity(i);
            }
        });

        //pressione lunga sul preferito (per eliminare)
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FavouritesActivity.this, R.style.AppCompatAlertDialogStyleLight);
                builder.setTitle("Rimuovere questo preferito?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        //ottengo il preferito
                        Favourite fav = (Favourite) parent.getItemAtPosition(position);
                        if(removeListItem(fav)){
                            Snackbar.make(view, "Preferito rimosso", Snackbar.LENGTH_SHORT).show();
                        } else{
                            Snackbar.make(view, "ERRORE, impossibile rimuovere il preferto", Snackbar.LENGTH_SHORT).show();//errore
                        }
                    }
                });
                builder.setNegativeButton("ANNULLA", null);
                builder.show();

                return true;
            }
        });
    }

    public void refreshListContent(List<Favourite> favList){
        fla.clear();
        fla.addAll(favList);
        fla.notifyDataSetChanged();
    }

    public boolean removeListItem(Favourite fav){
        MyFile f = new MyFile();
        //rimuovo il preferito dalla lista e dal file xml
        favList = s.getFavList();

        if (favList.remove(fav) && f.removeFavourite(fav) == 0) {
            s.setFavList(favList);
            //se va tutto bene aggiorno la lista da visualizzare
            refreshListContent(s.getFavList());
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
        if (id == R.id.action_refresh) {
            List<Favourite> favList;
            MyFile f = new MyFile();

            favList = f.getFavouritesList();
            s.setFavList(favList);
            refreshListContent(favList);

            Snackbar.make((findViewById(R.id.activity_favourites)), "Preferiti aggiornati", Snackbar.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}