package com.example.volleytrials;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserToUserChat extends AppCompatActivity implements WebSocketListener{
    String URL = "";

    String oldComment = "";
    String postComment = "";
    private RequestQueue requestQueue;
    private TextView thread;
    private EditText message;

    protected void onCreate(Bundle savedInstanceState) {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        String serverUrl = "ws://coms-309-016.class.las.iastate.edu:8080/chat/room1/" + UserData.getInstance().getName();

        Log.d("CHATTAG", serverUrl);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_to_user_chat);

        Intent intent = getIntent();
        int tenantId = intent.getIntExtra("tenant_id", 0);

        getDetails(tenantId);

        Button backButton = findViewById(R.id.btnBack);
        Button sendButton = findViewById(R.id.bt2);
        message = (EditText) findViewById(R.id.messageTXT);
        thread = (TextView) findViewById(R.id.tv1);

        //CONNECT
        WebSocketManager.getInstance().connectWebSocket(serverUrl);
        WebSocketManager.getInstance().setWebSocketListener(UserToUserChat.this);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserToUserChat.this, OtherTenantDetails.class);
                intent.putExtra("tenant_id", tenantId);
                startActivity(intent);
                finish();
            }
        });

        //SEND
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // send message
                    WebSocketManager.getInstance().sendMessage(message.getText().toString());
                } catch (Exception e) {
                    Log.d("ExceptionSendMessage:", e.getMessage().toString());
                }
                message.setText("");
                WebSocketManager.getInstance().connectWebSocket("ws://coms-309-016.class.las.iastate.edu:8080/notif/" + UserData.getInstance().getEmail());
                String url = "http://coms-309-016.class.las.iastate.edu:8080/users";
                RequestQueue queue = Volley.newRequestQueue(UserToUserChat.this);
                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                        response -> {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    if (response.getJSONObject(i).getInt("userId") == (tenantId)) {
                                        String targetEmail = response.getJSONObject(i).get("userEmail").toString();
                                        WebSocketManager.getInstance().sendMessage(targetEmail + " " + UserData.getInstance().getName() + "sent you a message");
                                        break;
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        },
                        error -> Toast.makeText(UserToUserChat.this, "Something Went Wrong", Toast.LENGTH_SHORT).show());
                queue.add(request);
            }
        });
    }

    //RECEIVE
    @Override
    public void onWebSocketMessage(String message) {
        /**
         * In Android, all UI-related operations must be performed on the main UI thread
         * to ensure smooth and responsive user interfaces. The 'runOnUiThread' method
         * is used to post a runnable to the UI thread's message queue, allowing UI updates
         * to occur safely from a background or non-UI thread.
         */
        runOnUiThread(() -> {
            try{
                JSONObject jsonObject = new JSONObject(message);
                // Extract data from the JSON message
                String content = jsonObject.optString("content", "Default Content");
                String sender = jsonObject.optString("sender", "Default Sender");
                Log.d("Tag", content);
                // Update your UI with the extracted data
                String threadTxt = thread.getText().toString();
                if (content.equals("") || content == null){
                    thread.setText(threadTxt);
                }
                else{
                    thread.setText(threadTxt + "\n" + sender + ": " + content);
                }
            }
            catch(JSONException e){
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        String closedBy = remote ? "server" : "local";
        runOnUiThread(() -> {
            String s = thread.getText().toString();
            thread.setText(s + "---\nconnection closed by " + closedBy + "\nreason: " + reason);
        });
    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {}

    @Override
    public void onWebSocketError(Exception ex) {}

    private void getDetails(int tenantId){
        URL = Const.User_URL;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int i;
                        try{
                            JSONObject tenantObject = response.getJSONObject(0);
                            for (i = 0; i < response.length(); i ++){
                                JSONObject trialObject = response.getJSONObject(i);
                                if (trialObject.has("userId") && trialObject.getInt("userId") == tenantId){
                                    tenantObject = trialObject;
                                }
                            }
                            String name = tenantObject.optString("userName");
                            // Update your UI elements
                            TextView tname = findViewById(R.id.otherName);
                            //Set Text Views
                            tname.setText(name);

                        }catch (Exception e) {
                            Log.e("Error Tag", e.toString());
                            Log.d("Error Tag", "CONFUSION");
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error Tag", "CONFUSION");
                        Log.e("Error Tag", error.toString());
                        // Handle any errors that occur during the request.
                    }
                });
        requestQueue.add(request);
    }
}
