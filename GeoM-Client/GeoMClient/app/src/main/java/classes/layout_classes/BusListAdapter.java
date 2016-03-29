package classes.layout_classes;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mattia.geom.MapActivity;
import com.example.mattia.geom.R;

import classes.Bus;
import classes.Favourite;
import classes.MyFile;

import java.util.List;

public class BusListAdapter extends RecyclerView.Adapter<BusListAdapter.BusViewHolder> {

    private List<Bus> busList;
    private MyFile f;

    public BusListAdapter(List<Bus> busList) {
        this.busList = busList;
    }

    @Override
    public int getItemCount() {
        return busList.size();
    }

    @Override
    public void onBindViewHolder(final BusViewHolder busViewHolder, int i) {
        Bus b = busList.get(i);
        busViewHolder.busName.setText(b.getPTName());
        busViewHolder.busCity.setText(b.getPTCity());

        busViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ottengo la posizione dell'elemento
                int pos = busViewHolder.getAdapterPosition();
                //ottengo l'elemento in posizion "pos"
                Bus b = busList.get(pos);
                //trasformo l'elemento in un preferito
                Favourite fav = new Favourite(b);
                //salvo il preferito
                int addResult;
                String textToShow;
                if((addResult=f.addFavourite(fav))==0) textToShow="Preferito aggiunto";
                else if(addResult==-2) textToShow="ERRORE preferito gia esistente";
                else textToShow="ERRORE preferito non aggiunto";

                //lancio MapActivity
                Intent i = new Intent(v.getContext(), MapActivity.class);
                //pulisco la lista delle activity in coda
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("snackbarContent", textToShow);
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public BusViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        f = new MyFile();

        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.bus_train_card_layout, viewGroup, false);

        return new BusViewHolder(itemView);
    }

    public static class BusViewHolder extends RecyclerView.ViewHolder {
        protected TextView busName;
        protected TextView busCity;

        public BusViewHolder(View v) {
            super(v);
            busName =  (TextView) v.findViewById(R.id.bus_train_name);
            busCity = (TextView)  v.findViewById(R.id.bus_train_city);
        }
    }
}
