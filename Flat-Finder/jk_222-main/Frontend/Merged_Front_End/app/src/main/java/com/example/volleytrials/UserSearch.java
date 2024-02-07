package com.example.volleytrials;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * This page is used by users to search for other users
 */
public class UserSearch extends AppCompatActivity {
    private ArrayList<JSONObject> ObjectArray = new ArrayList<JSONObject>();
    private EditText searchName;
    private EditText searchEmail;
    private EditText searchBudget;
    private EditText searchAddress;
    private EditText searchPets;

    /**
     * Initializes objects and sets on click listeners in the UserSearch page
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);

        Bundle extras = getIntent().getExtras();
        String userType = null;
        if (extras != null) {
            userType = extras.getString("userType");
        }

        String url = "http://coms-309-016.class.las.iastate.edu:8080/users";
        RequestQueue queue = Volley.newRequestQueue(UserSearch.this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            ObjectArray.add(response.getJSONObject(i));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    Log.d("Array List", ObjectArray.toString());
                },
                error -> {
                    Toast.makeText(this, "Server Request Failed", Toast.LENGTH_SHORT).show();
                });
        queue.add(request);

        searchName = findViewById(R.id.searchName);
        searchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    sortArray(0, ObjectArray.size() - 1);
                    displayArray();
                }
                catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        searchEmail = findViewById(R.id.searchEmail);
        searchEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    sortArray(0, ObjectArray.size() - 1);
                    displayArray();
                }
                catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        searchBudget = findViewById(R.id.searchBudget);
        searchBudget.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    sortArray(0, ObjectArray.size() - 1);
                    displayArray();
                }
                catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        searchAddress = findViewById(R.id.searchAddress);
        searchAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    sortArray(0, ObjectArray.size() - 1);
                    displayArray();
                }
                catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        searchPets = findViewById(R.id.searchPets);
        searchPets.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    sortArray(0, ObjectArray.size() - 1);
                    displayArray();
                }
                catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        Button searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sortArray(0, ObjectArray.size() - 1);
                    displayArray();
                }
                catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Button searchBack = findViewById(R.id.searchBack);
        String finalUserType = userType;
        searchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
               if (finalUserType.equals("tenant")) {
                   intent = new Intent(UserSearch.this, Homepage.class);
               }
               else if (finalUserType.equals("landlord")) {
                   intent = new Intent(UserSearch.this, LandHomepage.class);
               }
               startActivity(intent);
               finish();
            }
        });
    }

    /**
     * The base method of a quick sort algorithm to order the ObjectArray list
     * @param low The lowest index in the list to sort
     * @param high The highest index in the list to sort
     * @throws JSONException
     */
    private void sortArray(int low, int high) throws JSONException {
        if (low < high) {
            int mid = partition(low, high);
            sortArray(low, mid - 1);
            sortArray(mid + 1, high);
        }
    }

    /**
     * Uses the last index's value as a pivot and puts all values from low to high that are
     * lower than the pivot at lower indexes and all values higher than the pivot at higher indexes
     * @param low The lowest index it will sort
     * @param high The highest index it will sort
     * @return
     * @throws JSONException
     */
    private int partition(int low, int high) throws JSONException {
        JSONObject pivot = ObjectArray.get(high);
        int i = low - 1;
        for (int j = low; j <= high; j++) {
            if (compare(ObjectArray.get(j), pivot) < 0) {
                i++;
                swap(i, j);
            }
        }
        swap(i+1, high);
        return i + 1;
    }

