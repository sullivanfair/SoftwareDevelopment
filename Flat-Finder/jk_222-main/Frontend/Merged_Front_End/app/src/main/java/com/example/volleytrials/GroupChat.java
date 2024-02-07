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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;

public class GroupChat extends AppCompatActivity implements WebSocketListener{

    String roomName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            roomName = extra.getString("roomName");
        }

        String serverUrl = "ws://coms-309-016.class.las.iastate.edu:8080/chat/" + roomName + "/" + UserData.getInstance().getName();
        WebSocketManager.getInstance().connectWebSocket(serverUrl);
        WebSocketManager.getInstance().setWebSocketListener(GroupChat.this);

        String url = "http://coms-309-016.class.las.iastate.edu:8080/chats/messages/" + roomName;
        RequestQueue queue = Volley.newRequestQueue(GroupChat.this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    for (int i = 0; i < response.length(); i++) {
                        TextView nText = new TextView(this);
                        LinearLayout layout = findViewById(R.id.groupChatLinear);
                        try {
                            nText.setText(response.getJSONObject(i).getJSONObject("sender").get("userName").toString() + ": " +response.getJSONObject(i).get("content").toString());
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        nText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        layout.addView(nText);
                    }
                },
                error -> {});
        queue.add(request);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupChat.this, Groups.class);
                startActivity(intent);
                finish();
            }
        });

        Button leaveButton = findViewById(R.id.leaveButton);
        leaveButton.setOnClickListener(new View.OnClickListener() {
            String roomId = null;
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(GroupChat.this);

                String url = "http://coms-309-016.class.las.iastate.edu:8080/chats/chatrooms/" + roomId;
                JsonObjectRequest removeRoom = new JsonObjectRequest(Request.Method.DELETE, url, null,
                        response -> {},
                        error -> {});
                queue.add(removeRoom);

                url = "http://coms-309-016.class.las.iastate.edu:8080/chats/chatrooms/" + roomName + "/participants/" + UserData.getInstance().getName();
                JsonArrayRequest getRoomId = new JsonArrayRequest(Request.Method.GET, url, null,
                        response -> {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    if (response.getJSONObject(i).get("chatRoomName").toString().equals(roomName)) {
                                        roomId = response.getJSONObject(i).get("chatRoomId").toString();
                                    }
                                }
                                catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }, error -> {});
                queue.add(getRoomId);

                url = "http://coms-309-016.class.las.iastate.edu:8080/chats/chatrooms/" + roomId;
                JsonObjectRequest jsonRemoveRoom = new JsonObjectRequest(Request.Method.DELETE, url, null,
                        response -> {},
                        error -> {});
                queue.add(jsonRemoveRoom);

                Intent intent = new Intent(GroupChat.this, Groups.class);
                startActivity(intent);
                finish();
            }
        });

        EditText message = findViewById(R.id.editMessage);

        Button sendMessage = findViewById(R.id.sendButton);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebSocketManager.getInstance().sendMessage(message.getText().toString());
                message.setText("");
            }
        });
    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onWebSocketMessage(String message) {
        runOnUiThread(() -> {
            try{
                JSONObject jsonObject = new JSONObject(message);
                // Extract data from the JSON message
                String content = jsonObject.optString("content", "Default Content");
                String sender = jsonObject.optString("sender", "Default Sender");
                Log.d("Tag", content);
                // Update your UI with the extracted data
                TextView nText = new TextView(this);
                LinearLayout layout = findViewById(R.id.groupChatLinear);
                String showMessage = jsonObject.getJSONObject("sender").get("userName").toString() + ": " + jsonObject.get("content").toString();
                nText.setText(showMessage);
                nText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                layout.addView(nText);
            }
            catch(JSONException e){
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onWebSocketError(Exception ex) {

    }
}