package classes.layout_classes;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mattia.geom.MapActivity;
import com.example.mattia.geom.R;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import classes.Favourite;
import classes.MyFile;
import classes.SharedData;
import classes.Train;

public class TrainListAdapter extends RecyclerView.Adapter<TrainListAdapter.TrainViewHolder> {
    private List<Train> trainList;
    private MyFile f;
    private SharedData s;

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
                Train train = trainList.get(pos);
                //trasformo l'elemento in un preferito
                Favourite fav = new Favourite(train);

                //salvo il preferito
                int addResult;
                String textToShow;

                if((addResult=f.addFavourite(fav))==0){
                    s.addItemInFavouritesList(fav);//aggiorno la lista dei preferiti
                    textToShow="Preferito aggiunto";
                }
                else if(addResult==-2) textToShow="ERRORE preferito gia esistente";
                else textToShow="ERRORE preferito non aggiunto";

                //lancio MapActivity
                Intent i = new Intent(v.getContext(), MapActivity.class);
                i.putExtra("snackbarContent", textToShow);
                i.putExtra("train", (Parcelable)train);
                v.getContext().startActivity(i);
                //evito di ritornare a ChooseTrainActivity
                ((Activity) v.getContext()).finish();
            }
        });
    }

    @Override
    public TrainViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        f = new MyFile();
        s = ((Activity) viewGroup.getRootView().getContext()).getIntent().getParcelableExtra("SharedData");

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