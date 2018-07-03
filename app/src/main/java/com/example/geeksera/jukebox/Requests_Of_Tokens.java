package com.example.geeksera.jukebox;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.example.geeksera.jukebox.Adapter.SongQueueAdapter;
import com.example.geeksera.jukebox.Adapter.TokenRequestsAdapter;
import com.example.geeksera.jukebox.Objects.GetRequestsResult;
import com.example.geeksera.jukebox.Objects.Request;
import com.example.geeksera.jukebox.Objects.Requests_Object;
import com.example.geeksera.jukebox.Objects.SongsDetails;
import com.example.geeksera.jukebox.Session.MyApplication;
import com.example.geeksera.jukebox.StaticData.Static;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Requests_Of_Tokens extends Fragment {


    private List<GetRequestsResult> RequestDetailsLIst = new ArrayList<>();
    private RecyclerView recyclerView;
    private TokenRequestsAdapter mAdapter;

    MyApplication myApplication;


    public Requests_Of_Tokens() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_requests__of__tokens, container, false);

        myApplication = (MyApplication) getActivity().getApplicationContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new TokenRequestsAdapter(RequestDetailsLIst, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        SendRequestToServer();
        return view;
    }


    public void SendRequestToServer() {

        RequestQueue mRequestQueue;
        final Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        final com.android.volley.Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();

        String url = Static.ServerAddress + "GetRequests/" + myApplication.getDeviceID();
        url = url.replace(" ", "%20");

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        String json = null;
                        Gson gson = new Gson();
                        Requests_Object requests_object = gson.fromJson(response, Requests_Object.class);
                        for (GetRequestsResult getRequestsResult:requests_object.getGetRequestsResult()
                             ) {
                            RequestDetailsLIst.add(getRequestsResult);
                        }


                        mAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        mRequestQueue.add(stringRequest);
    }

}
