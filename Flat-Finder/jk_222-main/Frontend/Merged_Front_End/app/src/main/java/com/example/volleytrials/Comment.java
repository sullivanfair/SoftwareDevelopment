package com.example.volleytrials;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

import java.util.HashMap;
import java.util.Map;

public class Comment extends AppCompatActivity{
    String URL = "";

    String oldComment = "";
    String postComment = "";
    private RequestQueue requestQueue;

    protected void onCreate(Bundle savedInstanceState) {
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);

        Intent intent = getIntent();
        int listingIdx = intent.getIntExtra("listing_id", 0);

        getDetails(listingIdx);

        TextView listingName = findViewById(R.id.ComListing);
        listingName.setText(Integer.toString(listingIdx));

        Button backButton = findViewById(R.id.backCom);
        Button postButton = findViewById(R.id.postCom);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Comment.this, TopListings.class);
                startActivity(intent);
                finish();
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //On Click, set userComment to the string in text field
                EditText commentEditText = findViewById(R.id.commentBody);
                String userComment = commentEditText.getText().toString();
                //JSONObject userComment = "";
                URL = Const.Listing_URL;
                //Gets listing ID and the old comment string
                JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, URL, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    Log.d("MyTag", "WORKING A");
                                    JSONObject listingObject = response.getJSONObject(listingIdx - 1);
                                    oldComment = listingObject.optString("listingComments");
                                    postComment = oldComment + " | " + userComment;
                                    URL += "/";
                                    URL += listingObject.optString("listingId");
                                    URL += "/listingComments";
                                    Log.d("MyTag", "URL: " + URL);
                                    Log.d("MyTag", "Comments: " + postComment);

                                    StringRequest request = new StringRequest(Request.Method.PUT, URL,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    Log.d("MyTag", "PUT WORKING");
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Log.d("MyTag", "ERROR MESSAGE: " + error.getMessage());
                                                }
                                            }){
                                        @Override
                                        public byte[] getBody(){
                                            return postComment.getBytes();
                                        }
                                        @Override
                                        public String getBodyContentType() {
                                            return "application/json"; // Change content type if needed.
                                        }
                                    };
                                    requestQueue.add(request);


                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("MyTag", "FIRST ERROR RESPONSE");
                                // Handle an error response here.
                            }
                        });
                requestQueue.add(request2);

                //After sending the request, switch intent back to recommended listings
                Intent intent = new Intent(Comment.this, TopListings.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getDetails(int listingId){
        URL = Const.Listing_URL;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            JSONObject listingObject = response.getJSONObject(listingId - 1);
                            String lname = listingObject.optString("listingName");
                            String numLikes = listingObject.optString("listingLikes");


                            // Update your UI elements
                            TextView listName = findViewById(R.id.ComListing);
                            TextView likes = findViewById(R.id.setNumLikes);
                            listName.setText(lname);
                            likes.setText(numLikes);

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
}

