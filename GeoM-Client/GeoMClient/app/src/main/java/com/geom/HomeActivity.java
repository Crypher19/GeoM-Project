package com.geom;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.InterruptedIOException;
import java.util.ArrayList;

import classes.LoadingThread;
import classes.MyFile;
import classes.PublicTransport;
import classes.SharedData;
import classes.layout_classes.PublicTransportListViewAdapter;

public class HomeActivity extends AppCompatActivity {

    SharedData s;
    MyFile f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        f = new MyFile();
        s = getIntent().getBundleExtra("bundle").getParcelable("SharedData");

        //reset di tutte le variabili per il goBack()
        s.goToHomeActivity = false;
        s.goToChoosePTActivity = false;
        s.goToFavouritesActivity = false;

        //quando passo da FavouritesActivity a HomeActivity avendo eliminato tutti i preferiti
        if(getIntent().getBundleExtra("bundle").containsKey("snackbarContent")){
            Snackbar.make(findViewById(R.id.activity_home),
                getIntent().getBundleExtra("bundle").getString("snackbarContent"),
                Snackbar.LENGTH_LONG)
                .show();
        }

        //lista di mezzi di trasporto
        ListView lv = (ListView) findViewById(R.id.pt_listview);
        lv.setAdapter(new PublicTransportListViewAdapter(HomeActivity.this,
                R.layout.pt_item_list_layout, new ArrayList<>(s.PTList)));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, final View components, int pos, long id) {
                //ottengo il tipo di mezzo di trasporto
                String pt_type = ((PublicTransport) adapter.getItemAtPosition(pos)).getPt_type();
                s.pt_type = pt_type;//tipo di lista da visualizzare
                initNewActivity();
            }
        });

        //pulsante preferiti
        FloatingActionButton favourites_fab = (FloatingActionButton) findViewById(R.id.favourites_fab);
        favourites_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!s.favList.isEmpty()) {
                    s.goToHomeActivity = true;//activity alla quale devo ritornare

                    Intent i = new Intent(HomeActivity.this, FavouritesActivity.class);
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
        getMenuInflater().inflate(R.menu.menu_home, menu);
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

        LoadingThread lt = new LoadingThread(s);
        lt.start();
        try {
            lt.join();
            Log.i("THREAD EXCEPTION", "JOIN ESEGUITA");
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.i("THREAD EXCEPTION", "TREAD TERMINATO PER TIMEOUT");
        }

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