package com.example.volleytrials;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This page is the homepage for Landlord users. It has many buttons to navigate to other activities.
 */
public class LandHomepage extends AppCompatActivity {
    /**
     * Initializes objects and sets OnClickListeners for the LandHomepage Activity
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_homepage);

        Button toLogInButton = findViewById(R.id.buttonLogOut);
        toLogInButton.setOnClickListener(v -> {
            Intent intent = new Intent(LandHomepage.this, Login.class);
            startActivity(intent);
            finish();
        });

        Button toEdit = findViewById(R.id.buttonToEdit);
        toEdit.setOnClickListener(v -> {
            Intent intent = new Intent(LandHomepage.this, EditLandProfile.class);
            intent.putExtra("name", UserData.getInstance().getName());
            intent.putExtra("password", UserData.getInstance().getPassword());
            startActivity(intent);
            finish();
        });

        Button toPostListing = findViewById(R.id.toCreateListing);
        toPostListing.setOnClickListener(v -> {
            Intent intent = new Intent(LandHomepage.this, CreateListing.class);
            startActivity(intent);
            finish();
        });

        Button toListings = findViewById(R.id.toListings);
        toListings.setOnClickListener(v -> {
            Intent intent = new Intent(LandHomepage.this, ListingsPage.class);
            startActivity(intent);
            finish();
        });

        Button toNotifications = findViewById(R.id.toNotifications);
        toNotifications.setText("Notifications" + UserData.getInstance().getNotifications().size());
        toNotifications.setOnClickListener(v -> {
            Intent intent = new Intent(LandHomepage.this, Notifications.class);
            intent.putExtra("userType", "tenant");
            startActivity(intent);
            finish();
        });

        Button toSearch = findViewById(R.id.toSearch);
        toSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandHomepage.this, UserSearch.class);
                intent.putExtra("userType", "landlord");
                startActivity(intent);
                finish();
            }
        });
        Button toApplicants = findViewById(R.id.toApplications);
        toApplicants.setOnClickListener(v -> {
            Intent intent = new Intent(LandHomepage.this, ViewApplicants.class);
            startActivity(intent);
            finish();
        });

        Button toComplaints = findViewById(R.id.toComplaints);
        toComplaints.setOnClickListener(v -> {
            Intent intent = new Intent(LandHomepage.this, LLComplaints.class);
            startActivity(intent);
            finish();
        });

        Button toSendNotification = findViewById(R.id.toSendNotification);
        toSendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandHomepage.this, SendNotification.class);
                startActivity(intent);
                finish();
            }
        });
    }
}