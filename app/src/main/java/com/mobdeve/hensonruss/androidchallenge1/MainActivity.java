package com.mobdeve.hensonruss.androidchallenge1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView emailListView;
    Button newBtn, latestBtn;
    TextView noEmailsTv;

    public static final String RECEIVER_TAG = "RECEIVER_TAG";
    public static final String SUBJECT_TAG = "SUBJECT_TAG";
    public static final String BODY_TAG = "BODY_TAG";

    private ArrayList<Email> emailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // hide title bar
        setContentView(R.layout.activity_main);

        emailListView = (ListView) findViewById(R.id.emailListView);
        noEmailsTv = findViewById(R.id.noEmailsTv);

        ArrayList<String> receiverList = new ArrayList<>();
        ArrayList<String> subjectList = new ArrayList<>();
        ArrayList<String> bodyList = new ArrayList<>();

        // Get arraylist of emai in the shared preferences
        emailList = SharedPreference.readListFromPref(this);

        // check if there's email in the array list
        if(emailList == null){
            emailListView.setVisibility(View.GONE);
            noEmailsTv.setVisibility(View.VISIBLE);
            emailList = new ArrayList<>();
        }

        Intent i = getIntent();
        String receiver = i.getStringExtra(NewEmailActivity.RECEIVER_TAG);
        String subject = i.getStringExtra(NewEmailActivity.SUBJECT_TAG);
        String body = i.getStringExtra(NewEmailActivity.BODY_TAG);

        Email email = new Email(receiver, subject, body);
        emailList.add(email);

        // update the array list
        SharedPreference.writeListInPref(getApplicationContext(), emailList);

        // reverse the list so that the latest will be shown first
        Collections.reverse(emailList);

        for(int j = 1; j < emailList.size(); j++){
            receiverList.add(emailList.get(j).getReceiver());
            subjectList.add(emailList.get(j).getSubject());
            bodyList.add(emailList.get(j).getBody());
        }

        EmailListAdapter adapter = new EmailListAdapter(this, receiverList, subjectList, bodyList);
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

                if(emailList == null){
                    Toast.makeText(
                            MainActivity.this,
                            "There are no emails currently.",
                            Toast.LENGTH_LONG
                    ).show();
                }
                else {
                    Intent i = new Intent(MainActivity.this, EmailActivity.class);

                    String latestReceiver = emailList.get(1).getReceiver();
                    String latestSubject = emailList.get(1).getSubject();
                    String latestBody = emailList.get(1).getBody();

                    i.putExtra(RECEIVER_TAG, latestReceiver);
                    i.putExtra(SUBJECT_TAG, latestSubject);
                    i.putExtra(BODY_TAG, latestBody);

                    startActivity(i);
                }


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