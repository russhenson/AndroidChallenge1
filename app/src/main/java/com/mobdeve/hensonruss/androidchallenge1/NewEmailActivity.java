package com.mobdeve.hensonruss.androidchallenge1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewEmailActivity extends AppCompatActivity {

    Button discardBtn, sendBtn;
    EditText receiverEt, subjectEt, emailBodyEt;

    public static final String RECEIVER_TAG = "RECEIVER_TAG";
    public static final String SUBJECT_TAG = "SUBJECT_TAG";
    public static final String BODY_TAG = "BODY_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // hide title bar
        setContentView(R.layout.activity_new_email);

        // initialize edit text
        this.receiverEt = findViewById(R.id.receiverEt);
        this.subjectEt = findViewById(R.id.subjectEt);
        this.emailBodyEt = findViewById(R.id.emailBodyEt);

        this.discardBtn = findViewById(R.id.discardBtn);
        discardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewEmailActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();

        this.sendBtn = findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewEmailActivity.this, MainActivity.class);

                String receiver = receiverEt.getText().toString();
                String subject = subjectEt.getText().toString();
                String body = emailBodyEt.getText().toString();

                i.putExtra(RECEIVER_TAG, receiver);
                i.putExtra(SUBJECT_TAG, subject);
                i.putExtra(BODY_TAG, body);

                editor.putString(RECEIVER_TAG, receiver);
                editor.putString(SUBJECT_TAG, subject);
                editor.putString(BODY_TAG, body);


                startActivity(i);
                finish();

            }
        });
    }
}