    /**
     * Compares 2 JSONObjects sorting first by variables that are filtered then again by unfiltered
     * @param i The first JSONObject being compared
     * @param j The second JSONObject being compared
     * @return Returns -1 if i should be first, 1 if j should be first, and 0 otherwise
     * @throws JSONException
     */
    private int compare(JSONObject i, JSONObject j) throws JSONException {
        if (!searchName.getText().toString().equals("")) {
            String iName = i.get("userName").toString();
            String jName = j.get("userName").toString();
            String targetName = searchName.getText().toString();
            int iMatch = 0;
            int jMatch = 0;
            for (int x = 0; x < searchName.getText().length(); x++) {
                if (iName.charAt(x) == targetName.charAt(x)) {
                    iMatch++;
                }
                if (jName.charAt(x) == targetName.charAt(x)) {
                    jMatch++;
                }
            }
            if (iMatch > jMatch) {
                return -1;
            }
            else if (iMatch < jMatch) {
                return 1;
            }
        }
        if (!searchEmail.getText().toString().equals("")) {
            String iEmail = i.get("userEmail").toString();
            String jEmail = j.get("userEmail").toString();
            String targetEmail = searchEmail.getText().toString();
            int iMatch = 0;
            int jMatch = 0;
            for (int x = 0; x < targetEmail.length(); x++) {
                if (iEmail.charAt(x) == targetEmail.charAt(x)) {
                    iMatch++;
                }
                if (jEmail.charAt(x) == targetEmail.charAt(x)) {
                    jMatch++;
                }
            }
            if (iMatch > jMatch) {
                return -1;
            }
            else if (iMatch < jMatch) {
                return 1;
            }
        }
        if (!searchBudget.getText().toString().equals("")) {
            String targetBudget = searchBudget.getText().toString();
            int iMatch = 0;
            int jMatch = 0;
            if (i.has("tenantBudget")) {
                String iBudget = i.get("tenantBudget").toString();
                for (int x = 0; 0 < targetBudget.length(); x++) {
                    if (iBudget.charAt(x) == targetBudget.charAt(x)) {
                        iMatch++;
                    }
                }
            }
            if (j.has("tenantBudget")) {
                String jBudget = j.get("tenantBudget").toString();
                for (int x = 0; 0 < targetBudget.length(); x++) {
                    if (jBudget.charAt(x) == targetBudget.charAt(x)) {
                        jMatch++;
                    }
                }
            }
            if (iMatch > jMatch) {
                return -1;
            }
            else if (iMatch < jMatch) {
                return 1;
            }
        }
        if (!searchAddress.getText().toString().equals("")) {
            String targetAddress = searchAddress.getText().toString();
            int iMatch = 0;
            int jMatch = 0;
            if (i.has("tenantAddress")) {
                String iAddress = i.get("tenantAddress").toString();
                for (int x = 0; 0 < targetAddress.length(); x++) {
                    if (iAddress.charAt(x) == targetAddress.charAt(x)) {
                        iMatch++;
                    }
                }
            }
            if (j.has("tenantAddress")) {
                String jAddress = j.get("tenantBudget").toString();
                for (int x = 0; 0 < targetAddress.length(); x++) {
                    if (jAddress.charAt(x) == targetAddress.charAt(x)) {
                        jMatch++;
                    }
                }
            }
            if (iMatch > jMatch) {
                return -1;
            }
            else if (iMatch < jMatch) {
                return 1;
            }
        }
        if (!searchPets.getText().toString().equals("")) {
            String targetPets = searchPets.getText().toString();
            int iMatch = 0;
            int jMatch = 0;
            if (i.has("tenantPetPreference")) {
                String iPets = i.get("tenantPetPreference").toString();
                for (int x = 0; 0 < targetPets.length(); x++) {
                    if (iPets.charAt(x) == targetPets.charAt(x)) {
                        iMatch++;
                    }
                }
            }
            if (j.has("tenantPetPreference")) {
                String jPets = j.get("tenantPetPreference").toString();
                for (int x = 0; 0 < targetPets.length(); x++) {
                    if (jPets.charAt(x) == targetPets.charAt(x)) {
                        jMatch++;
                    }
                }
            }
            if (iMatch > jMatch) {
                return -1;
            }
            else if (iMatch < jMatch) {
                return 1;
            }
        }
        int compareInt = i.get("userName").toString().compareTo(j.get("userName").toString());
        if (compareInt < 0) {
            return -1;
        }
        else if (compareInt > 0) {
            return 1;
        }
        compareInt = i.get("userEmail").toString().compareTo(j.get("userEmail").toString());
        if (compareInt < 0) {
            return -1;
        }
        else if (compareInt > 0) {
            return 1;
        }
        if (i.has("tenantBudget")) {
            if (j.has("tenantBudget")) {
                compareInt = i.get("tenantBudget").toString().compareTo(j.get("tenantBudget").toString());
                if (compareInt < 0) {
                    return -1;
                }
                else if (compareInt > 0) {
                    return 1;
                }
            }
            else {
                return -1;
            }
        }
        else if (j.has("tenantBudget")) {
            return -1;
        }
        if (i.has("tenantAddress")) {
            if (j.has("tenantAddress")) {
                compareInt = i.get("tenantAddress").toString().compareTo(j.get("tenantAddress").toString());
                if (compareInt < 0) {
                    return -1;
                }
                else if (compareInt > 0) {
                    return 1;
                }
            }
            else {
                return -1;
            }
        }
        else if (j.has("tenantAddress")) {
            return -1;
        }
        if (i.has("tenantPetPreference")) {
            if (j.has("tenantPetPreference")) {
                compareInt = i.get("tenantPetPreference").toString().compareTo(j.get("tenantPetPreference").toString());
                if (compareInt < 0) {
                    return -1;
                }
                else if (compareInt > 0) {
                    return 1;
                }
            }
            else {
                return -1;
            }
        }
        else if (j.has("tenantPetPreference")) {
            return -1;
        }
        return 0;

    }

