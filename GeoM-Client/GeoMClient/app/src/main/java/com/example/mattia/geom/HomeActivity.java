package com.example.mattia.geom;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import classes.Bus;
import classes.Favourite;
import classes.PublicTransport;
import classes.Train;
import classes.layout_classes.PTListAdapter;

public class HomeActivity extends AppCompatActivity {
    List<PublicTransport> PTList;
    List<Bus> busList;
    List<Train> trainList;
    List<Favourite> favList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //prendo da MainActivity le tre liste "obbligatorie"
        PTList = getIntent().getParcelableArrayListExtra("PTList");
        busList = getIntent().getParcelableArrayListExtra("busList");
        trainList = getIntent().getParcelableArrayListExtra("trainList");

        //lista di mezzi di trasporto
        ListView lv = (ListView) findViewById(R.id.pt_listview);
        lv.setAdapter(new PTListAdapter(HomeActivity.this, R.layout.pt_item_list_layout, new ArrayList<>(PTList)));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, final View components, int pos, long id){

                PublicTransport pt = (PublicTransport) adapter.getItemAtPosition(pos);
                String pt_name = pt.getPTType();
                Intent i;
                if(pt_name.equals("Treno")){//scelgo il mezzo "Treno"
                    i = new Intent(HomeActivity.this, ChooseTrainActivity.class);
                    //prendo da MainActivity la lista "opzionale"
                    i.putParcelableArrayListExtra("trainList", new ArrayList<>(trainList));
                } else {//scelgo il mezzo "Bus"
                    i = new Intent(HomeActivity.this, ChooseBusActivity.class);
                    //prendo da MainActivity la lista "opzionale"
                    i.putParcelableArrayListExtra("busList", new ArrayList<>(busList));
                }
                startActivity(i);
            }
        });

        //pulsante preferiti
        FloatingActionButton favourite_fab = (FloatingActionButton) findViewById(R.id.favourites_fab);
        favourite_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //se ci sono preferiti
                if (getIntent().hasExtra("favList")) {
                    favList = getIntent().getParcelableArrayListExtra("favList");

                    Intent i = new Intent(HomeActivity.this, FavouritesActivity.class);
                    i.putParcelableArrayListExtra("favList", new ArrayList<>(favList));
                    startActivity(i);
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
}
