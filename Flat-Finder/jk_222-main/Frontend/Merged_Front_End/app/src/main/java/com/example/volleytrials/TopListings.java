package com.example.volleytrials;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 'TopListings' is an activity which allows a user to view the top 15 'recommended'
 * listings. Here, tenant users can also like and view details about a listing
 * @author Carter Parks
 */
public class TopListings extends AppCompatActivity {
    private RequestQueue requestQueue;

    private JSONArray allListings = new JSONArray();

    String URL = Const.DB_URL; //coms-309-016.class.las.iastate.edu

    /**
     * onCreate is run when the user opens the TopListings activity. Accessed from Homepage,
     * where the user has an option to 'view top listings', which navigates here
     *
     * @param savedInstanceState if this activity is being reopened after previously
     *                           being closed by the user, this Bundle contains the data
     *                           most recently saved in saveInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.listings_top);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        Button tohome = findViewById(R.id.backHome);
        EditText searchet = findViewById(R.id.searchEditText);

        /**
         * This sets an onClickListener for the "backButton" also called 'tohome'
         * When user clicks, navigates the user back to the "Homepage" activity
         */
        tohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TopListings.this, Homepage.class);
                startActivity(intent);
                finish();
            }
        });

        URL = Const.Listing_URL;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int i;
                        for (i = 0; i < response.length(); i ++ ){
                            JSONObject listingObject = null;
                            try {
                                listingObject = response.getJSONObject(i);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            if (listingObject != null) {
                                allListings.put(listingObject);
                                if (i < 15){
                                    addListingBlock(listingObject, i + 1);

                                }
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

        searchet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                // No action needed before text changes
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Filter and reorder the listings based on the search term
                filterListings(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No action needed after text changes
            }
        });
    }

    /**
     *
     * @param listingObject JSON Object representing the listing being added
     * @param place the INDEX of the listing UI element (1 indexed)
     */
    private void addListingBlock(JSONObject listingObject, int place){

        Log.d("Listing", listingObject.toString());

        String lname = listingObject.optString("listingName");
        String numLikes = listingObject.optString("listingLikes");
        int layoutId = getResources().getIdentifier("listingLayout" + place, "id", getPackageName());
        View listingLayout = findViewById(layoutId);
        TextView listingName = listingLayout.findViewById(R.id.ListingName);
        TextView likes = listingLayout.findViewById(R.id.NumLikes);
        listingName.setText(lname);
        likes.setText(numLikes);
        Button viewDetailsButton = listingLayout.findViewById(R.id.Details);
        Button commentButton = listingLayout.findViewById(R.id.Comment);
        ToggleButton likeButton = listingLayout.findViewById(R.id.Like);

        likeButton.setTextOn("v");
        likeButton.setTextOff("^");
        likeButton.setChecked(false);

        viewDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    detailsClick(listingObject.getInt("listingId"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentClick(place);
            }
        });

        likeButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    like(place);
                }
                else{
                    unlike(place);
                }
            }
        });
    }

    private void filterListings(String searchTerm) {
        List<JSONObject> filteredListings = new ArrayList<>();

        for (int i = 0; i < allListings.length(); i++) {
            try {
                JSONObject listing = allListings.getJSONObject(i);
                String listingName = listing.optString("listingName", "").toLowerCase();

                if (listingName.contains(searchTerm.toLowerCase())) {
                    filteredListings.add(listing);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < allListings.length(); i++) {
            try {
                JSONObject listing = allListings.getJSONObject(i);
                String listingName = listing.optString("listingName", "").toLowerCase();
                String listingAddr = listing.optString("listingAddress", "").toLowerCase();
                if (!filteredListings.contains(listing) && !listingName.contains(searchTerm.toLowerCase()) && listingAddr.contains(searchTerm.toLowerCase())) {
                    filteredListings.add(listing);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < allListings.length(); i++) {
            try {
                JSONObject listing = allListings.getJSONObject(i);
                String listingAddr = listing.optString("listingAddress", "").toLowerCase();
                String listingName = listing.optString("listingName", "").toLowerCase();

                if (!filteredListings.contains(listing) && !listingName.contains(searchTerm.toLowerCase()) && !listingAddr.contains(searchTerm.toLowerCase())) {
                    filteredListings.add(listing);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < filteredListings.size() && i < 15; i++) {
            JSONObject listingObject = filteredListings.get(i);
            addListingBlock(listingObject, i + 1);
        }

    }

    /**
     * detailsClick() is a function which navigates the user to 'ShowDetails' activity.
     * This sends the listingId (which is an index) as intent.putExtra("listing_id")
     * @param listingId the index of the desired listing in the array which is returned by the GET request
     */
    private void detailsClick(int listingId){
        Intent intent = new Intent(TopListings.this, ShowDetails.class);
        intent.putExtra("listing_id", listingId);
        startActivity(intent);
    }

    /**
     * commentClick() is a function which navigates the user to 'Comment' activity.
     * This sends the listingId (which is an index) as intent.putExtra("listing_id")
     * @param listingId the index of the desired listing in the array which is returned by the GET request
     */
    private void commentClick(int listingId){
        Intent intent = new Intent(TopListings.this, Comment.class);
        intent.putExtra("listing_id", listingId);
        startActivity(intent);
    }

    /**
     * like() is a function which takes listingId, uses a PUT request nested in a GET request.
     * The GET request obtains the listings Id, which is used in the URL for the PUT request.
     * The PUT request calls a function in the backend which increments the number of likes associated with a listing.
     * @param listingId actually the index of the desired listing in the array which is updated by the PUT request
     */
    private void like(int listingId){
        this.URL = Const.Listing_URL;
        final TopListings outerClass = this;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            JSONObject listingObject = response.getJSONObject(listingId - 1);
                            outerClass.URL += "/";
                            outerClass.URL += listingObject.optString("listingId");
                            outerClass.URL += "/incrementLikes";
                            JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.PUT, outerClass.URL, null,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try{
                                                int layoutId = getResources().getIdentifier("listingLayout" + listingId, "id", getPackageName());
                                                String numLikes = response.optString("listingLikes");
                                                View listingLayout = outerClass.findViewById(layoutId);
                                                TextView likes = listingLayout.findViewById(R.id.NumLikes);
                                                likes.setText(numLikes);
                                            }catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            // Handle the successful response from the backend.
                                            // You can process the response data here.
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            // Handle any errors that occur during the request.
                                        }
                                    });

                            requestQueue.add(request2);
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
        requestQueue.add(request);
    }

    /**
     * unlike() is a function which takes listingId, uses a PUT request nested in a GET request.
     * The GET request obtains the listings Id, which is used in the URL for the PUT request.
     * The PUT request calls a function in the backend which decrements the number of likes associated with a listing.
     * @param listingId actually the index of the desired listing in the array which is updated by the PUT request
     */
    private void unlike(int listingId){
        this.URL = Const.Listing_URL;
        final TopListings outerClass = this;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            JSONObject listingObject = response.getJSONObject(listingId - 1);
                            outerClass.URL += "/";
                            outerClass.URL += listingObject.optString("listingId");
                            outerClass.URL += "/decrementLikes";
                            JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.PUT, outerClass.URL, null,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try{
                                                int layoutId = getResources().getIdentifier("listingLayout" + listingId, "id", getPackageName());
                                                String numLikes = response.optString("listingLikes");
                                                View listingLayout = outerClass.findViewById(layoutId);
                                                TextView likes = listingLayout.findViewById(R.id.NumLikes);
                                                likes.setText(numLikes);
                                            }catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            // Handle the successful response from the backend.
                                            // You can process the response data here.
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d("MyTagD", "TEST");
                                            // Handle any errors that occur during the request.
                                        }
                                    });

                            requestQueue.add(request2);
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("MyTagY", "PROBLEM");
                        // Handle any errors that occur during the request.
                    }
                });
        requestQueue.add(request);
    }
}
