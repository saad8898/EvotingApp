package com.example.evoting500;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class DetailsActivity extends AppCompatActivity {

    TextView detailsName, detailsCnic, detailsEmail, detailsPhone, detailsStatus;
    String recStatus, key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        detailsName = findViewById(R.id.detailsName);
        detailsCnic = findViewById(R.id.detailsCnic);
        detailsEmail = findViewById(R.id.detailsEmail);
        detailsPhone = findViewById(R.id.detailsPhone);
        detailsStatus = findViewById(R.id.detailsStatus);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailsName.setText(bundle.getString("name"));
            detailsCnic.setText(bundle.getString("cnic"));
            detailsEmail.setText(bundle.getString("email"));
            detailsPhone.setText(bundle.getString("phone"));
            detailsStatus.setText(bundle.getString("status"));

            recStatus = bundle.getString("status");
            key = bundle.getString("key");
        }
    }

    public void verifyUser(View view) {
        FirebaseDatabase.getInstance().getReference("UsersDB")
                .child(key)
                .child("requestStatus")
                .setValue("Verified")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(DetailsActivity.this, "Request verified", Toast.LENGTH_SHORT).show();
                            detailsStatus.setText("Verified");
                        } else {
                            Toast.makeText(DetailsActivity.this, "Failed to verify request", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
