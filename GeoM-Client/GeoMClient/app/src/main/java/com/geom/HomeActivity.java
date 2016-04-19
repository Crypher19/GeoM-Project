package com.geom;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import classes.MyFile;
import classes.PublicTransport;
import classes.SharedData;
import classes.layout_classes.PublicTransportGenericListAdapter;

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

        s = getIntent().getParcelableExtra("SharedData");

        //quando passo da FavouritesActivity a HomeActivity avendo eliminato tutti i preferiti
        if(getIntent().hasExtra("snackbarContent")){
            Snackbar.make(findViewById(R.id.home_activity),
                getIntent().getStringExtra("snackbarContent"),
                Snackbar.LENGTH_SHORT)
                .show();
        }

        //lista di mezzi di trasporto
        ListView lv = (ListView) findViewById(R.id.pt_listview);
        lv.setAdapter(new PublicTransportGenericListAdapter(HomeActivity.this, R.layout.pt_generic_item_list_layout, new ArrayList<>(s.PTList)));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, final View components, int pos, long id) {
                //ottengo il tipo di mezzo di trasporto
                String pt_type = ((PublicTransport) adapter.getItemAtPosition(pos)).getPt_type();

                //intent ad un unica activity
                Intent i = new Intent(HomeActivity.this, ChoosePTActivity.class);
                i.putExtra("SharedData", s);
                i.putExtra("pt_type", pt_type); //indico la lista da visualizzare
                startActivityForResult(i, 1);
            }
        });

        //pulsante preferiti
        FloatingActionButton favourites_fab = (FloatingActionButton) findViewById(R.id.favourites_fab);
        favourites_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!s.favList.isEmpty()) {
                    Intent i = new Intent(HomeActivity.this, FavouritesActivity.class);
                    i.putExtra("SharedData", s);
                    startActivityForResult(i, 2);
                }//se non ci sono preferiti
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this, R.style.AppCompatAlertDialogStyleLight);
                    builder.setTitle("Nessun preferito trovato");
                    builder.setPositiveButton("OK", null);
                    builder.show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent i) {
        super.onActivityResult(requestCode, resultCode, i);
        if(resultCode == RESULT_OK){
            s = i.getParcelableExtra("SharedData");
        }
    }
}
