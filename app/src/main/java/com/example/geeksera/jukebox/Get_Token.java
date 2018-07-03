package com.example.geeksera.jukebox;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.geeksera.jukebox.Session.MyApplication;
import com.example.geeksera.jukebox.StaticData.Static;

public class Get_Token extends AppCompatActivity {

    EditText Username, NoOfTokens, TableNumber;
    String DeviceID;
    RadioButton Silver, Gold, Platinum;
    MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get__token);
        getSupportActionBar().setTitle("Get Tokken");
        Username = (EditText) findViewById(R.id.Username);
        NoOfTokens = (EditText) findViewById(R.id.NoOFTokens);
        TableNumber = (EditText) findViewById(R.id.TableNumber);

        Silver = findViewById(R.id.Silver);
        Gold = findViewById(R.id.Gold);
        Platinum = findViewById(R.id.Platinum);


        myApplication = (MyApplication) getApplicationContext();
        Username.setText(myApplication.getName());


        findViewById(R.id.Submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Username.getText().toString().equals("")) {
                    Username.setError("please Fill This Filed");
                    return;
                }
                if (NoOfTokens.getText().toString().equals("")) {
                    NoOfTokens.setError("please Fill This Filed");
                    return;
                }
                if (TableNumber.getText().toString().equals("")) {
                    TableNumber.setError("please Fill This Filed");
                    return;
                }

                SendRequestToServer();

            }
        });
        findViewById(R.id.Close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    public void SendRequestToServer() {


        String Type = "1";
        if (Silver.isChecked()) {
            Type = "1";
        }
        if (Gold.isChecked()) {
            Type = "2";
        }
        if (Platinum.isChecked()) {
            Type = "2";
        }


        String Funcation = "GetTokens/" + Username.getText().toString() + "/" + TableNumber.getText().toString() + "/" + NoOfTokens.getText().toString() + "/" +
                "/" + myApplication.getDeviceID() + "/" + myApplication.getGetRestaurantsResult().getId() + "/" + Type;
        ;
        myApplication.SendRequest(Funcation);
        finish();

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
