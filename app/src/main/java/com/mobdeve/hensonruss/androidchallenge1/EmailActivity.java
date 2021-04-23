// Please make sure to change the package name to match
// your own project's package.
package com.mobdeve.hensonruss.androidchallenge1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class EmailActivity extends AppCompatActivity {

    // Views needed
    private TextView senderTv, receiverTv, subjectTv, bodyTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        // Initialization of the views
        this.senderTv = findViewById(R.id.emailSenderTv);
        this.receiverTv = findViewById(R.id.emailReceiverTv);
        this.subjectTv = findViewById(R.id.emailSubjectTv);
        this.bodyTv = findViewById(R.id.emailBodyTv);

        // Get intent data
        Intent i = getIntent();
        String sender = "From: me";
        String receiver;
        String subject;
        String body;

        // Set Text attributes of all views
        this.senderTv.setText(sender);
        this.receiverTv.setText(receiver);
        this.subjectTv.setText(subject);
        this.bodyTv.setText(body);
    }
}