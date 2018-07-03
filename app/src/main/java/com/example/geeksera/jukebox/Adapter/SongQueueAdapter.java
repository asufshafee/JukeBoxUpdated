package com.example.geeksera.jukebox.Adapter;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.example.geeksera.jukebox.Dedicate_Song;
import com.example.geeksera.jukebox.Get_Token;
import com.example.geeksera.jukebox.Objects.CurrentDedicationsResult;
import com.example.geeksera.jukebox.Objects.DedicationObject;
import com.example.geeksera.jukebox.Objects.GetSongDedicationsResult;
import com.example.geeksera.jukebox.Objects.New;
import com.example.geeksera.jukebox.Objects.SongsDetails;
import com.example.geeksera.jukebox.R;
import com.example.geeksera.jukebox.RequestSong;
import com.example.geeksera.jukebox.Session.MyApplication;
import com.example.geeksera.jukebox.StaticData.Static;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SongQueueAdapter extends RecyclerView.Adapter<SongQueueAdapter.MyViewHolder> {

    private List<SongsDetails> SongDetails;
    Context mContext;
    private GetSongDedicationsResult[] RequestDetailsLIst;
    private List<CurrentDedicationsResult> RequestDetails;

    private RecyclerView recyclerView;
    private DadicationsAdapter mAdapter;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView SongName, Singer, Albem, Duration, Theme;

        LinearLayout Click;

        public MyViewHolder(View view) {
            super(view);
            SongName = (TextView) view.findViewById(R.id.SongName);
            Singer = (TextView) view.findViewById(R.id.Singer);
            Albem = (TextView) view.findViewById(R.id.Albem);

            Duration = (TextView) view.findViewById(R.id.Duration);
            Theme = (TextView) view.findViewById(R.id.Theme);

            Click = (LinearLayout) view.findViewById(R.id.Click);

        }
    }


    public SongQueueAdapter(List<SongsDetails> SongLIst, Activity context) {
        this.SongDetails = SongLIst;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final SongsDetails mSongDetail = SongDetails.get(position);

        holder.SongName.setText(mSongDetail.getTitle());
        holder.Singer.setText(mSongDetail.getSinger());
        holder.Theme.setText(mSongDetail.getPlaystatus());
        holder.Albem.setText(mSongDetail.getAlbumName());
        holder.Duration.setText(mSongDetail.getDuration());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Datications(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return SongDetails.size();
    }


    public void Datications(final int Posotion) {
        RequestQueue mRequestQueue;
        final Cache cache = new DiskBasedCache(mContext.getCacheDir(), 1024 * 1024); // 1MB cap
        final com.android.volley.Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
        MyApplication myApplication = (MyApplication) mContext.getApplicationContext();

        String url = Static.ServerAddress + "GetSongDedications/" + SongDetails.get(Posotion).getS_id() + "/" + myApplication.getGetRestaurantsResult().getId();

        url = url.replace(" ", "%20");

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        New dedicationObject = gson.fromJson(response, New.class);

                        RequestDetailsLIst = dedicationObject.getGetSongDedicationsResult();

                        // Created a new Dialog
                        final Dialog dialog = new Dialog(mContext);
                        // inflate the layout
                        dialog.setContentView(R.layout.dedicaton_dialog);
                        RequestDetails = new ArrayList<>();

                        // Set the dialog text -- this is better done in the XML
                        for (GetSongDedicationsResult getSongDedicationsResult : RequestDetailsLIst) {
                            CurrentDedicationsResult currentDedicationsResult = new CurrentDedicationsResult();
                            currentDedicationsResult.setDedicatedBy(getSongDedicationsResult.getDedicatedBy());
                            currentDedicationsResult.setDedicatedto(getSongDedicationsResult.getDedicatedto());
                            currentDedicationsResult.setDedicationmessage(getSongDedicationsResult.getDedicationmessage());
                            RequestDetails.add(currentDedicationsResult);
                        }

                        recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_view);
                        mAdapter = new DadicationsAdapter(RequestDetails, mContext);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext.getApplicationContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(mAdapter);

                        Button Dedicate;
                        Dedicate = (Button) dialog.findViewById(R.id.back);
                        Dedicate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialog.hide();

                            }
                        });


                        dialog.show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        mRequestQueue.add(stringRequest);
    }
}