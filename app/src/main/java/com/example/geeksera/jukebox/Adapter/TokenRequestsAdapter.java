package com.example.geeksera.jukebox.Adapter;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.geeksera.jukebox.Objects.GetRequestsResult;
import com.example.geeksera.jukebox.Objects.History;
import com.example.geeksera.jukebox.Objects.Request;
import com.example.geeksera.jukebox.R;

import java.util.List;

public class TokenRequestsAdapter extends RecyclerView.Adapter<TokenRequestsAdapter.MyViewHolder> {

    private List<GetRequestsResult> RequestDetails;
    Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Username, TableNO, Status, Tokens, TTokens, Resturent;

        public MyViewHolder(View view) {
            super(view);
            Username = (TextView) view.findViewById(R.id.UserName);
            TableNO = (TextView) view.findViewById(R.id.TableNumber);
            Status = (TextView) view.findViewById(R.id.Status);
            Tokens = (TextView) view.findViewById(R.id.Tokens);
            TTokens = view.findViewById(R.id.TotalTokens);
            Resturent = view.findViewById(R.id.ResturentName);

        }
    }


    public TokenRequestsAdapter(List<GetRequestsResult> RequestLIst, Activity context) {
        this.RequestDetails = RequestLIst;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_requests, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final GetRequestsResult request = RequestDetails.get(position);

        holder.Username.setText(request.getPurchasedby());
        holder.TableNO.setText(request.getTableNumber());
        holder.Tokens.setText(request.getTcount());
        holder.Resturent.setText(request.getRestaurantInfo().getName());
        if (request.getTokenstatus().equals("1")) {
            holder.Status.setText("Approved");
        } else {
            holder.Status.setText("Not Approved");
            holder.Status.setTextColor(Color.RED);
        }

        int count = Integer.parseInt(request.getTcount());

        int Consume = 0;
        if (request.getHistory() != null)
            for (History history : request.getHistory()) {
                Consume = Consume + Integer.parseInt(history.getConsumedTokens());
            }
        holder.Tokens.setText(String.valueOf(count));
        holder.TTokens.setText(String.valueOf(count + Consume));


    }

    @Override
    public int getItemCount() {
        return RequestDetails.size();
    }
}