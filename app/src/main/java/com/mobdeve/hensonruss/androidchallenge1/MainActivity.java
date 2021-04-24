package com.mobdeve.hensonruss.androidchallenge1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    public static final String DRAFT_TAG = "DRAFT_TAG";

    private ArrayList<Email> emailList;
    private boolean draft;

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

        // Check if there's draft
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        draft = sp.getBoolean(DRAFT_TAG, false);


        // Get arraylist of email in the shared preferences
        emailList = SharedPreference.readListFromPref(this);

        // check if there's email in the array list
        if(emailList == null){
            emailListView.setVisibility(View.GONE);
            noEmailsTv.setVisibility(View.VISIBLE);
            emailList = new ArrayList<>();
        }

        Intent i = getIntent();

        if(i.getExtras() != null && draft != true) {

            String receiver = i.getStringExtra(RECEIVER_TAG);
            String subject = i.getStringExtra(SUBJECT_TAG);
            String body = i.getStringExtra(BODY_TAG);
            //draft = i.getBooleanExtra(DRAFT_TAG, false);

            Email email = new Email(receiver, subject, body);
            emailList.add(email);
        }
        else if(draft == true){

            String receiver = sp.getString(RECEIVER_TAG, "");
            String subject = sp.getString(SUBJECT_TAG, "");
            String body = sp.getString(BODY_TAG, "");

            Email email = new Email(receiver, subject, body);
            emailList.add(email);
        }
            // update the array list
            SharedPreference.writeListInPref(getApplicationContext(), emailList);

            // reverse the list so that the latest will be shown first
            Collections.reverse(emailList);

            for (int j = 0; j < emailList.size(); j++) {
                receiverList.add(emailList.get(j).getReceiver());
                subjectList.add(emailList.get(j).getSubject());
                bodyList.add(emailList.get(j).getBody());
            }

            EmailListAdapter adapter = new EmailListAdapter(this, receiverList, subjectList, bodyList);
            emailListView.setAdapter(adapter);

            emailListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    /*if(draft == true && position == 0){
                        Intent i = new Intent(MainActivity.this, NewEmailActivity.class);
                        String receiver = emailList.get(0).getReceiver();
                        String subject = emailList.get(0).getSubject();
                        String body = emailList.get(0).getBody();

                        i.putExtra(RECEIVER_TAG, receiver);
                        i.putExtra(SUBJECT_TAG, subject);
                        i.putExtra(BODY_TAG, body);
                        i.putExtra(DRAFT_TAG, draft);

                        startActivity(i);
                    }*/
                }
            });

        this.newBtn = findViewById(R.id.newBtn);
        newBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(draft == true) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("You have a draft email")
                            .setMessage("By creating a new email, you'll automatically delete the draft. Do you wish to proceed?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(MainActivity.this, NewEmailActivity.class);

                                    //delete the draft
                                    SharedPreferences.Editor editor = sp.edit();


                                    startActivity(i);
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                else {
                    Intent i = new Intent(MainActivity.this, NewEmailActivity.class);
                    startActivity(i);
                }

            }
        });


        this.latestBtn = findViewById(R.id.latestBtn);
        latestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(emailList.size() == 0){
                    Toast.makeText(
                            MainActivity.this,
                            "There are no emails currently.",
                            Toast.LENGTH_LONG
                    ).show();
                }
                else if(draft == true){
                    Intent i = new Intent(MainActivity.this, EmailActivity.class);

                    String latestReceiver = emailList.get(1).getReceiver();
                    String latestSubject = emailList.get(1).getSubject();
                    String latestBody = emailList.get(1).getBody();

                    i.putExtra(RECEIVER_TAG, latestReceiver);
                    i.putExtra(SUBJECT_TAG, latestSubject);
                    i.putExtra(BODY_TAG, latestBody);

                    startActivity(i);
                }
                else {
                    Intent i = new Intent(MainActivity.this, EmailActivity.class);

                    String latestReceiver = emailList.get(0).getReceiver();
                    String latestSubject = emailList.get(0).getSubject();
                    String latestBody = emailList.get(0).getBody();

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