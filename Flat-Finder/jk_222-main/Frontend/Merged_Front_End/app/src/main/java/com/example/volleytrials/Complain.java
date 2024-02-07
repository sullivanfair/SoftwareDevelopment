package com.example.volleytrials;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Complain extends AppCompatActivity {

    String URL;
    private RequestQueue requestQueue;
    private JSONObject thisListing;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);

        Intent intent = getIntent();
        int listingId = intent.getIntExtra("listingId", 0);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        Button backButton = findViewById(R.id.backComplaint);
        Button sendButton = findViewById(R.id.sendComplaint);
        EditText complaintBody = findViewById(R.id.complaintBody);

        URL = Const.Listing_URL;
        URL += "/" + listingId;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        thisListing = response;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // Handle any errors that occur during the request.
                    }
                });
        requestQueue.add(request);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Complain.this, TopListings.class);
                startActivity(intent);
                finish();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                URL = Const.Tenants_URL;
                JSONObject requestBody = new JSONObject();
                URL += "/" + UserData.getInstance().getName() + "/" + UserData.getInstance().getPassword() + "/matches";
                Log.d("USER URL: ", URL);
                JsonArrayRequest requestUser = new JsonArrayRequest(Request.Method.GET, URL, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray responseA) {
                                try{
                                    JSONObject userObject = responseA.getJSONObject(0);
                                    Log.d("USER: ", userObject.toString());
                                    String complaintTxt = complaintBody.getText().toString();
                                    requestBody.put("content", complaintTxt);
                                    requestBody.put("receivingLandlord", thisListing.getJSONObject("listingOwner"));
                                    requestBody.put("sendingTenant", userObject);
                                    requestBody.put("status", "Pending");
                                    URL = Const.Complain_URL;
                                    Log.d("listing URL: ", URL);

                                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, requestBody,
                                            response -> {
                                                Toast.makeText(Complain.this, "Success", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(Complain.this, TopListings.class);
                                                startActivity(intent);
                                                finish();
                                            }, error -> {
                                        Toast.makeText(Complain.this, "Error", Toast.LENGTH_LONG).show();


                                    });
                                    requestQueue.add(request);

                                }catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                // Handle any errors that occur during the request.
                            }
                        });
                requestQueue.add(requestUser);
            }
        });
    }
}
