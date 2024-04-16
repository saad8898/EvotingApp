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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class userRequests extends AppCompatActivity {

    RecyclerView recyclerView;
    List<UserData> userDataList;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_requests);
        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(userRequests.this, 1);
        recyclerView.setLayoutManager(layoutManager);
        AlertDialog.Builder builder = new AlertDialog.Builder(userRequests.this);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

        userDataList = new ArrayList<>();
        MyAdapter adapter = new MyAdapter(userRequests.this, userDataList);
        recyclerView.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance().getReference("UsersDB");

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userDataList.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    UserData userData = itemSnapshot.getValue(UserData.class);
                    userData.setKey(itemSnapshot.getKey());
                    userDataList.add(userData);
                    if (userData.getRequestStatus().equals("Verified")) {
                        sendNotification(userData);
                    }
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });
    }

    private void sendNotification(UserData userData) {
        String username = userData.getFullname();
        String message = "Verification is done. You are now a verified user.";

        String channelId = "verification_channel";
        int notificationId = 1;

        Intent intent = new Intent(this, UserDashboard.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.notification_icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.app_logo))
                .setContentTitle("Verification Complete")
                .setContentText("Hello " + username + "! " + message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Verification Channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = builder.build();
        notificationManager.notify(notificationId, notification);
    }

    public void logOut(View view) {
        Intent intent = new Intent(userRequests.this, MainActivity.class);
        startActivity(intent);
    }
}