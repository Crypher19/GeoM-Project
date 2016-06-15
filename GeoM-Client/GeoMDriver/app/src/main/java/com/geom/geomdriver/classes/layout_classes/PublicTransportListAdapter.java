package com.geom.geomdriver.classes.layout_classes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geom.geomdriver.CoordActivity;
import com.geom.geomdriver.R;
import com.geom.geomdriver.classes.Connectivity;
import com.geom.geomdriver.classes.PublicTransport;

import java.util.List;

public class PublicTransportListAdapter extends RecyclerView.Adapter<PublicTransportListAdapter.ViewHolder> {
    private List<PublicTransport> pt_list;

    public PublicTransportListAdapter(List<PublicTransport> pt_list){
        this.pt_list = pt_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pt_item_list_layout, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.pt_name.setText(pt_list.get(position).getPt_name());
        holder.pt_route.setText(pt_list.get(position).getPt_route());

        //apro CoordActivity cliccando sull'elemento
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //client connesso ad internet
                if(Connectivity.isConnected(v.getContext())) {
                    PublicTransport pt = pt_list.get(holder.getAdapterPosition());
                    Intent i = new Intent(v.getContext(), CoordActivity.class);
                    Bundle b = new Bundle();

                    b.putParcelable("pt", pt);
                    i.putExtra("bundle", b);
                    v.getRootView().getContext().startActivity(i);
                    ((Activity) v.getRootView().getContext()).finish();
                } else{//client non connesso
                    showAlertDialog(v,
                            v.getContext().getString(R.string.internet_error_title),
                            v.getContext().getString(R.string.internet_error_message));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pt_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView pt_name;
        public TextView pt_route;

        public ViewHolder(View vi) {
            super(vi);
            pt_name = (TextView) vi.findViewById(R.id.pt_name);
            pt_route = (TextView) vi.findViewById(R.id.pt_route);
        }
    }

    public void showAlertDialog(View v, String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(),
                R.style.AppCompatAlertDialogStyleLight);
        if(title != null && !title.isEmpty())
            builder.setTitle(Html.fromHtml("<b>" + title + "<b>"));
        if(message != null && !message.isEmpty())
            builder.setMessage(message);

        builder.setPositiveButton(Html.fromHtml("<b>" + v.getContext().getString(R.string.ok_string) + "<b>"), null);
        builder.show();
    }
}
