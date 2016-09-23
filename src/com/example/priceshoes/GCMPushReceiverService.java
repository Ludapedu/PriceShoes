package com.example.priceshoes;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by luis.perez on 21/09/2016.
 */

public class GCMPushReceiverService extends GcmListenerService {

    int NotificationNumber = 1;

    @Override
    public void onMessageReceived(String from, Bundle data)
    {
        sendNotification(data.getString("mensaje"));
    }

    private void sendNotification (String message)
    {
        Intent intent = new Intent(this, Resumen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestCode = NotificationNumber;
        PendingIntent pendingintent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.pagos)
                .setLargeIcon((((BitmapDrawable)getResources()
                                .getDrawable(R.drawable.logo)).getBitmap()))
                .setContentText(message)
                .setVibrate(new long[] {500, 300, 500, 300})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLights(0x00ff,1500,1500)
                .setAutoCancel(true)
                .setContentIntent(pendingintent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NotificationNumber, noBuilder.build());
        NotificationNumber ++;

    }
}
