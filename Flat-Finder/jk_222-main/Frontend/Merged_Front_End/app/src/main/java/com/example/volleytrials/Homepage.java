package com.example.volleytrials;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

/**
 * This page is the homepage for Tenant users. It has many navigation buttons and a list of recommended roommates.
 */
public class Homepage extends AppCompatActivity {
    /**
     * Initializes the buttons for homepage and creates a button for every recommended roommate
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        for (int i = 1; i < UserData.getInstance().getArray().length(); i++) {
            Button nButton = new Button(this);
            LinearLayout layout = findViewById(R.id.homepageLinear);
            try {
                nButton.setText(UserData.getInstance().getArray().getJSONObject(i).get("userName").toString());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            nButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            int tenantId;
            try {
                tenantId = UserData.getInstance().getArray().getJSONObject(i).getInt("userId");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            int finalTenantId = tenantId;
            nButton.setOnClickListener(v -> {
                Intent intent = new Intent(Homepage.this, OtherTenantDetails.class);
                intent.putExtra("tenant_id", finalTenantId);
                startActivity(intent);
                finish();
            });
            layout.addView(nButton);
        }

        Button tolistings = findViewById(R.id.toListings);
        tolistings.setOnClickListener(view -> {
            Intent intent = new Intent(Homepage.this, TopListings.class);
            startActivity(intent);
            finish();
        });

        Button toLogInButton = findViewById(R.id.buttonLogOut);
        toLogInButton.setOnClickListener(v -> {
            Intent intent = new Intent(Homepage.this, Login.class);
            startActivity(intent);
            finish();
        });

        Button toEdit = findViewById(R.id.buttonToEdit);
        toEdit.setOnClickListener(v -> {
            Intent intent = new Intent(Homepage.this, EditProfile.class);
            startActivity(intent);
            finish();
        });

        Button toNotifications = findViewById(R.id.toNotifications);
        toNotifications.setText("Notifications " + (UserData.getInstance().getNotifications().size()-1));
        toNotifications.setOnClickListener(v -> {
            Intent intent = new Intent(Homepage.this, Notifications.class);
            intent.putExtra("userType", "tenant");
            startActivity(intent);
            finish();
        });

        Button toSearch = findViewById(R.id.toSearch);
        toSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, UserSearch.class);
                intent.putExtra("userType", "tenant");
                startActivity(intent);
                finish();
            }
        });

        Button toComplaints = findViewById(R.id.toComplaints);
        toComplaints.setOnClickListener(v -> {
            Intent intent = new Intent(Homepage.this, TenantComplaints.class);
            startActivity(intent);
            finish();
        });

        Button toGroups = findViewById(R.id.toGroups);
        toGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, Groups.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
