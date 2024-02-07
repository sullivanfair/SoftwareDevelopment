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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This page is where a user logs in to their account
 * Sends the User to a different homepage depending on whether the user is a Landlord or Tenant
 */
public class Login extends AppCompatActivity{

    public EditText loginName;
    public EditText loginPassword;

    /**
     * Initializes objects in Login and sets OnClickListeners
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button signUpButton = findViewById(R.id.buttonSignUp);
        signUpButton.setOnClickListener(v -> openMainActivity());

        Button submitButton = findViewById(R.id.buttonSubmit);
        submitButton.setOnClickListener(v -> {
            loginName = findViewById(R.id.editTextName);
            loginPassword = findViewById(R.id.editTextPassword);
            if (loginName.getText().toString().isEmpty() || loginPassword.getText().toString().isEmpty()) {
                Toast.makeText(Login.this, "Enter Username and Password", Toast.LENGTH_LONG).show();
            }
            else {
                volleyRenterLogin(loginName.getText().toString(), loginPassword.getText().toString());
            }
        });
    }

    /**
     * Switches Activity from Login to Registration
     */
    public void openMainActivity() {
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
        finish();
    }

    /**
     * Attempts to Log the user in as a Tenant User
     * If it fails will switch to the Landlord Login
     * @param name The username the user is trying to login with
     * @param password The password the user is trying to login with
     */
    private void volleyRenterLogin(String name, String password) {
        String url = "http://coms-309-016.class.las.iastate.edu:8080/tenants/" + name + "/" + password + "/matches";
//        Log.d("URL", url);
        RequestQueue queue = Volley.newRequestQueue(Login.this);
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    Toast.makeText(Login.this, "Success", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Login.this, Homepage.class);
                    UserData.getInstance().setName(name);
                    UserData.getInstance().setPassword(password);
                    try {
                        UserData.getInstance().setArray(response);
                        JSONObject account = response.getJSONObject(0);
                        UserData.getInstance().setID(account.get("userId").toString());
//                        Log.d("UserData", account.get("userEmail").toString());
                        UserData.getInstance().setEmail(account.get("userEmail").toString());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    Intent serviceIntent = new Intent(this, NotificationService.class);
                    startService(serviceIntent);
                    startActivity(intent);
                    finish();
                }, error -> volleyLandlordLogin(name, password));
        queue.add(jsonRequest);
    }

    /**
     * Attempts to Log the user in as a Landlord User
     * @param name The username the user is attempting to log in with
     * @param password The password the user is attempting to log in with
     */
    private void volleyLandlordLogin(String name, String password) {
        String url = "http://coms-309-016.class.las.iastate.edu:8080/landlords/" + name + "/" + password;
//        Log.d("URL", url);
        RequestQueue queue = Volley.newRequestQueue(Login.this);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    Log.d("Response", response.toString());
                    Toast.makeText(Login.this, "Success", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Login.this, LandHomepage.class);
                    UserData.getInstance().setName(name);
                    UserData.getInstance().setPassword(password);
                    try {
                        UserData.getInstance().setID(response.get("userId").toString());
                        UserData.getInstance().setEmail(response.get("userEmail").toString());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    Intent serviceIntent = new Intent(this, NotificationService.class);
                    startService(serviceIntent);
                    volleyLandlordArray();
                    startActivity(intent);
                    finish();
                }, error -> Toast.makeText(Login.this, "Username or Password Incorrect", Toast.LENGTH_LONG).show());
        queue.add(jsonRequest);
    }

    /**
     * Sets array in Singleton class UserData to a list of listings this Landlord owns
     */
    private void volleyLandlordArray(){
        String url = "http://coms-309-016.class.las.iastate.edu:8080/listings/landlords/" + UserData.getInstance().getID();
        RequestQueue queue = Volley.newRequestQueue(Login.this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response ->{ UserData.getInstance().setArray(response);
                Log.d("Test", response.toString());},
                error -> Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show());
        queue.add(request);
    }
}
