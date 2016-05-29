package classes.layout_classes;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.geom.R;

import java.util.List;

import classes.PublicTransport;
import classes.SharedData;

/**
 * Created by Mattia on 29/05/2016.
 */
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
    public void onBindViewHolder(ViewHolder holder,int position){
        List<PublicTransport> fav_list = s.favList;

        holder.pt_image.setImageResource(fav_list.get(position).getPt_image_id());
        holder.pt_type.setText(fav_list.get(position).getPt_type());
        holder.pt_name.setText(fav_list.get(position).getPt_name());
        holder.pt_route.setText(fav_list.get(position).getPt_route());
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

        public ViewHolder(View vi) {
            super(vi);
            pt_image = (ImageView) vi.findViewById(R.id.pt_image);
            pt_type = (TextView) vi.findViewById(R.id.pt_type);
            pt_name = (TextView) vi.findViewById(R.id.pt_name);
            pt_route = (TextView) vi.findViewById(R.id.pt_route);
        }
    }
}