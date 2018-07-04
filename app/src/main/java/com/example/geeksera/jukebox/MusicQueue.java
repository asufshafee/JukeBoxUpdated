package com.example.geeksera.jukebox;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.geeksera.jukebox.Adapter.SongLibraryAdapter;
import com.example.geeksera.jukebox.Adapter.SongQueueAdapter;
import com.example.geeksera.jukebox.Objects.NowPlayingObject;
import com.example.geeksera.jukebox.Objects.Request;
import com.example.geeksera.jukebox.Objects.SongsDetails;
import com.example.geeksera.jukebox.Session.MyApplication;
import com.example.geeksera.jukebox.StaticData.Static;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicQueue extends Fragment {


    private List<SongsDetails> SongDetailsLIst = new ArrayList<>();
    private RecyclerView recyclerView;
    private SongQueueAdapter mAdapter;

    View MainView;

    public MusicQueue() {
        // Required empty public constructor
    }

    SwipeRefreshLayout swipeRefreshLayout;

    TextView SongName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_music_queue, container, false);
        MainView = view;
        SongName = view.findViewById(R.id.NowPlaying);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayoutQueue);
        mAdapter = new SongQueueAdapter(SongDetailsLIst, getActivity(),swipeRefreshLayout);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SendRequestToServer();
                NowPlaying();
            }
        });
        SendRequestToServer();
        NowPlaying();
        return view;
    }


    public void SendRequestToServer() {
        SongDetailsLIst.clear();
        RequestQueue mRequestQueue;
        final Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        final com.android.volley.Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
        MyApplication myApplication = (MyApplication) getActivity().getApplicationContext();

        String url = Static.ServerAddress + "GetQueue/" + myApplication.getGetRestaurantsResult().getId();

        url = url.replace(" ", "%20");

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            String json = null;
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("GetQueueResult");
                            Gson gson = new Gson();


                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    json = jsonArray.getString(i);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                SongsDetails songsDetails = new SongsDetails();
                                songsDetails = gson.fromJson(json, SongsDetails.class);
                                SongDetailsLIst.add(songsDetails);
                            }


                            Collections.sort(SongDetailsLIst, new Comparator<SongsDetails>() {
                                @Override
                                public int compare(SongsDetails o1, SongsDetails o2) {
                                    return Double.compare(Double.parseDouble(o1.getPeriority()), Double.parseDouble(o2.getPeriority()));
                                }
                            });
                            mAdapter.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                        swipeRefreshLayout.setRefreshing(false);

                    }
                });

        mRequestQueue.add(stringRequest);
    }


    public void Search(String Query) {

        List<SongsDetails> Search = new LinkedList<>();
        for (SongsDetails songsDetails : SongDetailsLIst) {
            if (songsDetails.getTitle().toLowerCase().contains(Query.toLowerCase()))
                Search.add(songsDetails);
        }

        recyclerView = (RecyclerView) MainView.findViewById(R.id.recycler_view);
        mAdapter = new SongQueueAdapter(Search, getActivity(),swipeRefreshLayout);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


    }


    public void Default() {
        recyclerView = (RecyclerView) MainView.findViewById(R.id.recycler_view);

        mAdapter = new SongQueueAdapter(SongDetailsLIst, getActivity(),swipeRefreshLayout);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }


    public void NowPlaying() {
        RequestQueue mRequestQueue;
        final Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        final com.android.volley.Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
        MyApplication myApplication = (MyApplication) getActivity().getApplicationContext();

        String url = Static.ServerAddress + "NowPlaying/" + myApplication.getGetRestaurantsResult().getId();

        url = url.replace(" ", "%20");

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Gson gson = new Gson();
                        NowPlayingObject nowPlayingObject = gson.fromJson(response, NowPlayingObject.class);

                        try {
                            if (nowPlayingObject != null)
                                SongName.setText("Now Playing: "+nowPlayingObject.getNowPlayingResult().getTitle().toString());
                        } catch (Exception Ex) {

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();

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
