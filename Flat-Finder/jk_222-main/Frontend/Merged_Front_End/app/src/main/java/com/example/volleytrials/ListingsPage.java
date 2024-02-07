package com.example.volleytrials;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import org.json.JSONException;

/**
 * This page allows Landlord Users to see all Listings connected to their account
 */
public class ListingsPage extends AppCompatActivity {
    /**
     * Initializes all objects and sets OnClickListeners for the ListingsPage activity
     * Creates a new object for every listing
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lisitings_page);

        Button backButton = findViewById(R.id.listingBackButton);

        for (int i = 0; i < UserData.getInstance().getArray().length(); i++) {
            ScrollView scroll = findViewById(R.id.listingScroll);
            TextView nView = new TextView(this);
            try {
                nView.setText(UserData.getInstance().getArray().getJSONObject(i).get("listingName").toString());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            nView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            Button nButton = new Button(this);
            nButton.setText("Edit");
            nButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            nButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ListingsPage.this, ListingsEdit.class);
                    startActivity(intent);
                }
            });
            LinearLayout nLayout = new LinearLayout(this);
            nLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            nLayout.setGravity(Gravity.CENTER);
            nLayout.addView(nView);
            nLayout.addView(nButton);
            scroll.addView(nLayout);
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListingsPage.this, LandHomepage.class);
                startActivity(intent);
            }
        });
    }
}