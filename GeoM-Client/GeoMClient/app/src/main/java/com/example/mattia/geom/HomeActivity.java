package com.example.mattia.geom;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import classes.Favourite;
import classes.MyFile;
import classes.layout_classes.PTListAdapter;
import classes.PublicTransport;

public class HomeActivity extends AppCompatActivity {

    MyFile f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        f = new MyFile();

        FloatingActionButton favourite_fab = (FloatingActionButton) findViewById(R.id.favourites_fab);
        favourite_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Favourite> favouriteList = new ArrayList<>();
                //if ((favouriteList = f.getFavouritesList()).size() > 0) {//ci sono preferiti

                //} else {//non ci sono preferiti
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this, R.style.AppCompatAlertDialogStyleLight);
                builder.setTitle("Nessun preferito trovato");
                builder.setPositiveButton("OK", null);
                builder.show();
                //}
            }
        });

        //-----------------------------test-------------------
        FloatingActionButton test_fab = (FloatingActionButton) findViewById(R.id.test_fab);
        test_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Favourite> favouriteList = new ArrayList<>();
                //if ((favouriteList = f.getFavouritesList()).size() > 0) {//ci sono preferiti

                //} else {//non ci sono preferiti
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this, R.style.AppCompatAlertDialogStyleDark);
                builder.setTitle("Nessun preferito trovato");
                builder.setPositiveButton("OK", null);
                builder.show();
                //}
            }
        });
        //----------------------------fine test---------------

        ArrayList<PublicTransport> PTList = new ArrayList<>();
        PTList.add(new PublicTransport("Treno", "Include Trennord, Trenitalia e Italo", R.mipmap.ic_material_train_grey));
        PTList.add(new PublicTransport("Bus", "Include ASF, Urbani e Internurbani", R.mipmap.ic_material_bus_grey));

        //list of Public Transport objects
        ListView lv = (ListView) findViewById(R.id.pt_listview);
        lv.setAdapter(new PTListAdapter(HomeActivity.this, R.layout.pt_item_list_layout, PTList));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, final View components, int pos, long id){

                 PublicTransport pt = (PublicTransport) adapter.getItemAtPosition(pos);
                 String pt_name = pt.getPTType();
                 Intent i;
                 if(pt_name.equals("Treno")){
                     i = new Intent(HomeActivity.this, ChooseTrainActivity.class);
                 } else {
                     i = new Intent(HomeActivity.this, ChooseBusActivity.class);
                 }
                 startActivity(i);
             }
        });
    }
}
