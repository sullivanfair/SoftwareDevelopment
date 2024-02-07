package com.example.test3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                // Here you should add the code you want to execute when the button is clicked
                // In our case we want to open the activity
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                // It will open the activity
                startActivity(intent);
            }
        });
    }
}