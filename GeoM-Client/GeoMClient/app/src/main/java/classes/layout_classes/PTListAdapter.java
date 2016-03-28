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
//list for public transport elements
public class PTListAdapter extends ArrayAdapter<PublicTransport> {
    private Activity activity;
    private ArrayList<PublicTransport> PTList;
    private LayoutInflater inflater = null;

    public PTListAdapter(Activity activity, int textViewResourceId, ArrayList<PublicTransport> PTList) {
        super(activity, textViewResourceId, PTList);
        try {
            this.activity = activity;
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
        public ImageView PTImageId;
        public TextView PTName;
        public TextView PTDescripion;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.pt_item_list_layout, null);
                holder = new ViewHolder();

                holder.PTImageId = (ImageView) vi.findViewById(R.id.pt_image);
                holder.PTName = (TextView) vi.findViewById(R.id.pt_name);
                holder.PTDescripion = (TextView) vi.findViewById(R.id.pt_description);

                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }

            holder.PTImageId.setImageResource(PTList.get(position).getPTPhotoID());
            holder.PTName.setText(PTList.get(position).getPTType());
            holder.PTDescripion.setText(PTList.get(position).getPTDescription());


        } catch (Exception e) {
            e.printStackTrace();
        }
        return vi;
    }
}