    /**
     * Swaps the values of index i and j in the ObjectArray list
     * @param i the first index to swap
     * @param j the second index to swap
     */
    private void swap(int i, int j) {
        JSONObject temp = ObjectArray.get(i);
        ObjectArray.set(i, ObjectArray.get(j));
        ObjectArray.set(j, temp);
    }

    /**
     * Goes through the ObjectArray list and creates a Table Row for each person and adds them to the list
     * @throws JSONException
     */
    private void displayArray() throws JSONException {
        ScrollView scroll = findViewById(R.id.searchScroll);
        scroll.removeAllViews();
        LinearLayout nLayout = new LinearLayout(this);
        nLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        nLayout.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < ObjectArray.size(); i++) {
            LinearLayout layout = new LinearLayout(this);
            layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.setGravity(Gravity.CENTER);
            TextView showName = new TextView(this);
            showName.setLayoutParams(new ViewGroup.LayoutParams(getResources().getDimensionPixelSize(R.dimen.SixtyFivedp), ViewGroup.LayoutParams.WRAP_CONTENT));
            showName.setText(ObjectArray.get(i).get("userName").toString());
            showName.setMaxLines(1);
            TextView showEmail = new TextView(this);
            showEmail.setLayoutParams(new ViewGroup.LayoutParams(getResources().getDimensionPixelSize(R.dimen.SixtyFivedp), ViewGroup.LayoutParams.WRAP_CONTENT));
            showEmail.setText(ObjectArray.get(i).get("userEmail").toString());
            showEmail.setMaxLines(1);
            TextView showBudget = new TextView(this);
            showBudget.setLayoutParams(new ViewGroup.LayoutParams(getResources().getDimensionPixelSize(R.dimen.SixtyFivedp), ViewGroup.LayoutParams.WRAP_CONTENT));
            if (ObjectArray.get(i).has("tenantBudget")) {
                showBudget.setText(ObjectArray.get(i).get("tenantBudget").toString());
            }
            showBudget.setMaxLines(1);
            TextView showAddress = new TextView(this);
            showAddress.setLayoutParams(new ViewGroup.LayoutParams(getResources().getDimensionPixelSize(R.dimen.SixtyFivedp), ViewGroup.LayoutParams.WRAP_CONTENT));
            if (ObjectArray.get(i).has("tenantAddress")) {
                showAddress.setText(ObjectArray.get(i).get("tenantAddress").toString());
            }
            showAddress.setMaxLines(1);
            TextView showPets = new TextView(this);
            showPets.setLayoutParams(new ViewGroup.LayoutParams(getResources().getDimensionPixelSize(R.dimen.SixtyFivedp), ViewGroup.LayoutParams.WRAP_CONTENT));
            if (ObjectArray.get(i).has("tenantPetPreference")) {
                showPets.setText(ObjectArray.get(i).get("tenantPetPreference").toString());
            }
            showPets.setMaxLines(1);
            Button button = new Button(this);
            button.setLayoutParams(new ViewGroup.LayoutParams(getResources().getDimensionPixelSize(R.dimen.SixtyFivedp), ViewGroup.LayoutParams.WRAP_CONTENT));
            button.setText("View");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "http://coms-309-016.class.las.iastate.edu:8080/users";
                    RequestQueue queue = Volley.newRequestQueue(UserSearch.this);
                    JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                            response -> {
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        if (showName.getText().toString().equals(response.getJSONObject(i).get("userName").toString())) {
                                            if (showEmail.getText().toString().equals(response.getJSONObject(i).get("userEmail").toString())) {
                                                Intent intent = new Intent(UserSearch.this, OtherTenantDetails.class);
                                                intent.putExtra("tenant_id", Integer.parseInt(response.getJSONObject(i).get("userId").toString()));
                                                startActivity(intent);
                                                finish();
                                                break;
                                            }
                                        }
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            },
                            error -> Toast.makeText(UserSearch.this, "Something Went Wrong", Toast.LENGTH_SHORT).show());
                    queue.add(request);
                }
            });
            layout.addView(showName);
            layout.addView(showEmail);
            layout.addView(showBudget);
            layout.addView(showAddress);
            layout.addView(showPets);
            layout.addView(button);
            nLayout.addView(layout);
        }
        scroll.addView(nLayout);
    }
}