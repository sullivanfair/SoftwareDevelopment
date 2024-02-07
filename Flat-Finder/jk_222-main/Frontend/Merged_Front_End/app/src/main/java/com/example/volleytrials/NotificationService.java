package com.example.volleytrials;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import org.java_websocket.handshake.ServerHandshake;

/**
 * This class sets a service that runs a Websocket allowing the notification websocket listener to run on every page
 */
public class NotificationService extends Service implements WebSocketListener{
    /**
     * Calls a method to start the websocket when the activity is started
     * @param intent The Intent supplied to {@link android.content.Context#startService},
     * as given.  This may be null if the service is being restarted after
     * its process has gone away, and it had previously returned anything
     * except {@link #START_STICKY_COMPATIBILITY}.
     * @param flags Additional data about this start request.
     * @param startId A unique integer representing this specific request to
     * start.  Use with {@link #stopSelfResult(int)}.
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        startWebSocket();
        return Service.START_STICKY;
    }

    /**
     * Not implemented
     * @param intent The Intent that was used to bind to this service,
     * as given to {@link android.content.Context#bindService
     * Context.bindService}.  Note that any extras that were included with
     * the Intent at that point will <em>not</em> be seen here.
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Creates and connects a web socket that listens and deals with notification messages
     */
    private void startWebSocket(){
//        Log.d("WebSocket", "startWebSocket Ran");
//        Log.d("UserData", UserData.getInstance().getEmail());
        String webSocketUrl = "ws://coms-309-016.class.las.iastate.edu:8080/notif/" + UserData.getInstance().getEmail();
//        Log.d("WebSocket", webSocketUrl);
        WebSocketManager.getInstance().connectWebSocket(webSocketUrl);
        WebSocketManager.getInstance().setWebSocketListener(this);
    }

    /**
     * Logs that the websocket has been opened
     * @param handshakedata Information about the server handshake.
     */
    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {
        Log.d("WebSocket", "Is Open");
    }

    /**
     * Logs that a message has been received and adds the message to UserData's notifications
     * @param message The received WebSocket message.
     */
    @Override
    public void onWebSocketMessage(String message) {
        UserData.getInstance().getNotifications().add(message);
    }

    /**
     * Logs that the websocket has been closed as well as the reason for closing
     * @param code   The status code indicating the reason for closure.
     * @param reason A human-readable explanation for the closure.
     * @param remote Indicates whether the closure was initiated by the remote endpoint.
     */
    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        Log.d("WebSocket", "Web Scoket closed because " + reason);
    }

    /**
     * Logs the error
     * @param ex The exception that describes the error.
     */
    @Override
    public void onWebSocketError(Exception ex) {
        Log.d("Error", ex.toString());
    }
}
