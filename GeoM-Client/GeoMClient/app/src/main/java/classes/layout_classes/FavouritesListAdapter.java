package classes.layout_classes;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mattia.geom.R;

import java.util.ArrayList;

import classes.PublicTransport;

public class FavouritesListAdapter extends ArrayAdapter<PublicTransport> {
    //private Activity activity;
    private ArrayList<PublicTransport> favList;
    private LayoutInflater inflater = null;

    public FavouritesListAdapter(Activity activity, int textViewResourceId, ArrayList<PublicTransport> favList) {
        super(activity, textViewResourceId, favList);
        try {
            //this.activity = activity;
            this.favList = favList;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCount() {
        return favList.size();
    }

    public PublicTransport getItem(PublicTransport position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public ImageView pt_image_id;
        public TextView pt_type;
        public TextView pt_name;
        public TextView pt_route;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.pt_specific_item_list_layout, null);
                holder = new ViewHolder();

                holder.pt_image_id = (ImageView) vi.findViewById(R.id.pt_image);
                holder.pt_type = (TextView) vi.findViewById(R.id.pt_type);
                holder.pt_name = (TextView) vi.findViewById(R.id.pt_name);
                holder.pt_route = (TextView) vi.findViewById(R.id.pt_route);

                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }

            holder.pt_image_id.setImageResource(favList.get(position).getPt_image_id());
            holder.pt_type.setText(favList.get(position).getPt_type());
            holder.pt_name.setText(favList.get(position).getPt_name());
            holder.pt_route.setText(favList.get(position).getPt_route());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return vi;
    }
}