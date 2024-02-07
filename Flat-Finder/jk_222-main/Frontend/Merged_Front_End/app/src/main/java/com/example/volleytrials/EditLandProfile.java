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
 * This page allows Landlord Users to change their profile information
 */
public class EditLandProfile extends AppCompatActivity {

    private EditText editName;
    private EditText editPassword;
    private EditText editEmail;

    /**
     * Initializes objects and sets OnClickListeners for EditLandProfile
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_land_profile);

        Button buttonLogIn = findViewById(R.id.buttonEditLogIn);
        buttonLogIn.setOnClickListener(v -> {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        });

        editName = findViewById(R.id.editName);
        editPassword = findViewById(R.id.editPassword);
        editEmail = findViewById(R.id.editEmail);
        Button buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(v -> {
            String name = editName.getText().toString();
            String password = editPassword.getText().toString();
            String email = editEmail.getText().toString();
            volleyUpdateProfile(name, password, email);
        });
    }

    /**
     * Updates the current users profile with new data
     * @param name The new username the user is trying to set
     * @param password The new password the user is trying to set
     * @param email The new email the user is trying to set
     */
    private void volleyUpdateProfile(String name, String password, String email) {
        String url = "http://coms-309-016.class.las.iastate.edu:8080/users/" + UserData.getInstance().getName() + "/userName";
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
        Log.d("Params", new JSONObject(params).toString());
        Log.d("URL", url);
        RequestQueue queue = Volley.newRequestQueue(EditLandProfile.this);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(params),
                response -> {
                    Toast.makeText(EditLandProfile.this, "Success", Toast.LENGTH_LONG).show();
                    editName.setText("");
                    editPassword.setText("");
                    editEmail.setText("");
                }, error -> Toast.makeText(EditLandProfile.this, "Error", Toast.LENGTH_LONG).show());
        queue.add(jsonRequest);
//        Log.d("End", String.valueOf(jsonRequest));
    }
}