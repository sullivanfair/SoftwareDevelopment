package com.example.volleytrials;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
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

import java.util.HashMap;

public class CreateListing extends AppCompatActivity {
    private EditText lName;
    private EditText lAddr;
    private EditText lPrice;
    private EditText lAmenities;
    private EditText lPets;
    private EditText lNumBeds;
    private EditText lNumBaths;
    private String URL;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);

        Button backButton = findViewById(R.id.backCL);

        Button post = findViewById(R.id.postCL);

        lName = findViewById(R.id.et0);
        lAddr = findViewById(R.id.et1);
        lPrice = findViewById(R.id.et2);
        lPets = findViewById(R.id.et3);
        lNumBeds = findViewById(R.id.et4);
        lNumBaths = findViewById(R.id.et5);
        lAmenities = findViewById(R.id.et6);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateListing.this, LandHomepage.class);
                startActivity(intent);
                finish();
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = lName.getText().toString();
                String addr = lAddr.getText().toString();
                String priceTxt = lPrice.getText().toString();
                String pets = lPets.getText().toString();
                String bedsTxt = lNumBeds.getText().toString();
                String bathsTxt = lNumBaths.getText().toString();
                String amenities = lAmenities.getText().toString();

                int price = 0;
                int beds = 0;
                int baths = 0;
                try {
                    price = Integer.parseInt(priceTxt);
                    beds = Integer.parseInt(bedsTxt);
                    baths = Integer.parseInt(bathsTxt);
                } catch (NumberFormatException e) {

                }

                JSONObject params = new JSONObject();
                //THESE WILL HAVE TO BE CHANGED TO MATCH BACKEND
                try {
                    params.put("listingName", name);
                    params.put("listingAddress", addr);
                    params.put("listingPrice", Integer.toString(price));
                    params.put("listingStatus", "available");
                    params.put("listingAmenities", amenities);
                    params.put("listingPetPreference", pets);
                    params.put("listingBedrooms", Integer.toString(beds));
                    params.put("listingBathrooms", Integer.toString(baths));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                URL = Const.User_URL;

                JsonArrayRequest requestUser = new JsonArrayRequest(Request.Method.GET, URL, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray responseA) {
                                try{
                                    int i;
                                    JSONObject userObject = null;
                                    int userId = Integer.parseInt(UserData.getInstance().getID());

                                    for (i = 0; i < responseA.length(); i++) {
                                        JSONObject jsonObject = responseA.getJSONObject(i);

                                        // Check if the ID in the current object matches the user's ID
                                        if (jsonObject.has("userId") && jsonObject.getInt("userId") == userId) {
                                            userObject = jsonObject;
                                            break; // Break the loop since we found the matching object
                                        }
                                    }

                                    Log.d("USER: ", userObject.toString());
                                    params.put("listingOwner", userObject);
                                    Log.d("Params", params.toString());
                                    URL = Const.Listing_URL;
                                    Log.d("URL", URL);
                                    if (!(name.isEmpty() || addr.isEmpty() || priceTxt.isEmpty() || pets.isEmpty() || bedsTxt.isEmpty() || bathsTxt.isEmpty() || amenities.isEmpty())) {

                                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, params,
                                                response -> {
                                                    Toast.makeText(CreateListing.this, "Success", Toast.LENGTH_LONG).show();

                                                }, error -> {
                                            Toast.makeText(CreateListing.this, "Error", Toast.LENGTH_LONG).show();


                                        });
                                        requestQueue.add(request);

                                        Intent intent = new Intent(CreateListing.this, LandHomepage.class);
                                        startActivity(intent);
                                        finish();
                                    }

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
