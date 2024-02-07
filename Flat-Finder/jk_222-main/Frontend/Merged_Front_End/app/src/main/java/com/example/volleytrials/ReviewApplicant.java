package com.example.volleytrials;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ReviewApplicant extends AppCompatActivity {
    JSONObject appObject;
    String URL = "";
    RequestQueue requestQueue;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_applicant);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        Intent intent = getIntent();
        String applicationObject = intent.getStringExtra("applicationObject");

        try {
            appObject = new JSONObject(applicationObject);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Log.d("TEST TAG",appObject.toString());
        Button backButton = findViewById(R.id.backRA);
        Button acceptButton = findViewById(R.id.acceptApplication);
        Button rejectButton = findViewById(R.id.rejectApplication);
        TextView title = findViewById(R.id.titleRA);
        TextView budget = findViewById(R.id.Tbudg);
        TextView bedPref = findViewById(R.id.TnumB);
        TextView bathPref = findViewById(R.id.TnumBaths);
        TextView location = findViewById(R.id.Tloc);
        TextView petPref = findViewById(R.id.TpetP);

        try {
            title.setText(appObject.getJSONObject("applyingTenant").getString("userName") + " has applied to " + appObject.getJSONObject("listing").getString("listingName"));
            budget.setText(appObject.getJSONObject("applyingTenant").getString("tenantBudget"));
            bedPref.setText(appObject.getJSONObject("applyingTenant").getString("tenantBedroomPreference"));
            bathPref.setText(appObject.getJSONObject("applyingTenant").getString("tenantBathroomPreference"));
            location.setText(appObject.getJSONObject("applyingTenant").getString("tenantAddress"));
            petPref.setText(appObject.getJSONObject("applyingTenant").getString("tenantPetPreference"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReviewApplicant.this, ViewApplicants.class);
                startActivity(intent);
                finish();
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    appObject.put("applicationStatus","Accepted");
                    Log.d("NEW Application:", appObject.toString());
                    URL = Const.App_URL;
                    URL += "/" + appObject.getInt("applicationId");
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, URL, appObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Toast.makeText(ReviewApplicant.this, "Success", Toast.LENGTH_LONG).show();
                                    Log.d("RESPONSE", response.toString());
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(ReviewApplicant.this, "Failure", Toast.LENGTH_LONG).show();
                                    error.printStackTrace();
                                    Log.e("ERROR", error.toString());
                                }
                            });
                    requestQueue.add(request);
                } catch (JSONException e) {
                    Log.e("Another error", e.toString());
                    Toast.makeText(ReviewApplicant.this, "Failure", Toast.LENGTH_LONG).show();
                    throw new RuntimeException(e);
                }
            }
        });

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    appObject.put("applicationStatus","Rejected");
                    Log.d("NEW Application:", appObject.toString());
                    URL = Const.App_URL;
                    URL += "/" + appObject.getInt("applicationId");
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, URL, appObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Toast.makeText(ReviewApplicant.this, "Success", Toast.LENGTH_LONG).show();
                                    Log.d("RESPONSE", response.toString());
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(ReviewApplicant.this, "Failure", Toast.LENGTH_LONG).show();
                                    error.printStackTrace();
                                    Log.e("ERROR", error.toString());
                                }
                            });
                    requestQueue.add(request);
                } catch (JSONException e) {
                    Log.e("Another error", e.toString());
                    Toast.makeText(ReviewApplicant.this, "Failure", Toast.LENGTH_LONG).show();
                    throw new RuntimeException(e);
                }
            }
        });

    }
}
