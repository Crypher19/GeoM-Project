package classes.layout_classes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.geom.MapActivity;
import com.geom.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import classes.PublicTransport;
import classes.SharedData;

public class PublicTransportListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public final int VIEW_TYPE_ITEM = 0;
    public final int VIEW_TYPE_LOADING = 1;

    private SharedData s;

    private OnLoadMoreListener mOnLoadMoreListener;

    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    protected final List<PublicTransport> pt_list;

    protected final List<PublicTransport> filteredPTList;

    public PublicTransportListAdapter(SharedData s, RecyclerView recyclerView) {

        this.s = s;
        pt_list = s.getCurrentPTList();
        this.filteredPTList = new ArrayList<>();

        final LinearLayoutManager linearLayoutManager
                = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }


    public PublicTransportListAdapter(List<PublicTransport> pt_list, SharedData s, RecyclerView recyclerView) {

        this.s = s;
        this.pt_list = pt_list;
        this.filteredPTList = new ArrayList<>();

        final LinearLayoutManager linearLayoutManager
                = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return pt_list.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int viewType) {

        if(pt_list.size() > 0 && pt_list != null){
            if (viewType == VIEW_TYPE_ITEM) {

                View itemView = LayoutInflater.
                        from(viewGroup.getContext()).
                        inflate(R.layout.pt_item_list_layout, viewGroup, false);
                return new ListViewHolder(itemView);

            } else if (viewType == VIEW_TYPE_LOADING) {
                View view = LayoutInflater.from(viewGroup.getRootView().getContext())
                        .inflate(R.layout.pt_list_footer_loading, viewGroup, false);
                return new LoadingViewHolder(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        if(pt_list.size() > 0 && pt_list != null) {

            if (viewHolder instanceof ListViewHolder) {//eseguo solo se in OnCreateViewHolder ho scelto la CardView

                final ListViewHolder holder = (ListViewHolder) viewHolder;

                PublicTransport pt = pt_list.get(position);
                holder.pt_name.setText(pt.getPt_name());
                holder.pt_route.setText(pt.getPt_route());

                //apro mapActivity cliccando sull'elemento
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();//ottengo la posizione dell'elemento
                        PublicTransport pt = pt_list.get(pos);//ottengo l'elemento in posizion "pos"

                        //lancio MapActivity
                        Intent i = new Intent(v.getContext(), MapActivity.class);
                        Bundle b = new Bundle();

                        s.goToChoosePTActivity = true;//devo tornare a ChoosePTActivity
                        b.putParcelable("PublicTransport", pt);
                        b.putParcelable("SharedData", s);
                        i.putExtra("bundle", b);
                        ((Activity) v.getRootView().getContext()).setResult(Activity.RESULT_OK);
                        ((Activity) v.getRootView().getContext()).startActivityForResult(i, 4);
                    }
                });

                //salvo - elimino i preferiti cliccando sulla stella
                holder.fav_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //ottengo la posizione dell'elemento selezionato
                        int pos = holder.getAdapterPosition();

                        //ottengo l'elemento in posizion "pos"
                        PublicTransport pt = pt_list.get(pos);

                        if (holder.fav_img.getDrawable().getConstantState().equals(//non è un preferito
                                ContextCompat.getDrawable(v.getContext(),
                                        R.drawable.ic_favorites_star_empty).getConstantState())) {

                            String message = addFav(pt);
                            //cambio immagine
                            holder.fav_img.setImageResource(R.drawable.ic_favorites_star_full);
                            //notifico il salvataggio all'utente
                            Snackbar.make(v.getRootView().findViewById(R.id.activity_choose_pt),
                                    message, Snackbar.LENGTH_LONG).show();

                        } else {//è un preferito

                            String message = removeFav(pos);
                            //cambio immagine
                            holder.fav_img.setImageResource(R.drawable.ic_favorites_star_empty);
                            //notifico l'eliminazione all'utente
                            Snackbar.make(v.getRootView().findViewById(R.id.activity_choose_pt),
                                    message, Snackbar.LENGTH_LONG).show();
                        }
                    }
                });


                //stella gialla gli elementi della lista che sono anche preferiti
                for (int i = 0; i < s.favList.size(); i++) {
                    if (pt_list.get(position).equals(s.favList.get(i))) {
                        holder.fav_img.setImageResource(R.drawable.ic_favorites_star_full);
                    }
                }

                //mezzo disponibile - non disponibile (pallino verde - rosso)
                if (pt_list.get(position).isPt_enabled()) {
                    holder.pt_enabled.setImageResource(R.drawable.ic_enabled_green);//mezzo disponibile
                } else
                    holder.pt_enabled.setImageResource(R.drawable.ic_disabled_red);//mezzo non disponibile*/

            } else if (viewHolder instanceof LoadingViewHolder) {//eseguo solo se in OnCreateViewHolder ho scelto la ProgressBar
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) viewHolder;
                loadingViewHolder.progressBar.setIndeterminate(true);
            }
        }
    }

    public String removeFav(int posInPtList) {
        String _return;

        //il preferito non è nella stessa posizione in pt_list e fav_list
        int posInFavList = -1;

        //ottengo l'oggetto in pt_list
        PublicTransport temp = pt_list.get(posInPtList);

        //trovo la sua posizione in fav_list
        for (int i = 0; i < s.favList.size(); i++) {
            if (temp.equals(s.favList.get(i))) {
                posInFavList = i;
                break;
            }
        }

        Log.i("REMOVE_FAVOURITE", "Elemento in pt_list: " + posInPtList);
        Log.i("REMOVE_FAVOURITE", "Elemento in fav_list: " + posInFavList);

        //rimuovo l'elemento alla posizione reale in fav_list
        if (s.removeFav(posInFavList)) {
            _return = "Preferito eliminato";
        } else
            _return = "ERRORE, preferito non eliminato";

        return _return;
    }

    public String addFav(PublicTransport fav){
        String _return;

        if(s.addFav(fav)){
            _return = "Preferito aggiunto";
        } else _return = "ERRORE, preferito non aggiunto";

        return _return;
    }

    @Override
    public int getItemCount() {
        return this.pt_list == null ? 0 : this.pt_list.size();
    }

    public void setLoaded() {
        isLoading = false;
    }
}

class LoadingViewHolder extends RecyclerView.ViewHolder {
    public ProgressBar progressBar;

    public LoadingViewHolder(View itemView) {
        super(itemView);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
    }
}

class ListViewHolder extends RecyclerView.ViewHolder {
    protected TextView pt_name;
    protected TextView pt_route;
    protected ImageView fav_img;
    protected ImageView pt_enabled;

    public ListViewHolder(View v) {
        super(v);
        pt_name =  (TextView) v.findViewById(R.id.pt_name);
        pt_route = (TextView)  v.findViewById(R.id.pt_route);
        fav_img = (ImageView) v.findViewById(R.id.fav_img);
        pt_enabled = (ImageView) v.findViewById(R.id.pt_enabled);
    }
}