package com.example.testing2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button)findViewById(R.id.button);
        TextView View = (TextView)findViewById(R.id.View);
        button.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                View.setText("You clicked the button");
            }
        });
    }
}