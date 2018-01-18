package com.example.heenali.demo_notification;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Belal on 5/27/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    private static final String TAG = "MyFirebaseMsgService";
    String json_save;
    UserFunctions UF;
    String mess_counter="";
    Notification notification;
    Activity a;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        //Displaying data in log
        // Check if message contains a notification payload.

        try
        {
            if (remoteMessage.getData().size() > 0)
            {
                Log.d(TAG, "Message data payload: " + remoteMessage.getData());
                Log.d(TAG, "From: " + remoteMessage.getFrom());
              //  Toast.makeText(getApplicationContext(),remoteMessage.getNotification().toString(),Toast.LENGTH_LONG).show();
                sendNotification(remoteMessage.toString());

            }
        }
        catch (Exception e)
        {
            Log.e("error_is", e.toString());
        }
    }

    //This method is only generating push notification
    //It is same as we did in earlier posts 
    private void sendNotification(String messageBody)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("from", "splash");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(bitmap)
                .setContentTitle("TrukkerUAE Driver")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }


}

