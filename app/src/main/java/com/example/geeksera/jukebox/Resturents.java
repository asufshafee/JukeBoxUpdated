package com.example.geeksera.jukebox;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.example.geeksera.jukebox.Objects.Resturents_Object;
import com.example.geeksera.jukebox.Session.MyApplication;
import com.example.geeksera.jukebox.StaticData.Static;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Resturents extends AppCompatActivity {

    Spinner Resturents;
    Resturents_Object resturents_object;
    ProgressDialog progressDialog;
    boolean Check = false;
    int position;

    EditText Username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturents);
        Resturents = findViewById(R.id.Resturents);
        progressDialog = new ProgressDialog(com.example.geeksera.jukebox.Resturents.this);
        progressDialog.setTitle("Wait....");
        progressDialog.setCancelable(false);
        Username = findViewById(R.id.Username);


        findViewById(R.id.Done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Username.getText().toString().equals("")) {
                    Username.setError("Please Enter Your Name");
                    return;
                }

                try {
                    MyApplication myApplication = (MyApplication) getApplicationContext();
                    myApplication.setName(Username.getText().toString());
                    myApplication.setGetRestaurantsResult(resturents_object.getGetRestaurantsResult()[position]);
                    Intent intent = new Intent(Resturents.this, Home.class);
                    startActivity(intent);
                    finish();
                    myApplication.GetTheme();
                } catch (Exception e) {

                }


            }
        });


        progressDialog.show();
        GetResturents();

    }

    RequestQueue mRequestQueue;


    public void GetResturents() {


        String url = Static.ServerAddress + "GetRestaurants";
        url = url.replace(" ", "%20");


        final Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        final com.android.volley.Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();


                        try {
                            Gson gson = new Gson();
                            resturents_object = gson.fromJson(response, Resturents_Object.class);

                            ArrayList<String> spinnerrray = new ArrayList<>();
                            for (GetRestaurantsResult getRestaurantsResult : resturents_object.getGetRestaurantsResult()
                                    ) {

                                spinnerrray.add(getRestaurantsResult.getName() + " (" + getRestaurantsResult.getCity() + ")");
                            }
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                    com.example.geeksera.jukebox.Resturents.this, android.R.layout.simple_spinner_item, spinnerrray);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            Resturents.setAdapter(spinnerArrayAdapter);


                            Resturents.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    Resturents.this.position = position;

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


                        } catch (Exception a) {

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        mRequestQueue.add(stringRequest);
    }
}
