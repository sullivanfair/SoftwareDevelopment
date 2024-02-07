package com.example.volleytrials;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewApplicants extends AppCompatActivity {
    private RequestQueue requestQueue;
    private JSONArray userApplicants = new JSONArray();
    String URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicants);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        Button tohome = findViewById(R.id.backToHome);

        tohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewApplicants.this, LandHomepage.class);
                startActivity(intent);
                finish();
            }
        });

        URL = Const.App_URL;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int i;
                        int iter = 0;
                        for (i = 0; i < response.length(); i ++ ){
                            JSONObject applicationObject = null;
                            try {
                                applicationObject = response.getJSONObject(i);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                            Log.d("application Object:", applicationObject.toString());
                            try {
                                if (applicationObject.getJSONObject("listing").getJSONObject("listingOwner").getInt("userId") == Integer.parseInt(UserData.getInstance().getID())) {
                                    userApplicants.put(applicationObject);
                                    Log.d("APP:", applicationObject.toString());
                                    addApplicationBlock(applicationObject, iter + 1);
                                    iter += 1;
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occur during the request.
                    }
                });
        requestQueue.add(request);
    }

    private void addApplicationBlock(JSONObject applicationObject, int place) throws JSONException {
        Log.d("Applicant:", applicationObject.toString());
        String aname = applicationObject.getJSONObject("applyingTenant").optString("userName");
        String lname = applicationObject.getJSONObject("listing").optString("listingName");
        int layoutId = getResources().getIdentifier("applicantLayout" + place, "id", getPackageName());
        View applicationLayout = findViewById(layoutId);
        TextView applicantName = applicationLayout.findViewById(R.id.ApplicantName);
        TextView listingName = applicationLayout.findViewById(R.id.appliedListing);
        applicantName.setText(aname);
        listingName.setText(lname);

        Button viewDetailsButton = applicationLayout.findViewById(R.id.AppDetails);

        viewDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(ViewApplicants.this, ReviewApplicant.class);
               intent.putExtra("applicationObject", applicationObject.toString());
               startActivity(intent);
            }
        });
    }
}
