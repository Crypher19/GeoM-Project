package classes.layout_classes;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.geom.R;

import java.util.ArrayList;

import classes.PublicTransport;
//lista di oggetti PublicTransport
public class PublicTransportGenericListAdapter extends ArrayAdapter<PublicTransport> {
    //private Activity activity;
    private ArrayList<PublicTransport> PTList;
    private LayoutInflater inflater = null;

    public PublicTransportGenericListAdapter(Activity activity, int textViewResourceId, ArrayList<PublicTransport> PTList) {
        super(activity, textViewResourceId, PTList);
        try {
            //this.activity = activity;
            this.PTList = PTList;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCount() {
        return PTList.size();
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
        public TextView pt_info;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.pt_generic_item_list_layout, null);
                holder = new ViewHolder();

                holder.pt_image_id = (ImageView) vi.findViewById(R.id.pt_image);
                holder.pt_type = (TextView) vi.findViewById(R.id.pt_type);
                holder.pt_info = (TextView) vi.findViewById(R.id.pt_info);

                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }

            holder.pt_image_id.setImageResource(PTList.get(position).getPt_image_id());
            holder.pt_type.setText(PTList.get(position).getPt_type());
            holder.pt_info.setText(PTList.get(position).getPt_info());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return vi;
    }
}

