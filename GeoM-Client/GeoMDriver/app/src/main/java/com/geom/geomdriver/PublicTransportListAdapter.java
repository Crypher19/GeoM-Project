package com.geom.geomdriver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        //apro SecondActivity cliccando sull'elemento
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublicTransport pt = pt_list.get(holder.getAdapterPosition());
                Intent i = new Intent(v.getContext(), SecondActivity.class);
                Bundle b = new Bundle();

                b.putParcelable("pt", pt);
                i.putExtra("bundle", b);
                v.getRootView().getContext().startActivity(i);
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
}
