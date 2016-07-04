package classes.layout_classes;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.geom.HomeActivity;
import com.geom.R;

import java.util.List;

import classes.Connectivity;
import classes.CoordThread;
import classes.PublicTransport;
import classes.SharedData;

public class FavoritesListAdapter extends RecyclerView.Adapter<FavoritesListAdapter.ViewHolder>{

    private SharedData s;

    public FavoritesListAdapter (SharedData s){
        this.s=s;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View itemView= LayoutInflater.from(parent.getContext())
        .inflate(R.layout.fav_item_list_layout, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position){
        List<PublicTransport> fav_list = s.favList;

        holder.pt_image.setImageResource(fav_list.get(position).getPt_image_id());
        holder.pt_type.setText(fav_list.get(position).getPt_type());
        holder.pt_name.setText(fav_list.get(position).getPt_name());
        holder.pt_route.setText(fav_list.get(position).getPt_route());

        //ottengo l'elemento attuale
        final PublicTransport pt = fav_list.get(holder.getAdapterPosition());

        //apro mapActivity cliccando sull'elemento
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.setEnabled(false); // imposto la label del mezzo NON cliccabile
                //controllo la connessione ad internet
                if (Connectivity.isConnected((v.getRootView().getContext()))) {
                    s.goToFavouritesActivity = true;
                    CoordThread ct = new CoordThread(s, v, pt);
                    CoordThread.ricezioneCoord = true;
                    ct.start();
                } else{//se non è connesso ad internet
                    showAlertDialog(v.getContext(),
                            v.getContext().getString(R.string.internet_error_title),
                            v.getContext().getString(R.string.internet_error_message));
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(),
                        R.style.AppCompatAlertDialogStyleLight);
                builder.setTitle("Eliminare questo preferito?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        String snackbarContent;

                        if(removeListItem(holder.getAdapterPosition())){
                            snackbarContent = "Preferito eliminato";
                        } else snackbarContent = "ERRORE, preferito non eliminato";

                        if(s.favList.size() == 0){//ultimo preferito rimasto
                            Intent i = new Intent(v.getContext(), HomeActivity.class);
                            Bundle b = new Bundle();
                            b.putString("snackbarContent", snackbarContent);
                            b.putParcelable("SharedData", s);
                            i.putExtra("bundle", b);
                            v.getContext().startActivity(i);
                        } else{//non è l'ultimo preferito
                            Snackbar.make(((Activity) v.getContext()).findViewById(
                                    R.id.activity_favourites), snackbarContent,
                                    Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setNegativeButton("ANNULLA", null);
                builder.show();
                return false;
            }
        });

        //mezzo disponibile - non disponibile (pallino verde - rosso)
        if (fav_list.get(position).isPt_enabled())
            holder.pt_enabled.setImageResource(R.drawable.ic_enabled_green);//mezzo disponibile
        else
            holder.pt_enabled.setImageResource(R.drawable.ic_disabled_red);//mezzo non disponibile*/
    }

    @Override
    public int getItemCount(){
        return s.favList.size();
    }

    public void removeItem(int position){
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, s.favList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView pt_image;
        public TextView pt_type;
        public TextView pt_name;
        public TextView pt_route;
        public ImageView pt_enabled;

        public ViewHolder(View vi) {
            super(vi);
            pt_image = (ImageView) vi.findViewById(R.id.pt_image);
            pt_type = (TextView) vi.findViewById(R.id.pt_type);
            pt_name = (TextView) vi.findViewById(R.id.pt_name);
            pt_route = (TextView) vi.findViewById(R.id.pt_route);
            pt_enabled = (ImageView) vi.findViewById(R.id.pt_enabled);
        }
    }

    public void showAlertDialog(Context context, String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context,
                R.style.AppCompatAlertDialogStyleLight);
        if(title != null && !title.isEmpty())
            builder.setTitle(Html.fromHtml("<b>"+ title +"</b>"));
        if(message != null && !message.isEmpty())
            builder.setMessage(message);

        builder.setPositiveButton(Html.fromHtml("<b>"+ "OK" +"</b>"), null);
        builder.show();
    }

    public boolean removeListItem(int position){
        PublicTransport pt = s.favList.get(position);

        if(pt != null) {
            if (s.removeFav(pt)) {
                //se va tutto bene aggiorno la lista da visualizzare
                removeItem(position);
                return true;
            }
        }
        return false;
    }
}