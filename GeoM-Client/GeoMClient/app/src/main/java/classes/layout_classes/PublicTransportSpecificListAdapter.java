package classes.layout_classes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.geom.MapActivity;
import com.geom.R;

import java.util.ArrayList;
import java.util.List;

import classes.MyFile;
import classes.PublicTransport;
import classes.SharedData;

public class PublicTransportSpecificListAdapter
        extends RecyclerView.Adapter
                    <PublicTransportSpecificListAdapter.PublicTransportSpecificViewHolder> {

    private List<PublicTransport> pt_list;
    private MyFile f;
    private SharedData s;

    public PublicTransportSpecificListAdapter(List<PublicTransport> pt_list) {
        this.pt_list = pt_list;
    }

    @Override
    public int getItemCount() {
        return pt_list.size();
    }

    @Override
    public void onBindViewHolder(final PublicTransportSpecificViewHolder holder, int position) {

        PublicTransport pt = pt_list.get(position);
        holder.pt_name.setText(pt.getPt_name());
        holder.pt_route.setText(pt.getPt_route());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ottengo la posizione dell'elemento
                int pos = holder.getAdapterPosition();
                //ottengo l'elemento in posizion "pos"
                PublicTransport pt = pt_list.get(pos);

                //lancio MapActivity
                Intent i = new Intent(v.getContext(), MapActivity.class);
                Bundle b = new Bundle();
                b.putParcelable("PublicTransport", pt);
                b.putParcelable("SharedData", s);
                i.putExtra("bundle", b);
                v.getContext().startActivity(i);
            }
        });

        //stella gialla gli elementi della lista che sono anche preferiti
        for(int i = 0; i < s.favList.size(); i++){
            if(pt_list.get(position).equals(s.favList.get(i))){
                holder.fav_img.setImageResource(R.drawable.ic_favourites_star_full);
            }
        }

        //mezzo disponibile - non disponibile (pallino verde - rosso)
        if(pt_list.get(position).isPt_enabled()){
            holder.pt_enabled.setImageResource(R.drawable.ic_enabled_green);//mezzo disponibile
        } else holder.pt_enabled.setImageResource(R.drawable.ic_disabled_red);//mezzo non disponibile
    }

    @Override
    public PublicTransportSpecificViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        f = new MyFile();
        s = ((Activity) viewGroup.getRootView().getContext()).getIntent().getBundleExtra("bundle").
                getParcelable("SharedData");

        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.pt_item_card_layout, viewGroup, false);

        final PublicTransportSpecificViewHolder holder
                = new PublicTransportSpecificViewHolder(itemView);//nuovo ViewHolder

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
                                R.drawable.ic_favourites_star_empty).getConstantState())) {
                    //cambio immagine
                    holder.fav_img.setImageResource(R.drawable.ic_favourites_star_full);
                    //notifico il salvataggio all'utente
                    Snackbar.make(viewGroup.getRootView().findViewById(R.id.activity_choose_pt),
                            addFav(pt), Snackbar.LENGTH_SHORT).show();
                }
                else {//è un preferito
                    //cambio immagine
                    holder.fav_img.setImageResource(R.drawable.ic_favourites_star_empty);
                    //notifico l'eliminazione all'utente
                    Snackbar.make(viewGroup.getRootView().findViewById(R.id.activity_choose_pt),
                            removeFav(pt), Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        return holder;
    }

    public static class PublicTransportSpecificViewHolder extends RecyclerView.ViewHolder {
        protected TextView pt_name;
        protected TextView pt_route;
        protected ImageView fav_img;
        protected ImageView pt_enabled;

        public PublicTransportSpecificViewHolder(View v) {
            super(v);
            pt_name =  (TextView) v.findViewById(R.id.pt_name);
            pt_route = (TextView)  v.findViewById(R.id.pt_route);
            fav_img = (ImageView) v.findViewById(R.id.fav_img);
            pt_enabled = (ImageView) v.findViewById(R.id.pt_enabled);
        }
    }

    public String removeFav(PublicTransport fav){
        List<PublicTransport>favList = new ArrayList<>();//non eliminare!
        favList = s.favList;
        String _return;

        //rimuovo il preferito dalla lista e dal file xml
        if (f.removeFavourite(fav) == 0) {
            for(int i = 0; i < favList.size(); i++){
                if(favList.get(i).equals(fav)){
                    s.favList.remove(i);
                }
            }
            _return = "Preferito eliminato";
        } else
            _return = "ERRORE, preferito non eliminato";

        return _return;
    }

    public String addFav(PublicTransport fav){
        //salvo il preferito
        int addResult;
        String _return;

        if((addResult=f.addFavourite(fav))==0){
            s.favList.add(fav);//aggiorno la lista dei preferiti
            _return="Preferito aggiunto";
        }
        else if(addResult==-2) _return="ERRORE preferito gia esistente";
        else _return="ERRORE preferito non aggiunto";

        return _return;
    }
}