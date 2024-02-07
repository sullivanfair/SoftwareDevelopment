package com.example.volleytrials;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
//import com.squareup.picasso.Picasso;

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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

//import java.awt.image.BufferedImage;
//import javax.imageio.ImageIO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public class ShowDetails extends AppCompatActivity{
    RequestQueue requestQueue;
    JSONObject listingObject;

    String URL = "";
    String ImgPath = "";

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_details);

        Intent intent = getIntent();
        int listingId = intent.getIntExtra("listing_id", 0);

        Log.d("listingId", listingId + "");
        TextView listingName = findViewById(R.id.DetListing);
        listingName.setText(Integer.toString(listingId));

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        getDetails(listingId);

        getImage();

        Button backButton = findViewById(R.id.backDet);
        ToggleButton likeButton = findViewById(R.id.likeDet);
        Button applyToListing = findViewById(R.id.applyBtn);
        Button complain = findViewById(R.id.complaintBtn);

        likeButton.setTextOn("v");
        likeButton.setTextOff("^");
        likeButton.setChecked(false);

        URL = Const.App_URL;

        /**
         * This sets an onClickListener for the "backButton"
         * When user clicks, n  avigates the user back to the "Top Listings" activity
         */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowDetails.this, TopListings.class);
                startActivity(intent);
                finish();
            }
        });

        likeButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    like(listingId);
                }
                else{
                    unlike(listingId);
                }
            }
        });

        applyToListing.setOnClickListener(new View.OnClickListener(){
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
                                    requestBody.put("applyingTenant", userObject);
                                    URL = Const.Listing_URL;
                                    Log.d("listing URL: ", URL);

                                    JsonArrayRequest requestListing = new JsonArrayRequest(Request.Method.GET, URL, null,
                                            new Response.Listener<JSONArray>() {
                                                @Override
                                                public void onResponse(JSONArray responseB) {
                                                    try{
                                                        JSONObject thisListing = responseB.getJSONObject(0);
                                                        JSONObject listingObject = responseB.getJSONObject(0);
                                                        int i;
                                                        for(i = 0; i < responseB.length(); i++){
                                                            listingObject = responseB.getJSONObject(i);
                                                            if (listingObject.has("listingId") && listingObject.getInt("listingId") == listingId){
                                                                thisListing = listingObject;
                                                                break;
                                                            }
                                                        }
                                                        Log.d("listing: ", thisListing.toString());


                                                        URL = Const.App_URL;
                                                        requestBody.put("listing", thisListing);
                                                        requestBody.put("applicationStatus", "Pending");
                                                        requestBody.put("applicationNotes", "None");
                                                        Log.d("requestURL", URL);
                                                        Log.d("requestBody", requestBody.toString());
                                                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, requestBody,
                                                                response -> {
                                                                    Toast.makeText(ShowDetails.this, "Success", Toast.LENGTH_LONG).show();
                                                                }, error -> {
                                                            Toast.makeText(ShowDetails.this, "Error", Toast.LENGTH_LONG).show();

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
                                    requestQueue.add(requestListing);

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

        complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(ShowDetails.this, Complain.class);
                intent.putExtra("listingId", listingId);
                startActivity(intent);
                finish();
            }
        });
    }

    private void like(int listingId){
        this.URL = Const.Listing_URL;
        final ShowDetails outerClass = this;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            outerClass.URL += "/" + listingId;
                            outerClass.URL += "/incrementLikes";
                            JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.PUT, outerClass.URL, null,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try{
                                                String numLikes = response.optString("listingLikes");
                                                TextView likes = findViewById(R.id.DetNumLikes);
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

    private void unlike(int listingId){
        this.URL = Const.Listing_URL;
        final ShowDetails outerClass = this;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            outerClass.URL += "/" + listingId;
                            outerClass.URL += "/decrementLikes";
                            JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.PUT, outerClass.URL, null,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try{
                                                String numLikes = response.optString("listingLikes");
                                                TextView likes = findViewById(R.id.DetNumLikes);
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


    private void getDetails(int listingId){
        URL = Const.Listing_URL;


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int i;
                        try{
                            listingObject = response.getJSONObject(0);

                            for(i = 0; i < response.length(); i ++){
                                if (response.getJSONObject(i).optInt("listingId") == listingId){
                                    listingObject = response.getJSONObject(i);

                                    URL = Const.App_URL;

                                    JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null,
                                            new Response.Listener<JSONArray>() {
                                                @Override
                                                public void onResponse(JSONArray response) {
                                                    int i;
                                                    for (i = 0; i < response.length(); i ++ ){
                                                        JSONObject applicationObject = null;
                                                        try {
                                                            applicationObject = response.getJSONObject(i);
                                                        } catch (JSONException e) {
                                                            throw new RuntimeException(e);
                                                        }

                                                        Log.d("application Object:", applicationObject.toString());
                                                        try {
                                                            Button complain = findViewById(R.id.complaintBtn);
                                                            if (applicationObject.getJSONObject("listing").optInt("listingId") == listingObject.optInt("listingId") && applicationObject.getJSONObject("applyingTenant").getInt("userId") == Integer.parseInt(UserData.getInstance().getID()) && applicationObject.getString("applicationStatus").equals("Accepted")) {
                                                                complain.setVisibility(View.VISIBLE);
                                                                Log.d("complaint Available:", "SUCCESS");
                                                                break;
                                                            }
                                                            else{
                                                                complain.setVisibility(View.GONE);
                                                                Log.d("complaint Available:", "FAILURE");
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
                                    break;
                                }
                            }
                            String name = listingObject.optString("listingName");
                            String numLikes = listingObject.optString("listingLikes");
                            String price = listingObject.optString("listingPrice");
                            String address = listingObject.optString("listingAddress");
                            String amenities = listingObject.optString("listingAmenities");
                            String comments = listingObject.optString("listingComments");
                            String beds = listingObject.optString("listingBedrooms");
                            String baths = listingObject.optString("listingBathrooms");
                            String pets = listingObject.optString("listingPetPreference");

                            // Update your UI elements
                            TextView listingName = findViewById(R.id.DetListing);
                            TextView likes = findViewById(R.id.DetNumLikes);
                            TextView cost = findViewById(R.id.DetCost);
                            TextView addr = findViewById(R.id.DetAddress);
                            TextView ammen = findViewById(R.id.DetAmenities);
                            TextView comm = findViewById(R.id.DetTop);
                            TextView bd = findViewById(R.id.DetBeds);
                            TextView bt = findViewById(R.id.DetBaths);
                            TextView pts = findViewById(R.id.DetPets);

                            listingName.setText(name);
                            likes.setText(numLikes);
                            cost.setText(price);
                            addr.setText(address);
                            ammen.setText(amenities);
                            comm.setText(comments);
                            bd.setText(beds);
                            bt.setText(baths);
                            pts.setText(pets);
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

    private void getImage(){
        Log.d("MyTagA", "IMG FUNCTION");
        URL = "http://coms-309-016.class.las.iastate.edu:8080/images/apartment1.jpg";
        Log.d("ImageURL: ", URL);
        this.ImgPath = "";
        final ShowDetails outerClass = this;

        StringRequest request2 = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            Log.d("MyTagB", "RESPONSE A: " + response);
                            String imgURL = "http://coms-309-016.class.las.iastate.edu:8080" + response;
                            Log.d("MyTagB", "RESPONSE B: " + imgURL);
                            ImageView listingImage = (ImageView) findViewById(R.id.imageViewListing);
//                            Picasso.get().setLoggingEnabled(true);
//                            Picasso.get().load(imgURL).into(listingImage);
                            //Picasso.get().load().Fi
                            //byte[] imageByteArray = response.getBytes();
                            //Log.d("MyTagC", "RESPONSE B: " + imageByteArray);

                            //ByteArrayInputStream bis = new ByteArrayInputStream(imageByteArray);

                            //Log.d("MYTAG", bis.toString());

                            //BufferedImage bImage2 = ImageIO.read(bis);

                            //ImageIO.write(bImage2, "jpg", new File("output.jpg") );

                            /*
                            try{
                                Log.d("MyTagA", "RESPONSE A: ");
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
                                //Log.d("MyTagX", "RESPONSE 1: " + bitmap.toString());
                                ImageView listingImage = (ImageView) findViewById(R.id.imageViewListing);
                                Log.d("MyTagY", "RESPONSE 2: " + "Image View Found");
                                listingImage.setImageBitmap(bitmap);
                                Log.d("MyTagZ", "RESPONSE 3: " + "Image view set");
                            }
                            catch (Exception e){
                                e.printStackTrace();
                                Log.e("Error", "CODE" + e.toString());
                            }
                             */

                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("MyTagB", "ERROR: " + error.getMessage());

                        // Handle any errors that occur during the request.
                    }
                });
        requestQueue.add(request2);
    }


}
