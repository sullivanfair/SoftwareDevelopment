package com.example.volleytrials;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class Groups extends AppCompatActivity {

    EditText joinGroup;
    String roomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        joinGroup = findViewById(R.id.joinGroup);

        String url = "http://coms-309-016.class.las.iastate.edu:8080/chats/chatrooms/" + UserData.getInstance().getName();
        RequestQueue queue = Volley.newRequestQueue(Groups.this);
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    for (int i = 0; i < response.length(); i++) {
                        Button nButton = new Button(this);
                        LinearLayout layout = findViewById(R.id.groupsLinear);
                        try {
                            nButton.setText(response.getJSONObject(i).get("chatRoomName").toString());
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        nButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        try {
                            roomName = response.getJSONObject(i).get("chatRoomName").toString();
                        }
                        catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        nButton.setOnClickListener(v -> {
                            Intent intent = new Intent(Groups.this, GroupChat.class);
                            intent.putExtra("roomName", roomName);
                            startActivity(intent);
                            finish();
                        });
                        layout.addView(nButton);
                    }
                },
                error -> {});
        queue.add(jsonRequest);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Groups.this, Homepage.class);
                startActivity(intent);
                finish();
            }
        });
        Button createGroups = findViewById(R.id.createGroups);
        createGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRoom();
                try {
                    joinRoom();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    private void createRoom() {
        String url = "http://coms-309-016.class.las.iastate.edu:8080/chats/chatrooms";
        HashMap<String, String> params = new HashMap<>();
        params.put("chatRoomName", joinGroup.getText().toString());
        RequestQueue queue = Volley.newRequestQueue(Groups.this);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                response -> {}, error -> Toast.makeText(Groups.this, "Error", Toast.LENGTH_LONG).show());
        queue.add(jsonRequest);
    }

    private void joinRoom() throws JSONException {
        JSONObject participant = null;
        String url = "http://coms-309-016.class.las.iastate.edu:8080/users";
        RequestQueue queue = Volley.newRequestQueue(Groups.this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            if (UserData.getInstance().getID().equals(response.getJSONObject(i).get("userId").toString())) {
//                                participant = response.getJSONObject(i);
//                                Log.d("AHHHHHHHH", response.getJSONObject(i).toString());
                                String newUrl = "http://coms-309-016.class.las.iastate.edu:8080/chats/chatrooms/" + joinGroup.getText().toString() + "/participants";
                                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, newUrl, response.getJSONObject(i),
                                newResponse -> {
                                    Intent intent = new Intent(Groups.this, GroupChat.class);
                                    intent.putExtra("roomName", joinGroup.getText().toString());
                                    startActivity(intent);
                                    finish();
                                }, error -> {
                                    Toast.makeText(Groups.this, "Error", Toast.LENGTH_LONG).show();
                                });
                                queue.add(jsonRequest);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                error -> {
                    Toast.makeText(Groups.this, "Error", Toast.LENGTH_LONG).show();
                });
        queue.add(request);
//        HashMap<String, String> params = new HashMap<>();
//        String userName = participant.get("userName").toString();
//        params.put("userName", userName);
//        url = "http://coms-309-016.class.las.iastate.edu:8080/chats/chatrooms/" + joinGroup.getText().toString() + "/participants";
//        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
//                response -> {
//                    Intent intent = new Intent(Groups.this, GroupChat.class);
//                    intent.putExtra("roomName", joinGroup.getText().toString());
//                    startActivity(intent);
//                    finish();
//                }, error -> {});
//        queue.add(jsonRequest);
    }
}