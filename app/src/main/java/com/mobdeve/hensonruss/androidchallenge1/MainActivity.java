package com.mobdeve.hensonruss.androidchallenge1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView emailListView;
    Button newBtn, latestBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // hide title bar
        setContentView(R.layout.activity_main);

        emailListView = (ListView) findViewById(R.id.emailListView);

        ArrayList<String> receiverList = new ArrayList<>();
        ArrayList<String> subjectList = new ArrayList<>();
        ArrayList<String> bodyList = new ArrayList<>();



        Intent i = getIntent();

        String receiver = i.getStringExtra(NewEmailActivity.RECEIVER_TAG);
        String subject = i.getStringExtra(NewEmailActivity.SUBJECT_TAG);
        String body = i.getStringExtra(NewEmailActivity.BODY_TAG);

        receiverList.add(receiver);
        subjectList.add(subject);
        bodyList.add(body);

        receiverList.add("henson.rj10@gmail.com");
        receiverList.add("steezymnl@gmail.com");
        receiverList.add("henson.russ10@gmail.com");
        subjectList.add("Question regarding Photoshop");
        subjectList.add("Steezy design - April");
        subjectList.add("Meeting set");
        bodyList.add("hakdjaiofhasoihdfiosahfioshfioa");
        bodyList.add("pangalawa shoaifhfioashoifhasio faiosfhaioshjfio hfioashfioas");
        bodyList.add("pangatlo jhasiofhaioshfioas fhasiofhaowe  jrf90ajfh09we");


        EmailListAdapter adapter = new EmailListAdapter(this, receiverList, subjectList);
        emailListView.setAdapter(adapter);

        this.newBtn = findViewById(R.id.newBtn);
        newBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NewEmailActivity.class);
                startActivity(i);
            }
        });


        this.latestBtn = findViewById(R.id.latestBtn);
        latestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, EmailActivity.class);

                //PASS DATA TO SHOW THE LATEST

                startActivity(i);
            }
        });

    }

    class EmailListAdapter extends ArrayAdapter<String> {

        Context context;
        ArrayList<String> rReceiver;
        ArrayList<String> rSubject;
        ArrayList<String> rBody;

        EmailListAdapter(Context c, ArrayList<String> receiver, ArrayList<String> subject, ArrayList<String> body) {
            super(c, R.layout.email_layout, R.id.sentEmailReceiverTv, receiver);
            this.context = c;
            this.rReceiver = receiver;
            this.rSubject = subject;
            this.rBody = body;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View email = layoutInflater.inflate(R.layout.email_layout, parent, false);
            TextView receiver = email.findViewById(R.id.sentEmailReceiverTv);
            TextView subject = email.findViewById(R.id.sentEmailSubjectTv);
            TextView body = email.findViewById(R.id.sentEmailBodyTv);

            receiver.setText(rReceiver.get(position));
            subject.setText(rSubject.get(position));
            body.setText(rBody.get(position));

            return email;
        }



    }
}