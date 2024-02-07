package com.example.volleytrials;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * This page allows Tenant users to modify their profile information and preferences
 * The only way out is going back to login
 */
public class EditProfile extends AppCompatActivity {

    private EditText editName;
    private EditText editPassword;
    private EditText editEmail;
    private EditText editPrice;
    private EditText editLocation;
    private EditText editPets;
    private EditText editRoom;
    private EditText editBath;

    /**
     * Initializes objects for EditProfile and sets the OnClickListeners
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Button buttonLogIn = findViewById(R.id.buttonEditLogIn);
        buttonLogIn.setOnClickListener(v -> {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        });

        editName = findViewById(R.id.editName);
        editPassword = findViewById(R.id.editPassword);
        editEmail = findViewById(R.id.editEmail);
        editPrice = findViewById(R.id.editPrice);
        editLocation = findViewById(R.id.editLocation);
        editPets = findViewById(R.id.editPets);
        editRoom = findViewById(R.id.editRoom);
        editBath = findViewById(R.id.editBath);
        Button buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(v -> {
            String name = editName.getText().toString();
            String password = editPassword.getText().toString();
            String email = editEmail.getText().toString();
            String price = editPrice.getText().toString();
            String location = editLocation.getText().toString();
            String pets = editPets.getText().toString();
            String room = editRoom.getText().toString();
            String bath = editBath.getText().toString();
            volleyUpdateProfile(name, password, email, price, location, pets, room, bath);
        });
    }

    /**
     * Updates the current users profile with new data
     * Only attempts to update fields that were filled out
     * @param name The new username the user is trying to set
     * @param password The new password the user is trying to set
     * @param email The new email the user is trying to set
     * @param price The new preferred price the user is trying to set
     * @param location The new preferred location the user is trying to set
     * @param pets The new pet preference the user is trying to set
     * @param room The new preferred bedroom amount the user is trying to set
     * @param bath The new preferred bathroom amount the user is trying to set
     */
    private void volleyUpdateProfile(String name, String password, String email, String price, String location, String pets, String room, String bath) {
        String url = "http://coms-309-016.class.las.iastate.edu:8080/tenants/" + UserData.getInstance().getName() + "/" + UserData.getInstance().getPassword();
        HashMap<String, String> params = new HashMap<>();
        if (!name.isEmpty()) {
            params.put("userName", name);
        }
        if (!password.isEmpty()) {
            params.put("userPassword", password);
        }
        if (!email.isEmpty()) {
            params.put("userEmail", email);
        }
        if (!price.isEmpty()) {
            params.put("tenantBudget", price);
        }
        if (!location.isEmpty()) {
            params.put("tenantAddress", location);
        }
        if (!pets.isEmpty()){
        params.put("tenantPetPreference", pets);
        }
        if (!room.isEmpty()){
        params.put("tenantRoomPreference", room);
        }
        if (!bath.isEmpty()){
            params.put("tenantBathroomPreference", bath);
        }
        Log.d("Params", new JSONObject(params).toString());
        Log.d("URL", url);
        RequestQueue queue = Volley.newRequestQueue(EditProfile.this);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(params),
                response -> {
                    Toast.makeText(EditProfile.this, "Success", Toast.LENGTH_LONG).show();
                    editName.setText("");
                    editPassword.setText("");
                    editEmail.setText("");
                    editPrice.setText("");
                    editPets.setText("");
                    editLocation.setText("");
                    editRoom.setText("");
                    editBath.setText("");
                }, error -> Toast.makeText(EditProfile.this, "Error", Toast.LENGTH_LONG).show());
        queue.add(jsonRequest);
//        Log.d("End", String.valueOf(jsonRequest));
    }
}