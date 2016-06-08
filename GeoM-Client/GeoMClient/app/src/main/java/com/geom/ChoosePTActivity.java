package com.geom;


import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import classes.Connectivity;
import classes.LoadingThread;
import classes.PublicTransport;
import classes.SharedData;
import classes.layout_classes.ListViewDivider;
import classes.layout_classes.OnLoadMoreListener;
import classes.layout_classes.PublicTransportListAdapter;

/*implementazione metodi onClick di cardview in PublicTransportListAdapter*/
public class ChoosePTActivity extends AppCompatActivity {
    SharedData s;
    SwipeRefreshLayout swipeRefreshLayout;
    PublicTransportListAdapter publicTransportListAdapter;
    RecyclerView recyclerView;
    List<PublicTransport> pt_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_pt);

        s = getIntent().getBundleExtra("bundle").getParcelable("SharedData");

        //ottengo la lista di mezzi da visualizzare
        pt_list = s.getCurrentPTList();

        //quando passo da FavoritesActivity a ChoosePTActivity avendo eliminato tutti i preferiti
        if(getIntent().getBundleExtra("bundle").containsKey("snackbarContent")){
            Snackbar.make(findViewById(R.id.activity_choose_pt),
                    getIntent().getBundleExtra("bundle").getString("snackbarContent"),
                    Snackbar.LENGTH_LONG)
                    .show();
        }

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //il titolo varia in base alla lista visualizzata
        toolbar.setTitle("Nuovo " + s.pt_type);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        //CardView
        recyclerView = (RecyclerView) findViewById(R.id.pt_recycler_view);
        //divider
        recyclerView.addItemDecoration(new ListViewDivider(this, ListViewDivider.VERTICAL_LIST));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //inizializzo l'Adapter e la ProgressBar
        initAdapter();

        //SwypeRefreshLayout (trascino verso il basso per aggiornare)
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(refreshing, 2000);
            }
        });

        //FloatingActionButton per i preferiti
        FloatingActionButton favourites_fab
                = (FloatingActionButton) findViewById(R.id.favourites_fab);
        favourites_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!s.favList.isEmpty()) {
                    Intent i = new Intent(ChoosePTActivity.this, FavoritesActivity.class);
                    Bundle b = new Bundle();

                    s.goToChoosePTActivity = true;

                    b.putParcelable("SharedData", s);
                    i.putExtra("bundle", b);
                    startActivityForResult(i, 2);
                }//se non ci sono preferiti
                else {
                    showAlertDialog("Nessun preferito trovato", null);
                }
}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.choose_pt_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                //Log.i("GUI_LOG", "ho premuto CERCA");
                //mentre cerco la ProgressBar non deve apparire
                publicTransportListAdapter.showProgressBar=false;
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                //Log.i("GUI_LOG", "ho premuto BACK");
                //ricostruisco la lista
                initAdapter();
                //imposto la ProgressBar in modo che sia visibile
                publicTransportListAdapter.showProgressBar=true;
                return true;
            }
        });

        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView
                = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));

        SearchManager searchManager
                = (SearchManager) getSystemService(SEARCH_SERVICE);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //imposto l'hint
        searchView.setQueryHint(getResources().getString(R.string.search_hint));

        AutoCompleteTextView searchTextView
                = (AutoCompleteTextView) searchView.findViewById(
                android.support.v7.appcompat.R.id.search_src_text);

        //imposto il cursore custom
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(searchTextView, R.drawable.cursor);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


        //listener per la query di ricerca
        SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {

            @Override //metodo richiamato quando si preme sul tasto cerca
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override //metodo richiamato ogni volta che il testo cambia
            public boolean onQueryTextChange(String query) {

                //ricerca dinamica
                query = query.toLowerCase();

                filterList(query);

                return true;
            }
        };
        //aggiungo il listener a SearchView
        searchView.setOnQueryTextListener(listener);

        return true;
    }

    //Aggiorno quando trascino verso il basso
    private final Runnable refreshing = new Runnable() {
        public void run() {
            try {
                String message;
                swipeRefreshLayout.setRefreshing(true);

                if(Connectivity.isConnected(ChoosePTActivity.this)) {
                    pt_list.clear(); // svuoto completamente la lista
                    refresh();
                    //Log.i("GUI_LOG", "refresh eseguito");

                    publicTransportListAdapter.notifyDataSetChanged();
                    recyclerView.invalidate();
                    message = "Lista aggiornata";
                } else{
                    message = getString(R.string.internet_error_title);
                }

                //termino l'animazione
                swipeRefreshLayout.setRefreshing(false);

                //notifico all'utente l'esito dell'operazione
                Snackbar.make(findViewById(R.id.activity_choose_pt),
                        message, Snackbar.LENGTH_LONG).show();

            } catch (Exception e) {
                e.printStackTrace();
                //operazione NON andata a buon fine
                Snackbar.make(findViewById(R.id.activity_choose_pt),
                        "ERRORE, lista non aggiornata",
                        Snackbar.LENGTH_LONG)
                        .show();
            }
        }
    };

    //Carico più elementi quando arrivo in fondo alla lista
    private final Runnable loading = new Runnable() {
        boolean shown = false;
        public void run() {
            //rimuovo la ProgressBar da RecyclerView
            pt_list.remove(pt_list.size() - 1);
            publicTransportListAdapter.notifyItemRemoved(pt_list.size());

            //controllo se il dispositivo è connesso ad internet
            if(Connectivity.isConnected(ChoosePTActivity.this)) {
                //carico piu elementi
                loadMore();
                //Log.i("GUI_LOG", "loadmore eseguito");
                publicTransportListAdapter.notifyDataSetChanged();
            } else{//se non è connesso ad internet
                if(!shown) {
                    Snackbar.make(findViewById(R.id.activity_choose_pt),
                            getString(R.string.internet_error_title),
                            Snackbar.LENGTH_LONG)
                            .show();
                    shown = true;
                }
            }
            publicTransportListAdapter.setLoaded();
        }
    };

    //Carico più elementi quando arrivo alla fine della lista
    public void loadMore(){
        s.offset = pt_list.size();

        LoadingThread lt = new LoadingThread(s);
        lt.start();
        try {
            lt.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Aggiorno la lista quando trascino ferso il basso
    public void refresh(){
        LoadingThread lt = new LoadingThread(s);
        lt.start();
        try {
            lt.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //inizializzo PublicTransportListAdapter e OnLoadMoreListener
    public void initAdapter(){
        publicTransportListAdapter
                = new PublicTransportListAdapter(s, recyclerView);
        recyclerView.setAdapter(publicTransportListAdapter);

        //ProgressBar (scorro in basso per caricare più elementi)
        publicTransportListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add ProgressBar to RecyclerView
                pt_list.add(null);
                publicTransportListAdapter.notifyItemInserted(pt_list.size() - 1);

                /*Log.i("GUI_LOG", "prima del caricamento");
                for(int i = 0; i < pt_list.size(); i++){
                    if(pt_list.get(i) != null)
                        Log.i("GUI_LOG", "elemento in posizione " + i + ": " + pt_list.get(i).getPt_id());
                    else
                        Log.i("GUI_LOG", "elemento in posizione " + i + ": progressbar");
                }*/

                //carico più elementi
                new Handler().postDelayed(loading, 2000);
            }
        });
    }

    //filtro la lista in base alla query ricevuta
    public void filterList(String query){
        final List<PublicTransport> filteredList = new ArrayList<>();

        for (int i = 0; i < pt_list.size(); i++) {

            if(pt_list.get(i) != null){//evito di includere ProgressBar nella ricerca
                final String pt_name = pt_list.get(i).getPt_name().toLowerCase();
                final String pt_route = pt_list.get(i).getPt_route().toLowerCase();
                if (pt_name.contains(query) || pt_route.contains(query)) {
                    filteredList.add(pt_list.get(i));
                }
            }
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(ChoosePTActivity.this));
        publicTransportListAdapter = new PublicTransportListAdapter(filteredList, s, recyclerView);
        recyclerView.setAdapter(publicTransportListAdapter);
        publicTransportListAdapter.notifyDataSetChanged();  // data set changed
    }

    public void showAlertDialog(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(ChoosePTActivity.this,
                R.style.AppCompatAlertDialogStyleLight);
        if(title != null && !title.isEmpty())
            builder.setTitle(Html.fromHtml("<b>"+ title +"</b>"));
        if(message != null && !message.isEmpty())
            builder.setMessage(message);

        builder.setPositiveButton(Html.fromHtml("<b>"+ "OK" +"</b>"), null);
        builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent i) {
        super.onActivityResult(requestCode, resultCode, i);
        if(resultCode == RESULT_OK){
            s = getIntent().getBundleExtra("bundle").getParcelable("SharedData");
        }
    }

    public void goBack(){//da ChoosePTActivity a HomeActivity

        Intent i = new Intent(ChoosePTActivity.this, HomeActivity.class);
        Bundle b = new Bundle();

        s.goToHomeActivity = false;

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
