package com.example.volleytrials;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class TenantComplaints extends AppCompatActivity {
    private RequestQueue requestQueue;

    String URL;
    JSONObject complaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_complaints);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        Button backButton = findViewById(R.id.backTC);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TenantComplaints.this, Homepage.class);
                startActivity(intent);
                finish();
            }
        });

        URL = Const.Complain_URL;
        URL += "/tenants/" + UserData.getInstance().getID();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int i;
                        LinearLayout complaintDetailsLayout = findViewById(R.id.complaintDetailsLayout);
                        for (i = 0; i < response.length(); i++){
                            try {
                                complaint = response.getJSONObject(i);

                                View itemView = getLayoutInflater().inflate(R.layout.complaint, null);

                                TextView body = itemView.findViewById(R.id.complaintText);
                                Button resolved = itemView.findViewById(R.id.resolve);
                                Button urgent = itemView.findViewById(R.id.urgent);

                                body.setText("Complaint " + (i + 1) + ": " + complaint.getString("content"));

                                resolved.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        URL = Const.Complain_URL;
                                        try {
                                            URL += "/" + complaint.getInt("complaintId");
                                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, URL, null,
                                                    new Response.Listener<JSONObject>() {
                                                        @Override
                                                        public void onResponse(JSONObject response) {

                                                        }
                                                    },
                                                    new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {

                                                        }
                                                    });
                                            requestQueue.add(request);
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                });

                                urgent.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        URL = Const.Complain_URL;
                                        try {
                                            URL += "/" + complaint.getInt("complaintId") + "/status/Urgent";
                                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, URL, null,
                                                    new Response.Listener<JSONObject>() {
                                                        @Override
                                                        public void onResponse(JSONObject response) {
                                                            Toast.makeText(TenantComplaints.this, "Success", Toast.LENGTH_LONG).show();
                                                            Log.d("RESPONSE", response.toString());
                                                        }
                                                    },
                                                    new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            Toast.makeText(TenantComplaints.this, "Failure", Toast.LENGTH_LONG).show();
                                                            error.printStackTrace();
                                                            Log.e("ERROR", error.toString());
                                                        }
                                                    });
                                            requestQueue.add(request);
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                });
                                complaintDetailsLayout.addView(itemView);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            Log.d("complaint",complaint.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR", error.toString());
                        // Handle any errors that occur during the request.
                    }
                });
        requestQueue.add(request);
    }
}
