package com.example.evoting500;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDashboard extends AppCompatActivity {

    ImageView bgapp;
    LinearLayout option;
    Animation fromBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        bgapp = findViewById(R.id.bgapp);
        option = findViewById(R.id.options);
        bgapp.animate().translationY(-1100).setDuration(800).setStartDelay(300);
        fromBottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        option.startAnimation(fromBottom);
    }


    public void castVote(View view) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            DatabaseReference votingStatusRef = FirebaseDatabase.getInstance().getReference().child("votingPeriod").child("votingStatus");
            votingStatusRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String votingStatus = dataSnapshot.getValue(String.class);

                    if (votingStatus != null && votingStatus.equals("Started")) {
                        DatabaseReference userVotesRef = FirebaseDatabase.getInstance().getReference("UsersDB").child(userId).child("hasVoted");
                        userVotesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String hasVoted = dataSnapshot.getValue(String.class);

                                if (hasVoted == null || !hasVoted.equals("true")) {
                                    FirebaseDatabase.getInstance().getReference("UsersDB").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            UserData userData = dataSnapshot.getValue(UserData.class);
                                            if (userData != null && userData.getRequestStatus().equals("Verified")) {
                                                Toast.makeText(UserDashboard.this, "User Verified", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(UserDashboard.this, CandidateList.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(UserDashboard.this, "Please wait for verification.", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Toast.makeText(UserDashboard.this, "Error occurred.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    Toast.makeText(UserDashboard.this, "You have already casted your vote.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(UserDashboard.this, "Error occurred.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(UserDashboard.this, "Voting has not started yet.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(UserDashboard.this, "Error occurred.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "User not authenticated.", Toast.LENGTH_SHORT).show();
        }
    }

    public void logOut(View view) {
        Intent intent = new Intent(UserDashboard.this, MainActivity.class);
        startActivity(intent);
    }
}