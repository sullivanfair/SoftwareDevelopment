package com.example.volleytrials;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

/**
 * This page shows the user all notifications they have received
 */
public class Notifications extends AppCompatActivity{
    /**
     * Initializes objects and sets OnClickListeners for the Notifications Activity
     * Creates an object for every Notification this user has in the User Data file
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        Bundle extras = getIntent().getExtras();
        String userType;
        if (extras != null) {
            userType = extras.getString("userType");
        } else {
            userType = null;
        }

        for (int i = 0; i < UserData.getInstance().getNotifications().size(); i++) {
            TextView nView = new TextView(this);
            LinearLayout layout = findViewById(R.id.notiLinear);
            nView.setText(UserData.getInstance().getNotifications().get(i));
            nView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.addView(nView);
        }
        WebSocketManager.getInstance().connectWebSocket("ws://coms-309-016.class.las.iastate.edu:8080/notif/" + UserData.getInstance().getEmail());
        WebSocketManager.getInstance().sendMessage(UserData.getInstance().getEmail() + " Clear Notification History");
        UserData.getInstance().getNotifications().clear();

        Button back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            Intent intent = null;
            if (userType.equals("tenant")) {
                intent = new Intent(Notifications.this, Homepage.class);
            }
            else if (userType.equals("landlord")) {
                intent = new Intent(Notifications.this, LandHomepage.class);
            }
            startActivity(intent);
            finish();
        });
    }

    /**
     * Called in case the back button is placed and the user is not a Tenant User and sends them to the Activity LandHomepage
     */
    private void landlordBack() {
        Intent intent = new Intent(Notifications.this, LandHomepage.class);
        startActivity(intent);
        finish();
    }
}