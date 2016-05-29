package classes.layout_classes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.geom.R;

import java.util.List;

import classes.PublicTransport;
//lista di oggetti PublicTransport
public class PublicTransportGenericListAdapter extends RecyclerView.Adapter<PublicTransportGenericListAdapter.ViewHolder> {

    private List<PublicTransport> pt_list;

    public PublicTransportGenericListAdapter(List<PublicTransport> pt_list){
        this.pt_list = pt_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.generic_pt_item_list_layout, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.pt_image_id.setImageResource(pt_list.get(position).getPt_image_id());
        holder.pt_type.setText(pt_list.get(position).getPt_type());
        holder.pt_info.setText(pt_list.get(position).getPt_info());
    }

    @Override
    public int getItemCount() {
        return pt_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView pt_image_id;
        public TextView pt_type;
        public TextView pt_info;

        public ViewHolder(View vi) {
            super(vi);
            pt_image_id = (ImageView) vi.findViewById(R.id.pt_image);
            pt_type = (TextView) vi.findViewById(R.id.pt_type);
            pt_info = (TextView) vi.findViewById(R.id.pt_info);
        }
    }
}