package com.example.geeksera.jukebox.Session;

import android.Manifest;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.example.geeksera.jukebox.Objects.GetRestaurantsResult;
import com.example.geeksera.jukebox.Objects.Request;
import com.example.geeksera.jukebox.Objects.SongsDetails;
import com.example.geeksera.jukebox.StaticData.Static;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class MyApplication extends Application {


    RequestQueue mRequestQueue;
    SongsDetails songsDetails;
    Request request;
    String DeviceID;
    String Theme;
    GetRestaurantsResult getRestaurantsResult;
    String Name;
    List<String> IDs = new LinkedList<>();
    String[] IDs1;
    SharedPreferences.Editor editor;
    SharedPreferences prefs;

    String Json = "";

    public String getJson() {
        return prefs.getString("name", "");
    }

    public void setJson(String json) {
        editor.putString("name", "json");
        editor.apply();
        editor.commit();
    }

    Gson gson = new Gson();

    @Override
    public void onCreate() {


        final Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        final com.android.volley.Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
        prefs = getSharedPreferences("Juke", MODE_PRIVATE);
        editor = getSharedPreferences("Juke", MODE_PRIVATE).edit();

        if (getJson().equals("")) {
            IDs1 = new String[0];
        } else {

            IDs1 = gson.fromJson(getJson(), String[].class);
        }

        IDs.addAll(Arrays.asList(IDs1));

        super.onCreate();
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getThemee() {
        return Theme;
    }

    public void setTheme(String theme) {
        Theme = theme;
    }

    public SongsDetails getSongsDetails() {
        return songsDetails;
    }

    public void setSongsDetails(SongsDetails songsDetails) {
        this.songsDetails = songsDetails;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
        DeviceID = DeviceID.replace(":", "");
    }


    public RequestQueue getmRequestQueue() {
        return mRequestQueue;
    }

    public void setmRequestQueue(RequestQueue mRequestQueue) {
        this.mRequestQueue = mRequestQueue;
    }


    public GetRestaurantsResult getGetRestaurantsResult() {
        return getRestaurantsResult;
    }

    public void setGetRestaurantsResult(GetRestaurantsResult getRestaurantsResult) {
        this.getRestaurantsResult = getRestaurantsResult;
    }

    public void SendRequest(String Funcation) {


        String url = Static.ServerAddress + Funcation;
        url = url.replace(" ", "%20");

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        mRequestQueue.add(stringRequest);
    }

    public void GetTheme() {


        String url = Static.ServerAddress + "selecTheme/" + getGetRestaurantsResult().getId();
        url = url.replace(" ", "%20");

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("selecThemeResult");
                            Theme = jsonArray.getJSONObject(0).getString("Name");

                        } catch (Exception a) {

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        mRequestQueue.add(stringRequest);
    }


    public void AddIds(String ID) {
        IDs.add(ID);
        IDs1 = new String[IDs.size()];
        for (int i = 0; i < IDs.size(); i++)
            IDs1[i] = IDs.get(i);
        setJson(gson.toJson(IDs1));
    }

    public List<String> getIDs() {
        return IDs;
    }

    public void setIDs(List<String> IDs) {
        this.IDs = IDs;
    }
}
