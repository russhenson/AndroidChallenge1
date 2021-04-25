package com.mobdeve.hensonruss.androidchallenge1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewEmailActivity extends AppCompatActivity {

    Button discardBtn, sendBtn;
    EditText receiverEt, subjectEt, emailBodyEt;

    private static final String RECEIVER_TAG = "RECEIVER_TAG";
    private static final String SUBJECT_TAG = "SUBJECT_TAG";
    private static final String BODY_TAG = "BODY_TAG";
    private static final String DRAFT_TAG = "DRAFT_TAG";

    private boolean draft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // hide title bar
        setContentView(R.layout.activity_new_email);

        // initialize edit text
        this.receiverEt = findViewById(R.id.receiverEt);
        this.subjectEt = findViewById(R.id.subjectEt);
        this.emailBodyEt = findViewById(R.id.emailBodyEt);

        Intent i = getIntent();

        draft = i.getBooleanExtra(DRAFT_TAG, false);

        if(draft == true){
            receiverEt.setText(i.getStringExtra(RECEIVER_TAG));
            subjectEt.setText(i.getStringExtra(SUBJECT_TAG));
            emailBodyEt.setText(i.getStringExtra(BODY_TAG));
        }
        else {
            receiverEt.setText(null);
            subjectEt.setText(null);
            emailBodyEt.setText(null);
        }


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

                if(receiver.length() < 1 | subject.length() < 1 | body.length() < 1){
                    Toast.makeText(
                            NewEmailActivity.this,
                            "Please make sure all entries have text.",
                            Toast.LENGTH_LONG
                    ).show();
                }
                else if(draft == true){
                    draft = false;
                    editor.putBoolean(DRAFT_TAG, draft);
                    // delete draft

                    startActivity(i);
                    finish();
                }
                else {
                    startActivity(i);
                    finish();
                }



            }
        });
        Log.d("NewEmailActivity", "onCreate called.");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("NewEmailActivity", "onResume called.");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        int receiver = receiverEt.getText().toString().length();
        int subject = subjectEt.getText().toString().length();
        int body = emailBodyEt.getText().toString().length();

        // if it's empty
        if(receiver < 1 && subject < 1 && body < 1)
            draft = false;
        else
            draft = true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();

        String receiver = receiverEt.getText().toString();
        String subject = subjectEt.getText().toString();
        String body = emailBodyEt.getText().toString();

        if(receiver.length() > 1 | subject.length() > 1 | body.length() > 1){
            editor.putString(RECEIVER_TAG, receiver);
            editor.putString(SUBJECT_TAG, subject);
            editor.putString(BODY_TAG, body);
            editor.putBoolean(DRAFT_TAG, draft);

            editor.apply();
        }

        Boolean draft = sp.getBoolean(DRAFT_TAG, false);

        //Log.d("NewEmailActivity", "onPause called.");
        Log.d("NewEmailActivity", "onPause called. Draft: "  + draft);

    }

    @Override
    protected void onStop() {
        super.onStop();

        /*SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();

        String receiver = receiverEt.getText().toString();
        String subject = subjectEt.getText().toString();
        String body = emailBodyEt.getText().toString();

        if(receiver.length() > 1 | subject.length() > 1 | body.length() > 1){
            editor.putString(RECEIVER_TAG, receiver);
            editor.putString(SUBJECT_TAG, subject);
            editor.putString(BODY_TAG, body);
            editor.putBoolean(DRAFT_TAG, draft);

            editor.apply();
        }

        Boolean draft = sp.getBoolean(DRAFT_TAG, false);*/

        Log.d("NewEmailActivity", "onStop called.");
        //Log.d("NewEmailActivity", "onStop called. Draft:" + draft);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("NewEmailActivity", "onDestroy called.");
    }
}