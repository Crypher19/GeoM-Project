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
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;;

import classes.Connectivity;
import classes.LoadingThread;
import classes.PublicTransport;
import classes.SharedData;
import classes.layout_classes.ListViewDivider;
import classes.layout_classes.PublicTransportGenericListAdapter;
import classes.layout_classes.RecyclerItemClickListener;

public class HomeActivity extends AppCompatActivity {

    private SharedData s;

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
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.generic_pt_recycler_view);
        //divider
        recyclerView.addItemDecoration(new ListViewDivider(this, ListViewDivider.VERTICAL_LIST));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        PublicTransportGenericListAdapter publicTransportGenericListAdapter = new PublicTransportGenericListAdapter(s.PTList);
        recyclerView.setAdapter(publicTransportGenericListAdapter);

        recyclerView.addOnItemTouchListener( new RecyclerItemClickListener(this, recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //controllo se il dispositivo è connesso alla rete (wifi o mobile)
                        if(Connectivity.isConnected(HomeActivity.this)){
                            //ottengo il tipo di mezzo di trasporto
                            s.pt_type = (s.PTList.get(position).getPt_type());//tipo di lista da visualizzare
                            initNewActivity();
                        } else{//se il dispositivo non è connesso
                            showAlertDialog(getString(R.string.internet_error_title),
                                    getString(R.string.internet_error_message));
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {}
                })
        );

        //pulsante preferiti
        FloatingActionButton favourites_fab = (FloatingActionButton) findViewById(R.id.favourites_fab);
        assert favourites_fab != null;
        favourites_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!s.favList.isEmpty()) {
                    s.goToHomeActivity = true;//activity alla quale devo ritornare

                    Intent i = new Intent(HomeActivity.this, FavoritesActivity.class);
                    Bundle b = new Bundle();

                    b.putParcelable("SharedData", s);
                    i.putExtra("bundle", b);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivityForResult(i, 2);
                    finish();
                }//se non ci sono preferiti
                else {
                    showAlertDialog("Nessun preferito trovato", null);
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

        load();

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

    public void load(){
        /*LoadingThread lt = new LoadingThread(s);
        lt.start();
        try {
            lt.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        s.trainList.add(new PublicTransport(1, "aa", "aa", "aa", "aa", true, 12.0, 12.0));
        s.trainList.add(new PublicTransport(2, "bb", "bb", "bb", "bb", true, 12.0, 12.0));
        s.trainList.add(new PublicTransport(3, "cc", "cc", "cc", "cc", true, 12.0, 12.0));
        s.trainList.add(new PublicTransport(4, "dd", "dd", "dd", "dd", true, 12.0, 12.0));
        s.trainList.add(new PublicTransport(5, "ee", "ee", "ee", "ee", true, 12.0, 12.0));
        s.trainList.add(new PublicTransport(6, "ff", "ff", "ff", "ff", true, 12.0, 12.0));
        s.trainList.add(new PublicTransport(7, "gg", "gg", "gg", "gg", true, 12.0, 12.0));
        s.trainList.add(new PublicTransport(8, "hh", "hh", "hh", "hh", true, 12.0, 12.0));
        s.trainList.add(new PublicTransport(8, "ii", "ii", "ii", "ii", true, 12.0, 12.0));
        s.trainList.add(new PublicTransport(9, "ll", "ll", "ll", "ll", true, 12.0, 12.0));
        s.trainList.add(new PublicTransport(10, "mm", "mm", "mm", "mm", true, 12.0, 12.0));
    }

    public void showAlertDialog(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this,
                R.style.AppCompatAlertDialogStyleLight);
        if(title != null && !title.isEmpty())
            builder.setTitle(Html.fromHtml("<b>" + title + "<b>"));
        if(message != null && !message.isEmpty())
            builder.setMessage(message);

        builder.setPositiveButton(Html.fromHtml("<b>" + getString(R.string.ok_string) + "<b>"), null);
        builder.show();
    }
}