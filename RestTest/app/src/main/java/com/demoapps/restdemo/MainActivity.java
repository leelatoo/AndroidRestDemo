/*
 * Copyright (c) 2019. Leeladevi Balasundaram, leelabalasundaram@gmail.com
 */

package com.demoapps.restdemo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    // Rides list
    private String[] _rides = {"Harry Potter and the Escape from Gringotts","The Simpsons Ride","Shrek 4-D","Despicable Me Minion Mayhem"};
    // List of starting points
    private String[] _startingPointsList =  {"Gate", "Harry Potter and the Escape from Gringotts","The Simpsons Ride","Shrek 4-D","Despicable Me Minion Mayhem"};
    // Initial Url
    private String url = "https://integration.cloud.tibcoapps.com/uaut3vv6rqj3ustftgudnhdc6jjdsd2b/Resource/v1/trip/optimize";
    // Initial cusomter id
    private String _customerId = "90200124234";


    private String _startingPoint = _startingPointsList[0];
    private boolean[] _selectedRides;
    private String _selectedRidesDisplayText = _rides[0];
    private ProgressDialog _loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Rest Demo");
        toolbar.setVisibility(View.GONE);
        setSupportActionBar(toolbar);

        SetQueryBtnAction();
        SetRidesPickerAction();
        SetStartingPointPickerAction();
        SetLoader();
        _selectedRides = new boolean[_rides.length];

        ((TextView)findViewById(R.id.txtPreferredRides)).setText(_selectedRidesDisplayText);
        ((TextView)findViewById(R.id.txtStartingPoint)).setText(_startingPoint);
        ((EditText)findViewById(R.id.editTextCustomerId)).setText(_customerId);
        ((EditText)findViewById(R.id.txtUrl)).setText(url, TextView.BufferType.EDITABLE);
    }

    private void SetLoader()
    {
        _loader = new ProgressDialog(MainActivity.this);
        _loader.setMessage("Fetching results..");
        _loader.setIndeterminate(true);
        _loader.setCancelable(false);
    }

    private void SetQueryBtnAction()
    {
        final Button button = findViewById(R.id.btnFetchDetails);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                _loader.show();
                JSONObject jsonBody = null;
                try {
                    jsonBody = new JSONObject(GetJsonBody());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                url = ((EditText)findViewById(R.id.txtUrl)).getText().toString();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, url, jsonBody,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        LaunchResonseActivity(response.toString());
                                    }},
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        DisplayErrorDialog(error.getMessage());
                                    }
                                });

                RequestQueue myRQ = Volley.newRequestQueue(getApplicationContext());
                myRQ.add(jsonObjectRequest);

            }
        });
    }

    private void SetRidesPickerAction()
    {
        Button mButton = (Button) findViewById(R.id.RidesPicker);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                mBuilder.setTitle("Choose preferred rides");
                mBuilder.setMultiChoiceItems(_rides, _selectedRides, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        _selectedRides[which] = isChecked;
                    }
                });

                mBuilder.setCancelable(false);


                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        _selectedRidesDisplayText = "";
                        for (int i = 0; i<_selectedRides.length; i++){
                            boolean checked = _selectedRides[i];
                            if (checked) {
                                if (_selectedRidesDisplayText.length() > 0 )
                                    _selectedRidesDisplayText += ", ";
                                _selectedRidesDisplayText += _rides[i];
                            }
                        }
                        final TextView txtPreferredRides = findViewById(R.id.txtPreferredRides);
                        txtPreferredRides.setText(_selectedRidesDisplayText);
                    }
                });

                mBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
    }

    private void SetStartingPointPickerAction()
    {
        Button mButton = (Button) findViewById(R.id.StartingPointPicker);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                mBuilder.setTitle("Choose starting point");
                mBuilder.setSingleChoiceItems(_startingPointsList, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        _startingPoint = _startingPointsList[i];
                    }
                });
                mBuilder.setCancelable(false);

                mBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final TextView txtStartingPoint = findViewById(R.id.txtStartingPoint);
                        txtStartingPoint.setText(_startingPoint);
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
    }

    private String GetJsonBody()
    {
        CustomerDetails newCustomer = new CustomerDetails();
        newCustomer.setcustomer_id(((EditText)findViewById(R.id.editTextCustomerId)).getText().toString());
        newCustomer.setride_preferences(_selectedRidesDisplayText);
        newCustomer.setstarting_point(_startingPoint);

        Gson gson = new Gson();
        String jsonStr = gson.toJson(newCustomer);
        return  jsonStr;
    }

    private void LaunchResonseActivity(String jsonResponse)
    {
        _loader.dismiss();
        Gson gson = new Gson();
        RideDetails rideDetails = gson.fromJson(jsonResponse, RideDetails.class);
        Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
        intent.putExtra("RideDetails", rideDetails);
        startActivity(intent);
    }

    private void DisplayErrorDialog(String message)
    {
        _loader.dismiss();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle("Error!!");
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
