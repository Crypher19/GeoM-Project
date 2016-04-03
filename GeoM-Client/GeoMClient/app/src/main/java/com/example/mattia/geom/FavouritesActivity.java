package com.example.mattia.geom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import classes.Favourite;
import classes.layout_classes.FavouritesListAdapter;

public class FavouritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

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

        //ottengo la lista dei preferiti
        List<Favourite> favList = getIntent().getParcelableArrayListExtra("favList");
        //costruisco e visualizzo la lista nell'activity
        ListView lv = (ListView) findViewById(R.id.favListView);
        lv.setAdapter(new FavouritesListAdapter(FavouritesActivity.this, R.layout.favourite_item_list_layout, new ArrayList<>(favList)));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Favourite f = (Favourite) parent.getItemAtPosition(position);
                Intent i = new Intent(FavouritesActivity.this, MapActivity.class);
                i.putExtra("favourite", f);
                startActivity(i);
            }
        });

        //pressione lunga sul preferito (per eliminare)
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FavouritesActivity.this, R.style.AppCompatAlertDialogStyleLight);
                builder.setTitle("Rimuovere questo preferito?");
                builder.setPositiveButton("OK", null);
                builder.setNegativeButton("ANNULLA", null);
                builder.show();

                return true;
            }
        });
    }

}
