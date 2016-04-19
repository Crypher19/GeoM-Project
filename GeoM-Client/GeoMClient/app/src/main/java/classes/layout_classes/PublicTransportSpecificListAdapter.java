package classes.layout_classes;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geom.MapActivity;
import com.geom.R;

import java.util.List;

import classes.MyFile;
import classes.PublicTransport;
import classes.SharedData;

public class PublicTransportSpecificListAdapter
        extends RecyclerView.Adapter<PublicTransportSpecificListAdapter.PublicTransportSpecificViewHolder> {

    private List<PublicTransport> PTList;
    private MyFile f;
    private SharedData s;

    public PublicTransportSpecificListAdapter(List<PublicTransport> PTList) {
        this.PTList = PTList;
    }

    @Override
    public int getItemCount() {
        return PTList.size();
    }

    @Override
    public void onBindViewHolder(final PublicTransportSpecificViewHolder holder, int position) {

        PublicTransport pt = PTList.get(position);
        holder.pt_name.setText(pt.getPt_name());
        holder.pt_route.setText(pt.getPt_route());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ottengo la posizione dell'elemento
                int pos = holder.getAdapterPosition();
                //ottengo l'elemento in posizion "pos"
                PublicTransport fav = PTList.get(pos);
                //salvo il preferito
                int addResult;
                String textToShow;

                if((addResult=f.addFavourite(fav))==0){
                    s.favList.add(fav);//aggiorno la lista dei preferiti
                    textToShow="Preferito aggiunto";
                }
                else if(addResult==-2) textToShow="ERRORE preferito gia esistente";
                else textToShow="ERRORE preferito non aggiunto";

                //lancio MapActivity
                Intent i = new Intent(v.getContext(), MapActivity.class);
                i.putExtra("snackbarContent", textToShow);
                i.putExtra("PublicTransport", (Parcelable)fav);
                i.putExtra("SharedData", s);
                ((Activity)v.getContext()).startActivity(i);
            }
        });
    }

    @Override
    public PublicTransportSpecificViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        f = new MyFile();
        s = ((Activity) viewGroup.getRootView().getContext()).getIntent().getParcelableExtra("SharedData");

        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.pt_item_card_layout, viewGroup, false);

        return new PublicTransportSpecificViewHolder(itemView);
    }

    public static class PublicTransportSpecificViewHolder extends RecyclerView.ViewHolder {
        protected TextView pt_name;
        protected TextView pt_route;

        public PublicTransportSpecificViewHolder(View v) {
            super(v);
            pt_name =  (TextView) v.findViewById(R.id.pt_name);
            pt_route = (TextView)  v.findViewById(R.id.pt_route);
        }
    }
}