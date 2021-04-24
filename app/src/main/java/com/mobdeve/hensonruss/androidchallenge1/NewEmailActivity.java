package com.mobdeve.hensonruss.androidchallenge1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewEmailActivity extends AppCompatActivity {

    Button discardBtn, sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // hide title bar
        setContentView(R.layout.activity_new_email);

        this.discardBtn = findViewById(R.id.discardBtn);
        discardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewEmailActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        this.sendBtn = findViewById(R.id.sendBtn);
    }
}