package com.example.volleytrials;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class OtherTenantDetails  extends AppCompatActivity {

    RequestQueue requestQueue;

    String URL = "";

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_tenant_details);

        Intent intent = getIntent();
        int tenantId = intent.getIntExtra("tenant_id", 13);
        Log.d("TENANT ID:", tenantId + "");

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        getDetails(tenantId);

        TextView tenantName = findViewById(R.id.tenantName);
        Button backButton = findViewById(R.id.backOTD);
        Button chatButton = findViewById(R.id.chatWithUser);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtherTenantDetails.this, Homepage.class);
                startActivity(intent);
                finish();
            }
        });

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intentA = new Intent(OtherTenantDetails.this, UserToUserChat.class);
                intentA.putExtra("tenant_id", tenantId);
                startActivity(intentA);
                finish();
            }
        });

    }
    private void getDetails(int tenantId){
        URL = Const.User_URL;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int i;
                        try{
                            JSONObject tenantObject = response.getJSONObject(0);
                            for (i = 0; i < response.length(); i ++){
                                JSONObject trialObject = response.getJSONObject(i);
                                if (trialObject.has("userId") && trialObject.getInt("userId") == tenantId){
                                    tenantObject = trialObject;
                                }
                            }

                            String name = tenantObject.optString("userName");
                            int budget = tenantObject.optInt("tenantBudget");
                            int numBed = tenantObject.optInt("tenantBedroomPreference");
                            int numBath = tenantObject.optInt("tenantBathroomPreference");
                            String location = tenantObject.optString("tenantAddress");
                            String petPref = tenantObject.optString("tenantPetPreference");
                            Log.d("DEBUG DETAILS", name);
                            Log.d("DEBUG DETAILS", budget + "");
                            Log.d("DEBUG DETAILS", numBed + "");
                            Log.d("DEBUG DETAILS", numBath + "");
                            Log.d("DEBUG DETAILS", location);


                            // Update your UI elements
                            TextView tname = findViewById(R.id.tenantName);
                            TextView tbudget = findViewById(R.id.budg);
                            TextView tnumBed = findViewById(R.id.numB);
                            TextView tnumBath = findViewById(R.id.numBaths);
                            TextView tlocation = findViewById(R.id.loc);
                            TextView tpetPref = findViewById(R.id.petP);

                            //Set Text Views
                            tname.setText(name);
                            tbudget.setText(budget + "");
                            tnumBed.setText(numBed + "");
                            tnumBath.setText(numBath + "");
                            tlocation.setText(location);
                            tpetPref.setText(petPref);

                        }catch (Exception e) {
                            Log.e("Error Tag", e.toString());
                            Log.d("Error Tag", "CONFUSION");
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error Tag", "CONFUSION");
                        Log.e("Error Tag", error.toString());
                        // Handle any errors that occur during the request.
                    }
                });
        requestQueue.add(request);
    }
}
