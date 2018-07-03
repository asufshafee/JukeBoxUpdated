package com.example.geeksera.jukebox.Adapter;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.geeksera.jukebox.Objects.CurrentDedicationsResult;
import com.example.geeksera.jukebox.Objects.GetRequestsResult;
import com.example.geeksera.jukebox.Objects.History;
import com.example.geeksera.jukebox.R;

import java.util.List;

public class DadicationsAdapter extends RecyclerView.Adapter<DadicationsAdapter.MyViewHolder> {

    private List<CurrentDedicationsResult> RequestDetails;
    Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Message, To, From;

        public MyViewHolder(View view) {
            super(view);
            Message = (TextView) view.findViewById(R.id.Message);
            To = (TextView) view.findViewById(R.id.To);
            From = (TextView) view.findViewById(R.id.From);

        }
    }


    public DadicationsAdapter(List<CurrentDedicationsResult> RequestLIst, Context context) {
        this.RequestDetails = RequestLIst;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dadications, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final CurrentDedicationsResult request = RequestDetails.get(position);

        holder.From.setText(request.getDedicatedBy());
        holder.To.setText(request.getDedicatedto());
        holder.Message.setText(request.getDedicationmessage());


    }

    @Override
    public int getItemCount() {
        return RequestDetails.size();
    }
}