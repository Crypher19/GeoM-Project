package classes.layout_classes;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mattia.geom.MapActivity;
import com.example.mattia.geom.R;

import java.util.List;

import classes.Favourite;
import classes.MyFile;
import classes.Train;

public class TrainListAdapter extends RecyclerView.Adapter<TrainListAdapter.TrainViewHolder> {

    private List<Train> trainList;
    private MyFile f;

    public TrainListAdapter(List<Train> trainList) {
        this.trainList = trainList;
    }

    @Override
    public int getItemCount() {
        return trainList.size();
    }

    @Override
    public void onBindViewHolder(final TrainViewHolder trainViewHolder, int i) {
        Train t = trainList.get(i);
        trainViewHolder.trainName.setText(t.getPTName());
        trainViewHolder.trainCity.setText(t.getPTCity());

        trainViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ottengo la posizione dell'elemento
                int pos = trainViewHolder.getAdapterPosition();
                //ottengo l'elemento in posizion "pos"
                Train t = trainList.get(pos);
                //trasformo l'elemento in un preferito
                Favourite fav = new Favourite(t);
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
    public TrainViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        f=new MyFile();

        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.bus_train_card_layout, viewGroup, false);

        return new TrainViewHolder(itemView);
    }

    public static class TrainViewHolder extends RecyclerView.ViewHolder {
        protected TextView trainName;
        protected TextView trainCity;

        public TrainViewHolder(View v) {
            super(v);
            trainName =  (TextView) v.findViewById(R.id.bus_train_name);
            trainCity = (TextView)  v.findViewById(R.id.bus_train_city);
        }
    }
}