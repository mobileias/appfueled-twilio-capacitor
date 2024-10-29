package com.appfueled.twilio;

import android.app.Service;
import android.app.PendingIntent;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.IBinder;
import android.os.Build;
import androidx.core.app.NotificationCompat;

public class IncomingCallService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Retrieve caller data if available
        String callerName = intent.getStringExtra("callerName");

        // Display the call notification when service starts
        showIncomingCallNotification(callerName);
        return START_STICKY;
    }

    private void showIncomingCallNotification(String callerName) {
        String notificationTitle = "Incoming Call";
        String notificationText = (callerName != null) ? "Call from " + callerName : "Tap to answer the call";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "incoming_call_channel")
                .setContentTitle(notificationTitle)
                .setContentText(notificationText)
                .setSmallIcon(R.drawable.ic_call)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setFullScreenIntent(getPendingIntent(), true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("incoming_call_channel",
                    "Incoming Call Notifications", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        startForeground(1, builder.build());
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(this, CallActivity.class); // Opens custom call screen
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
