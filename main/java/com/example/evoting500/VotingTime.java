package com.example.evoting500;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VotingTime extends AppCompatActivity {

    private DatabaseReference votingPeriodRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_time);
        votingPeriodRef = FirebaseDatabase.getInstance().getReference().child("votingPeriod");
    }
    public void startVoting(View view) {
        votingPeriodRef.child("votingStatus").setValue("Started");
        Toast.makeText(this, "Voting Started", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent("com.example.evoting500.VOTING_STATUS_ACTION");
        intent.putExtra("votingStatus", "Started");
        sendBroadcast(intent);
    }

    public void endVoting(View view) {
        votingPeriodRef.child("votingStatus").setValue("Ended");
        Toast.makeText(this, "Voting Ended", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent("com.example.evoting500.VOTING_STATUS_ACTION");
        intent.putExtra("votingStatus", "Ended");
        sendBroadcast(intent);
    }

}