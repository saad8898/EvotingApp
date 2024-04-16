package com.example.evoting500;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class VotingStatusReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String votingStatus = intent.getStringExtra("votingStatus");

        if (votingStatus != null) {
            if (votingStatus.equals("Started")) {
                Toast.makeText(context, "Voting Started", Toast.LENGTH_SHORT).show();
            } else if (votingStatus.equals("Ended")) {
                Toast.makeText(context, "Voting Ended", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

