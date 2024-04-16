package com.example.evoting500;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboard extends AppCompatActivity {

    ImageView bgapp;
    LinearLayout option;
    Animation fromBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        bgapp = findViewById(R.id.bgapp);
        option = findViewById(R.id.options);
        bgapp.animate().translationY(-1100).setDuration(800).setStartDelay(300);
        fromBottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        option.startAnimation(fromBottom);
    }

    public void verifyUsers(View view) {
        Intent intent = new Intent(AdminDashboard.this, userRequests.class);
        startActivity(intent);
    }

    public void manageCandidates(View view) {
        Intent intent = new Intent(AdminDashboard.this, ManageCandidates.class);
        startActivity(intent);
    }

    public void votingPeriod(View view) {
        Intent intent = new Intent(AdminDashboard.this, VotingTime.class);
        startActivity(intent);
    }

    public void countVotes(View view) {
        Intent intent = new Intent(AdminDashboard.this, voteCount.class);
        startActivity(intent);
    }

    public void logOut(View view) {
        Intent intent = new Intent(AdminDashboard.this, MainActivity.class);
        startActivity(intent);
    }
}