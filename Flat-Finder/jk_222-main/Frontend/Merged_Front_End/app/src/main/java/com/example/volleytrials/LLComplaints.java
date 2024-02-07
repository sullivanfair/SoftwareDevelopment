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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LLComplaints extends AppCompatActivity {
    private String URL;
    private RequestQueue requestQueue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_complaints);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        Button backButton = findViewById(R.id.backLLC);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LLComplaints.this, LandHomepage.class);
                startActivity(intent);
                finish();
            }
        });
        URL = Const.Complain_URL;
        URL += "/landlords/" + UserData.getInstance().getID();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int i;
                        JSONObject complaint;

                        for (i = 0; i < response.length(); i++){
                            int layoutId = getResources().getIdentifier("Complaint" + (i + 1), "id", getPackageName());
                            TextView content = findViewById(layoutId);
                            try {
                                complaint = response.getJSONObject(i);
                                content.setText((i + 1) + ": " + complaint.getJSONObject("sendingTenant").getString("userName") + "; " + complaint.getString("content") + " (" + complaint.getString("status") + ")");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
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
