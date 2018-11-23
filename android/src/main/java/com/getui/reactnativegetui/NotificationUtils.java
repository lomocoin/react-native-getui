package com.getui.reactnativegetui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import org.json.JSONObject;

import java.util.Iterator;


public class NotificationUtils {
    // 发送消息到通知栏 ingithub
    public static void sendNotification(Context context, String json) throws Exception {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());

        String title, content;
        JSONObject jsonObject = new JSONObject(json);
        title = jsonObject.getString("title");
        content = jsonObject.getString("content");
        Iterator<String> it = jsonObject.keys();
        while (it.hasNext()) {
            String key = it.next();
            String value = jsonObject.getString(key);
            if (!key.equals("title") && !key.equals("content")) {
                intent.putExtra(key, value);
            }
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        showNotification(context, title, content, pendingIntent);
    }


    private static void showNotification(Context context, String title, String content, PendingIntent pendingIntent) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) context.getApplicationInfo().loadIcon(context.getPackageManager());
        Bitmap appIcon = bitmapDrawable.getBitmap();
        int smallIcon = context.getApplicationInfo().icon;
        //当sdk版本大于26
        if (Build.VERSION.SDK_INT >= 26) {
            String id = "channel_eunex";
            String description = "eunex";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationChannel channel = new NotificationChannel(id, description, importance);
            manager.createNotificationChannel(channel);
            Notification notification = new Notification.Builder(context, id)
                    .setCategory(Notification.CATEGORY_MESSAGE)
                    .setSmallIcon(smallIcon)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();
            manager.notify(0, notification);
        } else {

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(smallIcon)
                    .setLargeIcon(appIcon)
                    .setContentTitle(title)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                    .setContentText(content)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationBuilder.build());
        }
    }

}
