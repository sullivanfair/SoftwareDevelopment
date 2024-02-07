package com.example.volleytrials;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendNotification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);

        EditText email = findViewById(R.id.editToEmail);
        EditText message = findViewById(R.id.editMessage);

        Button send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationService service = new NotificationService();
                WebSocketManager.getInstance().sendMessage(email.getText().toString() + " " + message.getText().toString());
                email.setText("");
                message.setText("");
                Toast.makeText(SendNotification.this, "Message Sent", Toast.LENGTH_SHORT).show();
            }
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SendNotification.this, LandHomepage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}