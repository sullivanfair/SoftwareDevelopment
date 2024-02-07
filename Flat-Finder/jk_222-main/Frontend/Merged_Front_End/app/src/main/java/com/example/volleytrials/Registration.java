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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

/**
 * This page is where the user can create a new account
 * It has buttons moving to and from the Login Activity
 */
public class Registration extends AppCompatActivity {

    private String RentOrLand;
    private EditText editName;
    private EditText editPassword;
    private EditText editPrice;
    private EditText editLocation;
    private EditText editPets;
    private EditText editRoom;
    private EditText editBath;
    private EditText editEmail;

    /**
     * Creates the Registration Activity by initializing all the objects and setting all onClickListeners
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ScrollView scrollView = findViewById(R.id.RenterScroll);
        scrollView.setVisibility(View.INVISIBLE);

        Button buttonLogIn = findViewById(R.id.buttonLogIn);
        buttonLogIn.setOnClickListener(v -> openLogIn());

        Button buttonRenter = findViewById(R.id.buttonRenter);
        buttonRenter.setOnClickListener(v -> {
            scrollView.setVisibility(View.VISIBLE);
            RentOrLand = "Renter";
        });

        Button buttonLandlord = findViewById(R.id.buttonLandlord);
        buttonLandlord.setOnClickListener(v -> {
            scrollView.setVisibility(View.INVISIBLE);
            RentOrLand = "Landlord";
        });
        editName = findViewById(R.id.editTextName);
        editPassword = findViewById(R.id.editTextPassword);
        editPrice = findViewById(R.id.editTextPrice);
        editLocation = findViewById(R.id.editTextLocation);
        editPets = findViewById(R.id.editTextPets);
        editRoom = findViewById(R.id.editTextRoom);
        editBath = findViewById(R.id.editTextBathroom);
        editEmail = findViewById(R.id.editTextEmail);
        Button buttonCreate = findViewById(R.id.buttonCreate);
        buttonCreate.setOnClickListener(v -> {
            String name = editName.getText().toString();
            String password = editPassword.getText().toString();
            String price = editPrice.getText().toString();
            String location = editLocation.getText().toString();
            String pets = editPets.getText().toString();
            String room = editRoom.getText().toString();
            String bath = editBath.getText().toString();
            String email = editEmail.getText().toString();
            if (Objects.equals(RentOrLand, "Renter")) {
                if (!name.isEmpty() || !password.isEmpty()) {
                    volleyCreateRenter(name, password, price, location, pets, room, bath, email);
                }
            }
            if (Objects.equals(RentOrLand, "Landlord")) {
                if (!name.isEmpty() || !password.isEmpty()) {
                    volleyCreateLandlord(name, password, email);
                }
            }
        });
    }
    /**
    Switches Activity from Registration to LogIn
     */
    public void openLogIn() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
    /**
    Creates a new Renter User in the server
    @param name The username of the new account
    @param password The password of the new account
    @param price The preferred price the new account would pay
    @param location The area where the new account is looking for apartments
    @param pets Whether the new account wants pets or not in the apartment
    @param room The preferred number of rooms the new account wants in the apartment
    @param bath The preferred number of bathrooms the new account wants in the apartment
    @param email The email of the new account
     */
    private void volleyCreateRenter(String name, String password, String price, String location, String pets, String room, String bath, String email) {
        String url = "http://coms-309-016.class.las.iastate.edu:8080/tenants";
        HashMap<String, String> params = new HashMap<>();
        params.put("userName", name);
        params.put("userPassword", password);
        params.put("tenantBudget", price);
        params.put("tenantAddress", location);
        params.put("tenantPetPreference", pets);
        params.put("tenantBedroomPreference", room);
        params.put("tenantBathroomPreference", bath);
        params.put("userEmail", email);
//        Log.d("Params", new JSONObject(params).toString());
//        Log.d("URL", url);
        RequestQueue queue = Volley.newRequestQueue(Registration.this);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                response -> {
                    Toast.makeText(Registration.this, "Success", Toast.LENGTH_LONG).show();
                    editName.setText("");
                    editPassword.setText("");
                    RentOrLand = "";
                    editPrice.setText("");
                    editPets.setText("");
                    editLocation.setText("");
                    editRoom.setText("");
                    editBath.setText("");
                    editEmail.setText("");
                }, error -> Toast.makeText(Registration.this, "Error", Toast.LENGTH_LONG).show());
        queue.add(jsonRequest);
//        Log.d("End", String.valueOf(jsonRequest));
    }
    /**
     * Creates a new Landlord User in the server
     * @param name The username of the new account
     * @param password The password of the new account
     * @param email THe email of the new account
     */
    private void volleyCreateLandlord(String name, String password, String email) {
        String url = "http://coms-309-016.class.las.iastate.edu:8080/landlords/landlords";
        HashMap<String, String> params = new HashMap<>();
        params.put("userName", name);
        params.put("userPassword", password);
        params.put("userEmail", email);
//        Log.d("Params", new JSONObject(params).toString());
//        Log.d("URL", url);
        RequestQueue queue = Volley.newRequestQueue(Registration.this);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                response -> {
                    Toast.makeText(Registration.this, "Success", Toast.LENGTH_LONG).show();
                    editName.setText("");
                    editPassword.setText("");
                    RentOrLand = "";
                    editEmail.setText("");
                }, error -> Toast.makeText(Registration.this, "Error", Toast.LENGTH_LONG).show());
        queue.add(jsonRequest);
//        Log.d("End", String.valueOf(jsonRequest));
    }
}
