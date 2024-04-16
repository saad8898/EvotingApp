package com.example.evoting500;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class VoteCandidate extends AppCompatActivity {

    TextView detailName, detailHead;
    ImageView detailImage;
    String key = "";
    String imageUrl = "";
    Button castVotebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_candidate);
        detailName = findViewById(R.id.detailName);
        detailImage = findViewById(R.id.detailImage);
        detailHead = findViewById(R.id.detailHead);
        castVotebtn = findViewById(R.id.voteButton);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailName.setText(bundle.getString("Name"));
            detailHead.setText(bundle.getString("Head"));
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }
    }

    public void castVote(View view) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String partyName = detailName.getText().toString();

        DatabaseReference partyVotesRef = FirebaseDatabase.getInstance().getReference("PartyVotes").child(partyName).child("Votes");
        DatabaseReference userVotesRef = FirebaseDatabase.getInstance().getReference("UsersDB").child(userId).child("hasVoted");

        userVotesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String hasVoted = dataSnapshot.getValue(String.class);

                if (hasVoted == null || !hasVoted.equals("true")) {
                    partyVotesRef.runTransaction(new Transaction.Handler() {
                        @NonNull
                        @Override
                        public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                            Integer currentVotes = mutableData.getValue(Integer.class);
                            if (currentVotes == null) {
                                mutableData.setValue(1);
                            } else {
                                mutableData.setValue(currentVotes + 1);
                            }
                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, boolean committed, @Nullable DataSnapshot dataSnapshot) {
                            if (committed) {
                                userVotesRef.setValue("true").addOnSuccessListener(aVoid -> {
                                    Toast.makeText(VoteCandidate.this, "Vote casted successfully!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(VoteCandidate.this, UserDashboard.class);
                                    castVotebtn.setEnabled(false);
                                    startActivity(intent);
                                    finish();
                                }).addOnFailureListener(e -> {
                                    Toast.makeText(VoteCandidate.this, "Failed to cast vote. Please try again.", Toast.LENGTH_SHORT).show();
                                });
                            } else {
                                Toast.makeText(VoteCandidate.this, "Failed to cast vote. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(VoteCandidate.this, "You have already casted your vote.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(VoteCandidate.this, "Error occurred.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void logOut(View view) {
        Intent intent = new Intent(VoteCandidate.this, MainActivity.class);
        startActivity(intent);
    }
}